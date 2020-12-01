package com.lucianaugusto.recipeapp.repositories.reactive;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucianaugusto.recipeapp.domain.Recipe;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

	@Autowired
	RecipeReactiveRepository reactiveRepository;

	@Before
	public void setUp() {
		reactiveRepository.deleteAll().block();
	}

	@Test
	public void RecipeSave() {
		Recipe recipe = new Recipe();
		recipe.setDescription("Yummy");

		reactiveRepository.save(recipe).block();

		assertEquals(Long.valueOf(1L), reactiveRepository.count().block());
	}
}
