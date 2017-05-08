package com.xspeedit.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xspeedit.exception.TailleArticleException;
import com.xspeedit.pojo.Article;
import com.xspeedit.service.ArticlesService;

/**
 * Service pour toutes les opérations touchant à des articles
 * @author jferezou
 *
 */
@Service
public class ArticlesServiceImpl implements ArticlesService{

	@Value("${xspeedIt.article.taille.min}")
	private int articleTailleMin;
	@Value("${xspeedIt.article.taille.max}")
	private int articleTailleMax;

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticlesServiceImpl.class);
	
	/**
	 * A partir d'une liste de char, on construit notre liste d'articles avec leur taille
	 * @param line
	 * @return
	 * @throws TailleArticleException
	 */
	@Override
	public List<Article> getArticles(final String line) throws TailleArticleException{

		Instant start = Instant.now();
		List<Article> articles = new ArrayList<>();
		LOGGER.debug("Parse la line {} pour en extraire les articles", line);
		for (int i = 0 ; i < line.length() ; i++){
			
			char tailleChar = line.charAt(i);
			// la taille doit être un entier
			if(Character.isDigit(tailleChar)) {
				int taille = Character.getNumericValue(tailleChar);
				// on check que taille [1;9]
				if(taille >= this.articleTailleMin && taille <= this.articleTailleMax) {
					Article article = new Article(taille);
					articles.add(article);
				}
				else {
					throw new TailleArticleException("La taille de l'article doit être un entier compris entre ["+ this.articleTailleMin+" ; "+ this.articleTailleMax+"], taille de cet article : "+taille);
				}
			}
			else {
				throw new TailleArticleException("La taille de l'article doit être un entier, taille de cet article : " + tailleChar);
			}
		}

		Instant end = Instant.now();

		LOGGER.info("Fin traitement génération des articles en : {} ms", Duration.between(start, end).toMillis());
		return articles;		
	}
	
	/**
	 * Créé les articles et les classe dans une map en fonction de leur taille
	 * @param line
	 * @return
	 * @throws TailleArticleException
	 */
	@Override
	public Map<Integer,List<Article>> getArticlesOptim(final String line) throws TailleArticleException {
		Map<Integer,List<Article>> articlesMap = new HashMap<>();
		

		Instant start = Instant.now();
		LOGGER.debug("Parse la line {} pour en extraire les articles", line);
		for (int i = 0 ; i < line.length() ; i++){
			
			char tailleChar = line.charAt(i);
			// la taille doit être un entier
			if(Character.isDigit(tailleChar)) {
				int taille = Character.getNumericValue(tailleChar);
				// on check que taille [1;9]
				if(taille >= this.articleTailleMin && taille <= this.articleTailleMax) {
					if(articlesMap.get(taille) != null) {
						Article article = new Article(taille);
						articlesMap.get(taille).add(article);
					}
					else {
						List<Article> articles = new ArrayList<>();
						articles.add(new Article(taille));
						articlesMap.put(taille, articles);
					}
				}
				else {
					throw new TailleArticleException("La taille de l'article doit être un entier compris entre ["+ this.articleTailleMin+" ; "+ this.articleTailleMax+"], taille de cet article : "+taille);
				}
			}
			else {
				throw new TailleArticleException("La taille de l'article doit être un entier, taille de cet article : " + tailleChar);
			}
		}

		Instant end = Instant.now();

		LOGGER.info("Fin traitement génération des articles en : {} ms", Duration.between(start, end).toMillis());
		return articlesMap;		
	}

}
