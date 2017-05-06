package com.xspeedIt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

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
	public void testArticleNonEntierException() throws TailleArticleException {
		// si je n'ai pas que des entiers
		this.articlesService.getArticles("111a");
	}

	@Test(expected=TailleArticleException.class)
	public void testZeroException() throws TailleArticleException {
		// j'ai un zéro
		this.articlesService.getArticles("110");
	}
	@Test
	public void Test() throws TailleArticleException{
		
		String line ="163841689525773";
		assertThat(this.articlesService.getArticles(line)).hasSize(15).extracting(atc -> atc.getTaille()).containsExactlyElementsOf(new ArrayList<Integer>(Arrays.asList(1,6,3,8,4,1,6,8,9,5,2,5,7,7,3)));
	}

}
