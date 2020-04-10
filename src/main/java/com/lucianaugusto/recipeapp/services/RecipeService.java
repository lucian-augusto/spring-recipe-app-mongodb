package com.lucianaugusto.recipeapp.services;

import java.util.Set;

import com.lucianaugusto.recipeapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
}
