package com.lucianaugusto.recipeapp.domain;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = { "recipes" }) // Avoiding the Circular reference problem that results in a StackOverflow
// error by excluding the equals and hashcodes for the dominant side of the relationship on the non-dominant class
public class Category {

	private String id;

	private String description;

	private Set<Recipe> recipes;
}
