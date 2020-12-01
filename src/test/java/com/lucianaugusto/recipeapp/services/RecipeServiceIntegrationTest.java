package com.lucianaugusto.recipeapp.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.converters.RecipeCommandToRecipe;
import com.lucianaugusto.recipeapp.converters.RecipeToRecipeCommand;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIntegrationTest {

	public static final String NEW_DESCRIPTION = "New Description";

	@Autowired
	RecipeService recipeService;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Test
	public void testSaveOfDescription() throws Exception {
		// Given
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

		// When
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

		// Then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
	}

}
