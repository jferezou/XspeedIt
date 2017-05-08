package com.xspeedit.service;

import java.util.List;
import java.util.Map;

import com.xspeedit.exception.AlgoException;
import com.xspeedit.pojo.Article;
import com.xspeedit.pojo.Carton;
/**
 * Service mettant a disposition l'algorithme du best-fit
 * @author jferezou
 *
 */
public interface BestFitService {
	/**
	 * Implementation de l'algorithme du best-fit qui choisi de remplir le carton qui sera la mieux rempli avec ces articles
	 * @param articles : liste des articles à traiter
	 * @return
	 * @throws AlgoException
	 */
	List<Carton> bestFit(final List<Article> articles) throws AlgoException;
	
	/**
	 * Implementation de l'aglo optimisé : pré-traitement de la liste
	 * @param articles
	 * @return
	 * @throws AlgoException
	 */
	List<Carton> bestFitOptim(final Map<Integer,List<Article>> articles) throws AlgoException;
}
