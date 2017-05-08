package com.xspeedit.generateur;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generateur de liste d'articles
 * @author jferezou
 *
 */
public class GenerateurArticles {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateurArticles.class);
	
	private GenerateurArticles() {
		
	}
	
	// classe servant à générer des jeux de données
	public static void main(String[] args) {
		final int maxArticles = 10000000;
		final int minArticle = maxArticles - 1;

		final int minArticleTaille = 1;
		final int maxArticleTaille = 10;		
		final int nbrJeuxDonnees = 1;
		
		for(int i = 0; i < nbrJeuxDonnees; i++) {
			int nbArticles = generateRandowValue(minArticle, maxArticles);
			StringBuilder strB = new StringBuilder();
			for(int article =0; article <= nbArticles ; article++) {
				int poidArticle = generateRandowValue(minArticleTaille, maxArticleTaille);
				strB.append(poidArticle);
			}
			LOGGER.info("{}", strB.toString());
		}

	}

	
	private static int generateRandowValue(final int low, final int high) {
		Random r = new Random();
		return r.nextInt(high-low) + low;
	}
}
