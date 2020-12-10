package com.lucianaugusto.recipeapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lucianaugusto.recipeapp.converters.RecipeCommandToRecipe;
import com.lucianaugusto.recipeapp.converters.RecipeToRecipeCommand;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void testGetRecipes() throws Exception {
		Recipe recipe = new Recipe();

		when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe));

		Flux<Recipe> recipes = recipeService.getRecipes();

		assertEquals(Long.valueOf(1L), recipes.count().block());
		verify(recipeReactiveRepository, times(1)).findAll();
		verify(recipeReactiveRepository, never()).findById(ArgumentMatchers.anyString());
	}

	@Test
	public void testGetRecipesById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(recipe));

		Mono<Recipe> foundRecipe = recipeService.findById("1");

		assertNotNull("Null recipe returned", foundRecipe);
		verify(recipeReactiveRepository, times(1)).findById(ArgumentMatchers.anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	public void testDeleteById() throws Exception {
		String id = "1";
		when(recipeReactiveRepository.deleteById(id)).thenReturn(Mono.empty());
		recipeService.deleteById(id);

		// No 'when', since the method has void return type

		verify(recipeReactiveRepository).deleteById(ArgumentMatchers.anyString());
	}

}
