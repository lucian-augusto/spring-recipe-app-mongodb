package com.lucianaugusto.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity // Making this class an entity
public class Recipe {

//	Fields
	@Id // Specifying that this field will be an Id value
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Setting up how the Id value is generated using an auto generating field (that
	// has a certain strategy to be generated). The "IDENTITY" Strategy is a special type to relational databases that will support automatic
	// generation of a sequence
	private Long id; // To be able to use this entity in a relational database, it must have an Id value
	
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	
	@Lob
	private String directions;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe") // Setting up the One-to-Many relationship between Recipe and Ingredient.
	// We're using the cascade type to persist all operations and map it by a property of the child class(recipe in this case), which is where
	// this object will be stored as a property.
	private Set<Ingredient> ingredients = new HashSet<Ingredient>(); // Avoiding null pointer errors
	
	@Lob // Using this annotation to specify a large object (Increasing the size limit of the Byte array, which will be created as a Binary
	// Large Object Field - Blob)
	private Byte[] image;
	
	@Enumerated(value = EnumType.STRING) // Saving the values of the enumeration as strings and not Ordinal numbers (which is the default)
	private Difficulty difficulty;
	
	@OneToOne(cascade = CascadeType.ALL) // Creating the relationship between Recipe and Notes (one to one mapping) and setting up a cascade
	// so that Recipe can be the owner
	private Notes notes;
	
	@ManyToMany
	@JoinTable(name = "recipe_category",
			joinColumns = @JoinColumn(name = "recipe_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")) // Creating the relationship between the elements that belong to each class
			// that are present in the DB. It's necessary to join the tables to avoid the creation of unnecessary tables in the relational
			// DB.
	private Set<Category> categories = new HashSet<Category>(); // Avoiding null pointer errors
	
//	Custom Setter for notes
	public void setNotes(Notes notes) {
		this.notes = notes;
		notes.setRecipe(this); // Automatically builds the association between the notes and adds it into the current recipe, encapsulating
		// the logic so that we don't have to do it outside the class
	}
	
	public Recipe addIngredient(Ingredient ingredient) { // Builds the association for the bi-directional association, encapsulating the
		// logic in a single spot
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}
}
