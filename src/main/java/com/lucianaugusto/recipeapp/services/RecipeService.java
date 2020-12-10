package com.lucianaugusto.recipeapp.services;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.domain.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

	Flux<Recipe> getRecipes();

	Mono<Recipe> findById(String id);

	Mono<RecipeCommand> findCommandById(String id);

	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);

	Mono<Void> deleteById(String id);
}
