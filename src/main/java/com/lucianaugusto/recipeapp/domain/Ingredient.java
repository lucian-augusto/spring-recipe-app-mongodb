package com.lucianaugusto.recipeapp.domain;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = { "recipe" }) // Avoiding the Circular reference problem that results in a StackOverflow
// error by excluding the equals and hashcodes for the dominant side of the relationship on the non-dominant class
public class Ingredient {

	private Long Id;

	private String description;
	private BigDecimal amount;

	private UnitOfMeasure uom;

	private Recipe recipe;

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
		this.description = description;
		this.amount = amount;
		this.uom = uom;
		this.recipe = recipe;
	}

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
		this.description = description;
		this.amount = amount;
		this.uom = uom;
	}

	public Ingredient() {
	}
}
