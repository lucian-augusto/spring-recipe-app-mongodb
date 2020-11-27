package com.lucianaugusto.recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.converters.IngredientCommandToIngredient;
import com.lucianaugusto.recipeapp.converters.IngredientToIngredientCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.RecipeRepository;
import com.lucianaugusto.recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

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

	@Transactional
	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {
			// TODO Implement error
			log.debug("Recipe id: " + command.getRecipeId() + " not found");
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient foundIngredient = ingredientOptional.get();
				foundIngredient.setDescription(command.getDescription());
				foundIngredient.setAmount(command.getAmount());
				foundIngredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM not found"))); // TODO address this
			} else {
				// Add new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeRepository.save(recipe);

			Optional<Ingredient> savedIngredientsOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst();

			if (!savedIngredientsOptional.isPresent()) {
				// Not totally safe, but best guess
				savedIngredientsOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription()
								.equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUom().getId()
								.equals(command.getUom().getId()))
						.findFirst();
			}

			return ingredientToIngredientCommand.convert(savedIngredientsOptional.get());
		}
	}

	@Override
	public void deleteById(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();
			log.debug("Found recipe");

			Optional<Ingredient> ingreOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
			if (ingreOptional.isPresent()) {
				Ingredient ingredientToDelete = ingreOptional.get();
				recipe.getIngredients().remove(ingredientToDelete);
				recipeRepository.save(recipe);
			}
		} else {
			log.debug("Recipe Id not found. Id: " + recipeId);
		}

	}

}
