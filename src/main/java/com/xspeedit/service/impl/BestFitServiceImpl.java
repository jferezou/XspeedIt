package com.xspeedit.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xspeedit.exception.AlgoException;
import com.xspeedit.pojo.Article;
import com.xspeedit.pojo.Carton;
import com.xspeedit.service.BestFitService;

/**
 * Service mettant a disposition l'algorithme du best-fit
 * @author jferezou
 *
 */
@Service
public class BestFitServiceImpl implements BestFitService{

	@Value("${xspeedIt.carton.taille}")
	private int cartonTaille;

	private static final Logger LOGGER = LoggerFactory.getLogger(BestFitServiceImpl.class);

	/**
	 * Verifie que les contraintes sont respectee et que tous mes articles sont ranges
	 * @param articles
	 * @param cartons
	 * @throws AlgoException 
	 */
	private void verification(final List<Article> articles, final List<Carton> cartons) throws AlgoException {
		int totalArticles = 0;
		for(Carton carton : cartons) {
			if(carton.getPoidTotalArticle() > this.cartonTaille) {
				throw new AlgoException("Un des carton a une liste d'articles depassant son poid max : "+carton);
			}			
			totalArticles += carton.getArticles().size();
		}
		
		// on a bien le même nombre d'articles en entrée et dans les cartons
		if(totalArticles != articles.size()) {
			throw new AlgoException("Un article n'est pas rangé dans un carton ou est rangé plusieurs fois");
		}
		
		// TODO verifier que les articles sont bien 1 et une seule fois dans un carton
	}
	
	/**
	 * Implementation de l'algorithme du best-fit qui choisi de remplir le carton qui sera la mieux rempli avec ces articles
	 * @param articles : liste des articles à traiter
	 * @return
	 */
	@Override
	public List<Carton> bestFit(final List<Article> articles) throws AlgoException{
		Instant start = Instant.now();
		List<Carton> cartons = new ArrayList<>();
		
		for(Article article : articles) {
			LOGGER.debug("Traitement de l'article {}", article);
			Carton cartonSelectionne = null;
			// permet de mettre cet article dans le carton qui sera la mieux rempli
			int cartonIndex = 0;
			boolean cartonRempliMax = false;
			int meilleurPoidRemplissage = 0;
			while(cartonIndex < cartons.size() && !cartonRempliMax) {
				Carton cartonCourant = cartons.get(cartonIndex);
				LOGGER.debug("On essaye de le placer dans le carton {}", cartonCourant);
				int poidsCartonAvecArticle = cartonCourant.getPoidTotalArticle() + article.getTaille();
				// le cas d'arret ou le carton reussi a etre rempli au maximum
				if(poidsCartonAvecArticle == this.cartonTaille) {
					cartonRempliMax = true;
					cartonSelectionne = cartonCourant;
					LOGGER.debug("Poid max trouver {}, on stop l'algo avec ce carton", this.cartonTaille);
				}
				// sinon, si le remplissage est meilleur qu'avant, on sélectionne ce carton 
				else if (poidsCartonAvecArticle < this.cartonTaille && poidsCartonAvecArticle > meilleurPoidRemplissage) {
					meilleurPoidRemplissage = poidsCartonAvecArticle;
					cartonSelectionne = cartonCourant;
					LOGGER.debug("Ce carton est mieux rempli, on le sélectionne");
				}
				cartonIndex++;				
			}
			
			// si le carton ou placer l'article a été identifié on le met dedans
			if(cartonSelectionne != null) {
				cartonSelectionne.addArticle(article);
			}
			// sinon on le mets dans un nouveau carton
			else {
				LOGGER.debug("Aucun carton ne faisait l'affaire, on en créé un nouveau");
				Carton nouveauCarton = new Carton();
				nouveauCarton.addArticle(article);
				cartons.add(nouveauCarton);
			}
			
			
		}
		this.verification(articles, cartons);

		Instant end = Instant.now();
		LOGGER.info("Fin traitement best-fit en : {} ms", Duration.between(start, end).toMillis());
		return cartons;
	}


	/**
	 * Implementation de l'aglo optimisé : pré-traitement de la liste
	 * @param articles
	 * @return
	 * @throws AlgoException
	 */
	@Override
	public List<Carton> bestFitOptim(final Map<Integer, List<Article>> articles) throws AlgoException {
		List<Carton> cartons = new ArrayList<>();
		
		// on rempli les cartons : 91/82/73/64/55
		cartons.addAll(this.preRemplissage(articles.get(1), articles.get(9)));
		cartons.addAll(this.preRemplissage(articles.get(2), articles.get(8)));
		cartons.addAll(this.preRemplissage(articles.get(3), articles.get(7)));
		cartons.addAll(this.preRemplissage(articles.get(4), articles.get(6)));
		// le 55 est un cas particulier car on utilise la même liste
		cartons.addAll(this.preRemplissage(articles.get(5)));
		
		List<Article> articlesRestant = new ArrayList<>();
		for(int index = 9; index > 0 ; index --) {
			List<Article> articlesList = articles.get(index);
			if(articlesList != null) {
				articlesRestant.addAll(articlesList);
			}
		}
		
		// on appelle l'algo normal
		cartons.addAll(this.bestFit(articlesRestant));
		
		return cartons;
	}
	
	/**
	 * En fonction de 2 listes complémentaires (ie somme taille = 10), pré-construit des cartons optimisés
	 * @param l1
	 * @param l2
	 * @return
	 */
	private List<Carton> preRemplissage(final List<Article> l1, final List<Article> l2) {
		List<Carton> cartons = new ArrayList<>();

		LOGGER.debug("Liste 1 en entrée : {}", l1);
		LOGGER.debug("Liste 2 en entrée : {}", l2);
		if(l1 != null && l2 != null) {
			int index = 0;
			while(index < l1.size() && index < l2.size()) {
				Carton carton = new Carton();
				carton.addArticle(l1.get(index));
				carton.addArticle(l2.get(index));
				cartons.add(carton);
				index++;
			}
			
			// on supprime les articles remplis
			for(int i = 0; i < index ; i++) {
				l1.remove(0);
				l2.remove(0);
			}
		}
		LOGGER.debug("Liste 1 restante : {}", l1);
		LOGGER.debug("Liste 2 restante : {}", l2);
		return cartons;
	}


	/**
	 * En fonction d'une liste complémentaire (ie somme taille = 10), pré-construit des cartons optimisés
	 * @param l1
	 * @param l2
	 * @return
	 */
	private List<Carton> preRemplissage(final List<Article> l1) {
		List<Carton> cartons = new ArrayList<>();

		LOGGER.debug("Liste 1 en entrée : {}", l1);
		if(l1 != null && l1.size() > 1) {
			int index = 0;
			while(index + 1 < l1.size()) {
				Carton carton = new Carton();
				carton.addArticle(l1.get(index));
				carton.addArticle(l1.get(index + 1));
				cartons.add(carton);
				index = index + 2;
			}
			
			// on supprime les articles remplis
			for(int i = 0; i < index ; i++) {
				l1.remove(0);
			}
		}
		LOGGER.debug("Liste 1 restante : {}", l1);
		return cartons;
	}
	
}
