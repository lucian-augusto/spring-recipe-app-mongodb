package com.lucianaugusto.recipeapp.services;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

	IngredientCommand saveIngredientCommand(IngredientCommand command);

	void deleteById(String recipeId, String ingredientId);
}
