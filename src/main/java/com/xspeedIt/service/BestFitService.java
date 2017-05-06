package com.xspeedIt.service;

import java.util.List;

import com.xspeedIt.exception.AlgoException;
import com.xspeedIt.pojo.Article;
import com.xspeedIt.pojo.Carton;

public interface BestFitService {
	List<Carton> bestFit(final List<Article> articles) throws AlgoException;
}
