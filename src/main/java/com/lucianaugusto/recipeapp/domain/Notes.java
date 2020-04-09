package com.lucianaugusto.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Notes {

//	Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne // The cascade is not necessary since it's the Recipe that OWNS the Notes (So we can delete the notes without deleting the
	// Recipe but deleting the Recipe will ALSO delete the Notes)
	private Recipe recipe;
	
	@Lob // Using this annotation to specify a large object (Increasing the size limit of the Notes which will be created as a Class
	// Large Object Field - Clob)
	private String recipeNotes;
	
//	Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public String getRecipeNotes() {
		return recipeNotes;
	}
	public void setRecipeNotes(String recipeNotes) {
		this.recipeNotes = recipeNotes;
	}
	
	
	
}
