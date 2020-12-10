package com.lucianaugusto.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Recipe {

	@Id
	private String id; // To be able to use this entity in a relational database, it must have an Id
						// value

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;

	private String directions;

	private Set<Ingredient> ingredients = new HashSet<>(); // Avoiding null pointer errors

	private Byte[] image;

	private Difficulty difficulty;

	private Notes notes;

	private Set<Category> categories = new HashSet<>(); // Avoiding null pointer errors

//	Custom Setter for notes
	public void setNotes(Notes notes) {
		if (notes != null) {
			this.notes = notes;
		}
	}

	public Recipe addIngredient(Ingredient ingredient) { // Builds the association for the bi-directional association,
															// encapsulating the
		// logic in a single spot
		// ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}
}
