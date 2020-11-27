package com.lucianaugusto.recipeapp.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.converters.IngredientCommandToIngredient;
import com.lucianaugusto.recipeapp.converters.IngredientToIngredientCommand;
import com.lucianaugusto.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.lucianaugusto.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.RecipeRepository;
import com.lucianaugusto.recipeapp.repositories.UnitOfMeasureRepository;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	IngredientService ingredientService;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
				new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository,
				unitOfMeasureRepository, ingredientCommandToIngredient);
	}

	@Test
	public void findByRecipeIdAndIngredientId() throws Exception {

	}

	@Test
	public void findByRecipeIdAndIngredientIdHappyPath() {
		String id = "1";
		String id2 = "2";
		String id3 = "3";

		Recipe recipe = new Recipe();
		recipe.setId(id);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(id);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(id2);

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(id3);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);

		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(ArgumentMatchers.anyString())).thenReturn(recipeOptional);

		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(id, id3);

		assertEquals(id3, ingredientCommand.getId());
		verify(recipeRepository).findById(ArgumentMatchers.anyString());
	}

	@Test
	public void saveRecipeCommandTest() throws Exception {
		String recipeId = "1";
		String ingredientId = "2";
		IngredientCommand command = new IngredientCommand();
		command.setId(ingredientId);
		command.setRecipeId(recipeId);

		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(ingredientId);

		when(recipeRepository.findById(ArgumentMatchers.anyString())).thenReturn(recipeOptional);
		when(recipeRepository.save(ArgumentMatchers.any())).thenReturn(savedRecipe);

		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

		assertEquals(ingredientId, savedCommand.getId());
		verify(recipeRepository).findById(ArgumentMatchers.anyString());
		verify(recipeRepository).save(ArgumentMatchers.any(Recipe.class));
	}

	@Test
	public void testDeleteById() throws Exception {
		String recipeId = "1";
		String ingredientId = "2";
		// given
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);
		recipe.addIngredient(ingredient);

		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(ArgumentMatchers.anyString())).thenReturn(recipeOptional);

		// when
		ingredientService.deleteById(recipeId, ingredientId);

		// then
		verify(recipeRepository).findById(ArgumentMatchers.anyString());
		verify(recipeRepository).save(ArgumentMatchers.any(Recipe.class));
	}

}
