package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.NotesCommand;
import com.lucianaugusto.recipeapp.domain.Notes;

public class NotesCommandToNotesTest {

	public static final String ID_VALUE = "123";
	public static final String RECIPE_NOTES = "Notes";

	NotesCommandToNotes converter;

	@Before
	public void setUp() throws Exception {
		converter = new NotesCommandToNotes();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new NotesCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		// Given
		NotesCommand command = new NotesCommand();
		command.setId(ID_VALUE);
		command.setRecipeNotes(RECIPE_NOTES);

		// When
		Notes notes = converter.convert(command);

		// Then
		assertNotNull(notes);
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
	}

}
