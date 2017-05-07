package com.xspeedit.service;
/**
 * Service lançant les traitement une fois les fichiers txt avec les données lus
 * @author jferezou
 *
 */
public interface FillService {

	/**
	 * Méthode qui a partir d'une ligne extrait les articles, optimise le remplissage et génère le texte résultat
	 * @param line : la ligne à traiter
	 */
	String fill(final String line);
}
