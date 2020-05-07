package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
	
	private static final Long ID_VALUE = 1L;
	private static final String DESCRIPTION = "description";
	
	UnitOfMeasureCommandToUnitOfMeasure converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}
	
	@Test
	public void testNullParameter() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		// giver
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(ID_VALUE);
		command.setDescription(DESCRIPTION);
		
		// when
		UnitOfMeasure uom = converter.convert(command);
		
		// then
		assertEquals(ID_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
	}

}
