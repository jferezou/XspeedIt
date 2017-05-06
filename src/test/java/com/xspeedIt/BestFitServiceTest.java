package com.xspeedIt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xspeedIt.exception.AlgoException;
import com.xspeedIt.pojo.Article;
import com.xspeedIt.pojo.Carton;
import com.xspeedIt.service.BestFitService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class BestFitServiceTest {
	@Autowired
	private BestFitService bestFitService;
	
	
	@Test
	public void testBestFit() throws AlgoException {
		// 3 articles de taille 9 => 3 cartons
		List<Article> articles = new ArrayList<>();
		articles.add(new Article(9));
		articles.add(new Article(9));
		articles.add(new Article(9));
		List<Carton> cartons = this.bestFitService.bestFit(articles);
		assertThat(cartons.size()).isEqualTo(3);
		

		// 10 articles de taille 1 => 1 carton
		articles.clear();
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		articles.add(new Article(1));
		cartons = this.bestFitService.bestFit(articles);
		assertThat(cartons.size()).isEqualTo(1);


		// 2 articles de 5, 1 de 6, 1 de 4, 1 de 1 => 3 cartons
		articles.clear();
		articles.add(new Article(5));
		articles.add(new Article(1));
		articles.add(new Article(6));
		articles.add(new Article(4));
		articles.add(new Article(5));
		cartons = this.bestFitService.bestFit(articles);
		assertThat(cartons.size()).isEqualTo(3);

		// 163841689525773 => 8 cartons
		articles.clear();
		articles.add(new Article(1));
		articles.add(new Article(6));
		articles.add(new Article(3));
		articles.add(new Article(8));
		articles.add(new Article(4));
		articles.add(new Article(1));
		articles.add(new Article(6));
		articles.add(new Article(8));
		articles.add(new Article(9));
		articles.add(new Article(5));
		articles.add(new Article(2));
		articles.add(new Article(5));
		articles.add(new Article(7));
		articles.add(new Article(7));
		articles.add(new Article(3));
		cartons = this.bestFitService.bestFit(articles);
		assertThat(cartons.size()).isEqualTo(8);
	}
	
	@Test(expected=AlgoException.class)
	@Ignore
	public void testBestFitException() throws AlgoException {
		// si j'ai un chiffre plus grand que 9
		List<Article> articles = new ArrayList<>();
		articles.add(new Article(10));
		this.bestFitService.bestFit(articles);
	}

}
