package com.xspeedIt.service;

import java.io.IOException;
import org.apache.tika.exception.TikaException;

import com.xspeedIt.exception.TailleArticleException;

@FunctionalInterface
public interface ReaderFileService {

	/**
	 * Méthode lancant la lecture du fichier et la conversion de chaque ligne
	 */
	void readAndLaunch() throws TailleArticleException,TikaException, IOException ;
}
