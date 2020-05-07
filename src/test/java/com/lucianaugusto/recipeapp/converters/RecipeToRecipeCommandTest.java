package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.domain.Category;
import com.lucianaugusto.recipeapp.domain.Difficulty;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Notes;
import com.lucianaugusto.recipeapp.domain.Recipe;

public class RecipeToRecipeCommandTest {
	
	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "Description";
	public static final Integer PREP_TIME = Integer.valueOf("7");
	public static final Integer COOK_TIME = Integer.valueOf("5");
	public static final Integer SERVINGS = Integer.valueOf("3");
	public static final String SOURCE = "The Source";
	public static final String URL = "https://url.com";
	public static final String DIRECTIONS = "Do this!";
	public static final Difficulty DIFFICULTY = Difficulty.EASY;
	public static final Long NOTES_ID = 2L;
	public static final Long CATEGORY_ID_1 = 3L;
	public static final Long CATEGORY_ID_2 = 4L;
	public static final Long INGREDIENT_ID_1 = 5L;
	public static final Long INGREDIENT_ID_2 = 6L;
	
	RecipeToRecipeCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new NotesToNotesCommand());
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Recipe()));
	}

	@Test
	public void testConvert() throws Exception {
		// Given
		Notes notes = new Notes();
		notes.setId(NOTES_ID);
		
		Category category1 = new Category();
		category1.setId(CATEGORY_ID_1);
		Category category2 = new Category();
		category2.setId(CATEGORY_ID_2);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(INGREDIENT_ID_1);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(INGREDIENT_ID_2);
		
		Recipe recipe = new Recipe();
		recipe.setId(ID_VALUE);
		recipe.setDescription(DESCRIPTION);
		recipe.setPrepTime(PREP_TIME);
		recipe.setCookTime(COOK_TIME);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);
		recipe.setDirections(DIRECTIONS);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setNotes(notes);
		recipe.getCategories().add(category1);
		recipe.getCategories().add(category2);
		recipe.getIngredients().add(ingredient1);
		recipe.getIngredients().add(ingredient2);
		
		// When
		RecipeCommand command = converter.convert(recipe);
		
		// Then
		assertNotNull(command);
		assertEquals(ID_VALUE, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(PREP_TIME, command.getPrepTime());
		assertEquals(COOK_TIME, command.getCookTime());
		assertEquals(SERVINGS, command.getServings());
		assertEquals(SOURCE, command.getSource());
		assertEquals(URL, command.getUrl());
		assertEquals(DIRECTIONS, command.getDirections());
		assertEquals(DIFFICULTY, command.getDifficulty());
		assertEquals(NOTES_ID, command.getNotes().getId());
		assertEquals(2, command.getCategories().size());
		assertEquals(2, command.getIngredients().size());
	}

}
