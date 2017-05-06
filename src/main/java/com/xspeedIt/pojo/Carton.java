package com.xspeedIt.pojo;

import java.util.ArrayList;
import java.util.Collection;

public class Carton {

	
	private Collection<Article> articles;

	public Carton() {
		this.articles = new ArrayList<>();
	}
	
	private int poidTotalArticle;
	
	public Collection<Article> getArticles() {
		return articles;
	}
	
	
	public void addArticle(final Article article) {
		this.articles.add(article);
		this.poidTotalArticle += article.getTaille();
	}


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
