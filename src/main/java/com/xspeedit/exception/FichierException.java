package com.xspeedit.exception;

/**
 * Erreur lors de la lecture du fichier.
 * @author jferezou
 *
 */
public class FichierException extends Exception {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FichierException() {
	    super();
	  }

	  public FichierException(String s) {
	    super(s);
	  }
}
