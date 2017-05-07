package com.xspeedit.exception;

/**
 * Erreur lors de la création d'un article : taille négatif, pas entière etc ...
 * @author jferezou
 *
 */
public class TailleArticleException extends Exception {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TailleArticleException() {
	    super();
	  }

	  public TailleArticleException(String s) {
	    super(s);
	  }
}
