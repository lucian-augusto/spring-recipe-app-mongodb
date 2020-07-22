package com.lucianaugusto.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"}) // Avoiding the Circular reference problem that results in a StackOverflow error by excluding 
//the equals and hashcodes for the dominant side of the relationship on the non-dominant class
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

}
