package com.xspeedIt.pojo;

public class Article {

	
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
