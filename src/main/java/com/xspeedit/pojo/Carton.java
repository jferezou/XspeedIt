package com.xspeedit.pojo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Objet representant un carton
 * @author jferezou
 *
 */
public class Carton {

	// liste des articles présents dans ce carton
	private Collection<Article> articles;
	
	private int poidTotalArticle;

	public Carton() {
		this.articles = new ArrayList<>();
	}
	
	public Collection<Article> getArticles() {
		return articles;
	}
	
	/**
	 * Ajoute un article dans ce carton
	 * @param article
	 */
	public void addArticle(final Article article) {
		this.articles.add(article);
		this.poidTotalArticle += article.getTaille();
	}

	/**
	 * Renvoit la taille totale présente dans ce carton
	 * @return
	 */
	public int getPoidTotalArticle() {
		return poidTotalArticle;
	}


	public String genererResultat() {
		StringBuilder strB = new StringBuilder();
		for(Article article : articles) {
			strB.append(article.getTaille());
		}
		return strB.toString();
	}


	@Override
	public String toString() {
		return "Carton [poidTotalArticle=" + poidTotalArticle +", articles=" + articles + "]";
	}
	
	
	
}
