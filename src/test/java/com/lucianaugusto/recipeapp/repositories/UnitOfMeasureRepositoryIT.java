package com.lucianaugusto.recipeapp.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucianaugusto.recipeapp.bootstrap.RecipeBootstrap;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@Before
	public void setUp() throws Exception {
		// Cleaning DB cache form previous tests
		unitOfMeasureRepository.deleteAll();
		categoryRepository.deleteAll();
		recipeRepository.deleteAll();

		// Initializing bootstrapped DB data
		RecipeBootstrap bootstrap = new RecipeBootstrap(recipeRepository, categoryRepository, unitOfMeasureRepository);

		bootstrap.onApplicationEvent(null);
	}

	@Test
	public void testFindByDescription() {
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

		assertEquals("Teaspoon", uomOptional.get().getDescription());
	}

	@Test
	public void testFindByDescriptionCup() {
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");

		assertEquals("Cup", uomOptional.get().getDescription());
	}

}
