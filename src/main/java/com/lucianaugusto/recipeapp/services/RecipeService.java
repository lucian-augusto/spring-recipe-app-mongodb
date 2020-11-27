package com.lucianaugusto.recipeapp.services;

import java.util.Set;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(String id);

	RecipeCommand findCommandById(String id);

	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

	void deleteById(String id);
}
