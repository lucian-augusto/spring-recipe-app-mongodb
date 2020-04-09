package com.lucianaugusto.recipeapp.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingredient {

//	Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String description;
	private BigDecimal amount;
	
	@OneToOne(fetch = FetchType.EAGER) // Explicitly setting the fetch type
	private UnitOfMeasure oum;
	
	@ManyToOne // No cascade (default value = none)
	private Recipe recipe;

//	Getters and Setters
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public UnitOfMeasure getOum() {
		return oum;
	}

	public void setOum(UnitOfMeasure oum) {
		this.oum = oum;
	}
	
}
