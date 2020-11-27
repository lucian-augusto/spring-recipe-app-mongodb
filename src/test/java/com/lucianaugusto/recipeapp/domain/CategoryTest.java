package com.lucianaugusto.recipeapp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

	Category category;

	@Before
	public void setUp() {
		category = new Category();
	}

	@Test
	public void testGetId() {
		String idValue = "123";

		category.setId(idValue);

		assertEquals(idValue, category.getId());
	}

	@Test
	public void testGetDescription() {

	}

	@Test
	public void testGetRecipes() {

	}

}
