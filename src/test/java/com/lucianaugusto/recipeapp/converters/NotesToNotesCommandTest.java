package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.NotesCommand;
import com.lucianaugusto.recipeapp.domain.Notes;

public class NotesToNotesCommandTest {
	
	public static final Long ID_VALUE = 1L;
	public static final String RECIPE_NOTES = "Notes";
	
	NotesToNotesCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new NotesToNotesCommand();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Notes()));
	}
	
	@Test
	public void testConvert() throws Exception {
		// Given
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);
		
		// When
		NotesCommand command = converter.convert(notes);
		
		// Then
		assertNotNull(command);
		assertEquals(ID_VALUE, command.getId());
		assertEquals(RECIPE_NOTES, command.getRecipeNotes());
	}

}
