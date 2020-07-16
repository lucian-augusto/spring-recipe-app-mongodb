package com.lucianaugusto.recipeapp.services;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	
}
