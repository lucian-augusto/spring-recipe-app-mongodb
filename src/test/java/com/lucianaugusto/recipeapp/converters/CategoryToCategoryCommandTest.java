package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.CategoryCommand;
import com.lucianaugusto.recipeapp.domain.Category;

public class CategoryToCategoryCommandTest {

	public static final String ID_VALUE = "123";
	public static final String DESCRIPTION = "description";

	CategoryToCategoryCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Category()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);

		// when
		CategoryCommand categoryCommand = converter.convert(category);

		// then
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
	}

}
