package com.lucianaugusto.recipeapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.converters.RecipeCommandToRecipe;
import com.lucianaugusto.recipeapp.converters.RecipeToRecipeCommand;
import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.reactive.RecipeReactiveRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
			RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes() {
		log.debug("I'm in the service");

		return recipeReactiveRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeReactiveRepository.findById(id);
	}

	@Override
	@Transactional
	public Mono<RecipeCommand> findCommandById(String id) {
		return recipeReactiveRepository.findById(id).map(recipe -> {
			RecipeCommand command = recipeToRecipeCommand.convert(recipe);

			command.getIngredients().forEach(ingredient -> ingredient.setRecipeId(id));
			return command;
		});
	}

	@Override
	@Transactional
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
		return recipeReactiveRepository.save(recipeCommandToRecipe.convert(recipeCommand))//
				.map(recipeToRecipeCommand::convert);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		recipeReactiveRepository.deleteById(id).block();

		return Mono.empty();
	}

}
