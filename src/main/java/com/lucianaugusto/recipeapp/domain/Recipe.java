package com.lucianaugusto.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {

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
			notes.setRecipe(this); // Automatically builds the association between the notes and adds it into the
									// current recipe, encapsulating
			// the logic so that we don't have to do it outside the class
		}
	}

	public Recipe addIngredient(Ingredient ingredient) { // Builds the association for the bi-directional association,
															// encapsulating the
		// logic in a single spot
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}
}
