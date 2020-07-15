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
import com.lucianaugusto.recipeapp.converters.IngredientToIngredientCommand;
import com.lucianaugusto.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.RecipeRepository;

public class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	@Mock
	RecipeRepository recipeRepository;
	
	IngredientService ingredientService;
	
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
	}
	
	@Test
	public void findByRecipeIdAndIngredientId() throws Exception {
		
	}
	
	@Test
	public void findByRecipeIdAndIngredientIdHappyPath() {
		Long id = 1L;
		Long id2 = 2L;
		Long id3 = 3L;
		
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
		
		when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(id, id3);
		
		assertEquals(id, ingredientCommand.getRecipeId());
		assertEquals(id3, ingredientCommand.getId());
		verify(recipeRepository).findById(ArgumentMatchers.anyLong());
	}

}
