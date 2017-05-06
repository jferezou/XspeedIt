package com.xspeedIt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xspeedIt.exception.TailleArticleException;
import com.xspeedIt.service.ArticlesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ArticlesServiceTest {
	@Autowired
	private ArticlesService articlesService;
		
	@Test(expected=TailleArticleException.class)
	public void testBestFitException() throws TailleArticleException {
		// si je n'ai pas que des entiers
		this.articlesService.getArticles("111a");
	}

}
