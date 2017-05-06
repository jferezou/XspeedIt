package com.xspeedIt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xspeedIt.exception.TailleArticleException;
import com.xspeedIt.pojo.Article;
import com.xspeedIt.service.ArticlesService;

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
					throw new TailleArticleException("La taille de l'article doit être un entier compris entre "+ this.articleTailleMin+" et "+ this.articleTailleMax+", taille actuelle : "+taille);
				}
			}
			else {
				throw new TailleArticleException("La taille de l'article doit être un entier !");
			}
		}
		
		return articles;		
	}

}
