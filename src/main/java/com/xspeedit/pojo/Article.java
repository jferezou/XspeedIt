package com.xspeedit.pojo;

/**
 * Objet representant un article
 * @author jferezou
 *
 */
public class Article {

	// taille de cet article, compris entre [1;9]
	private int taille;

	public Article(int taille) {
		this.taille = taille;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	@Override
	public String toString() {
		return "Article [taille=" + taille + "]";
	}
	
	
}
