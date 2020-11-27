package com.lucianaugusto.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lucianaugusto.recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
