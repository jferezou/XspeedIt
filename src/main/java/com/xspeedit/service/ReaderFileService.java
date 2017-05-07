package com.xspeedit.service;

import java.io.IOException;
import org.apache.tika.exception.TikaException;

import com.xspeedit.exception.TailleArticleException;

/**
 * Service perettant les opérations sur les fichiers : lecture/ecriture
 * @author jferezou
 *
 */
public interface ReaderFileService {

	/**
	 * Méthode lancant la lecture du fichier, l'optimisation et l'écriture des résultats
	 */
	void readAndLaunch() throws TailleArticleException,TikaException, IOException ;
}
