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
import com.lucianaugusto.recipeapp.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIntegrationTest {

	public static final String NEW_DESCRIPTION = "New Description";

	@Autowired
	RecipeService recipeService;

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;

	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Test
	public void testSaveOfDescription() throws Exception {
		// Given
		Flux<Recipe> recipes = recipeReactiveRepository.findAll();
		Recipe testRecipe = recipes.blockFirst();
		RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

		// When
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		Mono<RecipeCommand> savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

		// Then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.block().getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.block().getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.block().getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.block().getIngredients().size());
	}

}
