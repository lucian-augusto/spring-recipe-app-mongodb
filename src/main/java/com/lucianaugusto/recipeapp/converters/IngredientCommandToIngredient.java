package com.lucianaugusto.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}

		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());

		if (source.getRecipeId() != null) {
			Recipe recipe = new Recipe();
			recipe.setId(source.getRecipeId());
			recipe.addIngredient(ingredient);
		}

		ingredient.setDescription(source.getDescription());
		ingredient.setAmount(source.getAmount());
		ingredient.setUom(uomConverter.convert(source.getUom()));

		return ingredient;
	}

}
