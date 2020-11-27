package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.CategoryCommand;
import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.commands.NotesCommand;
import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.domain.Difficulty;
import com.lucianaugusto.recipeapp.domain.Recipe;

public class RecipeCommandToRecipeTest {

	public static final String ID_VALUE = "123";
	public static final String DESCRIPTION = "Description";
	public static final Integer PREP_TIME = Integer.valueOf("7");
	public static final Integer COOK_TIME = Integer.valueOf("5");
	public static final Integer SERVINGS = Integer.valueOf("3");
	public static final String SOURCE = "The Source";
	public static final String URL = "https://url.com";
	public static final String DIRECTIONS = "Do this!";
	public static final Difficulty DIFFICULTY = Difficulty.EASY;
	public static final String NOTES_ID = "2";
	public static final String CATEGORY_ID_1 = "3";
	public static final String CATEGORY_ID_2 = "4";
	public static final String INGREDIENT_ID_1 = "5";
	public static final String INGREDIENT_ID_2 = "6";

	RecipeCommandToRecipe converter;

	@Before
	public void setUp() throws Exception {
		converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				new NotesCommandToNotes());
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new RecipeCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		// Given
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(NOTES_ID);

		CategoryCommand categoryCommand1 = new CategoryCommand();
		categoryCommand1.setId(CATEGORY_ID_1);
		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand2.setId(CATEGORY_ID_2);

		IngredientCommand ingredientCommand1 = new IngredientCommand();
		ingredientCommand1.setId(INGREDIENT_ID_1);
		IngredientCommand ingredientCommand2 = new IngredientCommand();
		ingredientCommand2.setId(INGREDIENT_ID_2);

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ID_VALUE);
		recipeCommand.setDescription(DESCRIPTION);
		recipeCommand.setPrepTime(PREP_TIME);
		recipeCommand.setCookTime(COOK_TIME);
		recipeCommand.setServings(SERVINGS);
		recipeCommand.setSource(SOURCE);
		recipeCommand.setUrl(URL);
		recipeCommand.setDirections(DIRECTIONS);
		recipeCommand.setDifficulty(DIFFICULTY);
		recipeCommand.setNotes(notesCommand);
		recipeCommand.getCategories().add(categoryCommand1);
		recipeCommand.getCategories().add(categoryCommand2);
		recipeCommand.getIngredients().add(ingredientCommand1);
		recipeCommand.getIngredients().add(ingredientCommand2);

		// When
		Recipe recipe = converter.convert(recipeCommand);

		// Then
		assertNotNull(recipe);
		assertEquals(ID_VALUE, recipe.getId());
		assertEquals(DESCRIPTION, recipe.getDescription());
		assertEquals(PREP_TIME, recipe.getPrepTime());
		assertEquals(COOK_TIME, recipe.getCookTime());
		assertEquals(SERVINGS, recipe.getServings());
		assertEquals(SOURCE, recipe.getSource());
		assertEquals(URL, recipe.getUrl());
		assertEquals(DIRECTIONS, recipe.getDirections());
		assertEquals(DIFFICULTY, recipe.getDifficulty());
		assertEquals(NOTES_ID, recipe.getNotes().getId());
		assertEquals(2, recipe.getCategories().size());
		assertEquals(2, recipe.getIngredients().size());
	}

}
