package com.lucianaugusto.recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.converters.IngredientToIngredientCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final RecipeRepository recipeRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeRepository = recipeRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent()) {
			// TODO implement error handling
			log.error("recipe id: " + recipeId + " not found");
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		if (!ingredientCommandOptional.isPresent()) {
			// TODO implement error handling
			log.error("ingredient id: " + ingredientId + " not found");
		}
		
		return ingredientCommandOptional.get();
	}
	
	
}
