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
	
//	Getters and Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getPrepTime() {
		return prepTime;
	}
	
	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}
	
	public Integer getCookTime() {
		return cookTime;
	}
	
	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}
	public Integer getServings() {
		return servings;
	}
	
	public void setServings(Integer servings) {
		this.servings = servings;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDirections() {
		return directions;
	}
	
	public void setDirections(String directions) {
		this.directions = directions;
	}
	
	public Byte[] getImage() {
		return image;
	}
	
	public void setImage(Byte[] image) {
		this.image = image;
	}
	
	public Notes getNotes() {
		return notes;
	}
	
	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}	
}
