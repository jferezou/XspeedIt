package com.xspeedIt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.xspeedIt.exception.AlgoException;
import com.xspeedIt.exception.TailleArticleException;
import com.xspeedIt.pojo.Article;
import com.xspeedIt.pojo.Carton;
import com.xspeedIt.service.ArticlesService;
import com.xspeedIt.service.BestFitService;
import com.xspeedIt.service.impl.FillServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class FillServiceTest {

	private FillServiceImpl fillService;
	
	@Mock
	private ArticlesService articlesService;

	@Mock
	private BestFitService bestFitService;


	@Before
	public void setup() throws TailleArticleException, AlgoException {
		Mockito.when(this.articlesService.getArticles(Mockito.anyString())).thenReturn(new ArrayList<Article>());
		List<Carton> cartons = new ArrayList<>();
		Carton c1 = new Carton();
		c1.addArticle(new Article(1));
		c1.addArticle(new Article(6));
		cartons.add(c1);
		Carton c2 = new Carton();
		c2.addArticle(new Article(8));
		cartons.add(c2);
		Carton c3 = new Carton();
		c3.addArticle(new Article(1));
		c3.addArticle(new Article(6));
		c3.addArticle(new Article(3));
		cartons.add(c3);
		Carton c4 = new Carton();
		c4.addArticle(new Article(9));
		cartons.add(c4);
		Carton c5 = new Carton();
		c5.addArticle(new Article(1));
		c5.addArticle(new Article(6));
		cartons.add(c5);
		Carton c6 = new Carton();
		c6.addArticle(new Article(1));
		c6.addArticle(new Article(1));
		c6.addArticle(new Article(1));
		c6.addArticle(new Article(1));
		c6.addArticle(new Article(1));
		cartons.add(c6);
		
		Mockito.when(this.bestFitService.bestFit(Mockito.anyList())).thenReturn(cartons);
		
		this.fillService = new FillServiceImpl();
		this.fillService.setArticlesService(this.articlesService);
		this.fillService.setBestFitService(this.bestFitService);
		
	}
	
	
	@Test
	public void Test() throws TailleArticleException{
		String line = "111";
		String lineResult = this.fillService.fill(line);
		assertThat(lineResult).containsSequence(line,"16/8/163/9/16/11111");
	}

}
