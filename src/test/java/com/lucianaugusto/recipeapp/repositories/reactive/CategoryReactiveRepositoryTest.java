package com.lucianaugusto.recipeapp.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucianaugusto.recipeapp.domain.Category;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	@Autowired
	CategoryReactiveRepository repository;

	@Before
	public void setUp() {
		repository.deleteAll().block();
	}

	@Test
	public void save() {
		Category category = new Category();
		category.setDescription("Test");

		repository.save(category).block();

		assertEquals(Long.valueOf(1L), repository.count().block());
	}

	@Test
	public void findByDescription() {
		Category category = new Category();
		category.setDescription("Test");

		repository.save(category).block();

		Category foundCategory = repository.findByDescription("Test").block();

		assertNotNull(foundCategory.getId());
	}
}
