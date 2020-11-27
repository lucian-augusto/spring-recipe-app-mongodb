package com.lucianaugusto.recipeapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = { "recipe" }) // Avoiding the Circular reference problem that results in a StackOverflow
											// error by excluding
//the equals and hashcodes for the dominant side of the relationship on the non-dominant class
public class Notes {

	private String id;

	private Recipe recipe;

	private String recipeNotes;

}
