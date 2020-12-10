package com.lucianaugusto.recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.converters.IngredientCommandToIngredient;
import com.lucianaugusto.recipeapp.converters.IngredientToIngredientCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.lucianaugusto.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeReactiveRepository recipeReactiveRepository;
	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			RecipeReactiveRepository recipeReactiveRepositoryRepository,
			UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepositoryRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeReactiveRepository = recipeReactiveRepositoryRepository;
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepositoryRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

		return recipeReactiveRepository.findById(recipeId) //
				.flatMapIterable(Recipe::getIngredients)//
				.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))//
				.single()//
				.map(ingredient -> {
					IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
					command.setRecipeId(recipeId);
					return command;
				});
	}

	@Transactional
	@Override
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
		Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId()).block();

		if (recipe == null) {
			// TODO Implement error
			log.debug("Recipe id: " + command.getRecipeId() + " not found");
			return Mono.just(new IngredientCommand());
		} else {

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient foundIngredient = ingredientOptional.get();
				foundIngredient.setDescription(command.getDescription());
				foundIngredient.setAmount(command.getAmount());
				foundIngredient.setUom(unitOfMeasureReactiveRepository.findById(command.getUom().getId()).block());

				if (foundIngredient == null) {
					new RuntimeException("UOM not Found");
				}
			} else {
				// Add new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

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

			IngredientCommand savedIngredientCommand = ingredientToIngredientCommand
					.convert(savedIngredientsOptional.get());
			savedIngredientCommand.setRecipeId(recipe.getId());

			return Mono.just(savedIngredientCommand);
		}
	}

	@Override
	public Mono<Void> deleteById(String recipeId, String ingredientId) {

		Recipe recipe = recipeReactiveRepository.findById(recipeId).block();

		if (recipe != null) {

			log.debug("Found recipe");

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient ingredientToDelete = ingredientOptional.get();
				recipe.getIngredients().remove(ingredientToDelete);
				recipeReactiveRepository.save(recipe).block();
			}
		} else {
			log.debug("Recipe Id not found. Id: " + recipeId);
		}

		return Mono.empty();
	}

}
