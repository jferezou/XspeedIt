package com.xspeedIt.service.impl;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xspeedIt.exception.AlgoException;
import com.xspeedIt.exception.TailleArticleException;
import com.xspeedIt.pojo.Article;
import com.xspeedIt.pojo.Carton;
import com.xspeedIt.service.ArticlesService;
import com.xspeedIt.service.BestFitService;
import com.xspeedIt.service.FillService;

@Service
public class FillServiceImpl implements FillService{
	private static final Logger LOGGER = LoggerFactory.getLogger(FillServiceImpl.class);

	
	@Autowired
	public ArticlesService articlesService;
	@Autowired
	public BestFitService bestFitService;
			
	private static final String SEPARATEUR = "/";
	@Override
	/**
	 * Permet de remplir les cartons
	 */
	public String fill(final String line) {
		LOGGER.info("Début remplissage");

		StringBuilder strB = new StringBuilder();
		try {
			// 1 on parse la ligne pour en faire une liste d'articles
			List<Article> articles = this.articlesService.getArticles(line);
			LOGGER.debug("Liste des articles : {}", articles);
			
			// 2 on trie ces articles du plus grand au plus petit
			Comparator<Article> articleComparator = (Article a1, Article a2) -> Integer.compare(a2.getTaille(), a1.getTaille());
			articles.sort(articleComparator);
			LOGGER.debug("Liste des articles triés : {}", articles);
			
			// 3 algo du best-fit : on parcourt ces articles un à un et on met l'article dans la boîte la mieux remplie
			List<Carton> cartons = this.bestFitService.bestFit(articles);			

			strB.append("Total cartons : ");
			strB.append(cartons.size());
			strB.append(" pour les articles : ");
			strB.append(line);
			strB.append("\n");
			for(Carton carton : cartons) {
				strB.append(carton.genererResultat());
				strB.append(SEPARATEUR);
			}
			// on supprime le dernier /
			strB.deleteCharAt(strB.length() - 1);
			
		} catch (TailleArticleException e) {
			LOGGER.error("Erreur lors de la récupération des articles", e);
		} catch (AlgoException e) {
			LOGGER.error("Erreur lors de l'optimisation", e);
		}
		

		String lineResult = strB.toString();
		LOGGER.debug("Fin remplissage : {}", lineResult);
		LOGGER.info("Fin remplissage");
		return lineResult;
	}

	
	
}
