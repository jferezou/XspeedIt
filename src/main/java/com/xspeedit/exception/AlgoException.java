package com.xspeedit.exception;

/**
 * Exception lors d'une erreur dans l'algorithme (ie : une contrainte n'est pas respectée)
 * @author jferezou
 *
 */
public class AlgoException extends Exception {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlgoException() {
	    super();
	  }

	  public AlgoException(String s) {
	    super(s);
	  }
}