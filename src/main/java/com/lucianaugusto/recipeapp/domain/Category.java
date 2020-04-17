package com.lucianaugusto.recipeapp.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"recipes"}) // Avoiding the Circular reference problem that results in a StackOverflow error by excluding 
// the equals and hashcodes for the dominant side of the relationship on the non-dominant class
@Entity
public class Category {

//	Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	@ManyToMany(mappedBy = "categories") // Maps this side of the Many-to-Many relationship to the one set in the categories property of the
	// Recipe class (Maps the Joining of the tables)
	private Set<Recipe> recipes;
}
