package com.xspeedit.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
	
}
