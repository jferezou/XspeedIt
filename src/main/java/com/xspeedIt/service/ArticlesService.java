package com.xspeedIt.service;

import java.util.List;

import com.xspeedIt.exception.TailleArticleException;
import com.xspeedIt.pojo.Article;

public interface ArticlesService {
	List<Article> getArticles(final String line) throws TailleArticleException;
}
