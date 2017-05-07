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
	
	// classe servant à générer des jeux de données
	public static void main(String[] args) {
		final int maxArticles = 50000;
		final int minArticle = 49999;

		final int minArticleTaille = 1;
		final int maxArticleTaille = 10;		
		final int nbrJeuxDonnees = 50;
		
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
		int result = r.nextInt(high-low) + low;
		return result;
	}
}
