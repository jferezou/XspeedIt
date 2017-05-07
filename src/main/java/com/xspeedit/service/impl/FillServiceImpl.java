package com.xspeedit.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xspeedit.exception.AlgoException;
import com.xspeedit.exception.TailleArticleException;
import com.xspeedit.pojo.Article;
import com.xspeedit.pojo.Carton;
import com.xspeedit.service.ArticlesService;
import com.xspeedit.service.BestFitService;
import com.xspeedit.service.FillService;

/**
 * Service lançant les traitement une fois les fichiers txt avec les données lus
 * @author jferezou
 *
 */
@Service
public class FillServiceImpl implements FillService{
	private static final Logger LOGGER = LoggerFactory.getLogger(FillServiceImpl.class);

	
	@Autowired
	public ArticlesService articlesService;
	@Autowired
	public BestFitService bestFitService;
			
	private static final String SEPARATEUR = "/";
	
	/**
	 * Méthode qui a partir d'une ligne extrait les articles, optimise le remplissage et génère le texte résultat
	 * @param line : la ligne à traiter
	 */
	@Override
	public String fill(final String line) {
		Instant start = Instant.now();
		LOGGER.info("Début remplissage");

		StringBuilder strB = new StringBuilder();
		List<Carton> cartons = new ArrayList<>();
		try {
			strB.append("Articles :\t\t\t");
			strB.append(line);
			
			// 1 on parse la ligne pour en faire une liste d'articles
			List<Article> articles = this.articlesService.getArticles(line);
			LOGGER.info("Traitement de {} articles", articles.size());
			LOGGER.debug("Liste des articles : {}", articles);
			
			// 2 on trie ces articles du plus grand au plus petit
			Comparator<Article> articleComparator = (Article a1, Article a2) -> Integer.compare(a2.getTaille(), a1.getTaille());
			articles.sort(articleComparator);
			LOGGER.debug("Liste des articles triés : {}", articles);
			
			// 3 algo du best-fit : on parcourt ces articles un à un et on met l'article dans la boîte la mieux remplie
			cartons = this.bestFitService.bestFit(articles);			

			strB.append("\nRobot optimisé :\t");
			for(Carton carton : cartons) {
				strB.append(carton.genererResultat());
				strB.append(SEPARATEUR);
			}
			// on supprime le dernier /
			strB.deleteCharAt(strB.length() - 1);
			
		} catch (TailleArticleException e) {
			LOGGER.error("Erreur lors de la récupération des articles", e);
			strB.append("\nATTENTION : \t");
			strB.append("Une erreur liée à la taille d'un article est survenue lors du traitement de cette châine d'articles : ");
			strB.append(e.getMessage());
		} catch (AlgoException e) {
			strB.append("\nATTENTION : \t");
			LOGGER.error("Erreur lors de l'optimisation", e);
			strB.append("Une erreur lors de l'optimisation est survenue lors du traitement de cette châine d'articles : ");
			strB.append(e.getMessage());
		}

		strB.append("\nTotal cartons : ");
		strB.append(cartons.size());
		strB.append("\n");

		String lineResult = strB.toString();
		Instant end = Instant.now();
		LOGGER.debug("Fin remplissage : {}", lineResult);
		LOGGER.info("Fin remplissage de cette châine d'article en : {} ms", Duration.between(start, end).toMillis());
		return lineResult;
	}
	public void setArticlesService(ArticlesService articlesService) {
		this.articlesService = articlesService;
	}
	public void setBestFitService(BestFitService bestFitService) {
		this.bestFitService = bestFitService;
	}	
}
