package com.xspeedit.service;

import java.util.List;
import java.util.Map;

import com.xspeedit.exception.TailleArticleException;
import com.xspeedit.pojo.Article;
/**
 * Service pour toutes les opérations touchant à des articles
 * @author jferezou
 *
 */
public interface ArticlesService {
	/**
	 * A partir d'une liste de char, on construit notre liste d'articles avec leur taille
	 * @param line
	 * @return
	 * @throws TailleArticleException
	 */
	List<Article> getArticles(final String line) throws TailleArticleException;
	
	/**
	 * Créé les articles et les classe dans une map en fonction de leur taille
	 * @param line
	 * @return
	 * @throws TailleArticleException
	 */
	Map<Integer,List<Article>> getArticlesOptim(final String line) throws TailleArticleException;
}
