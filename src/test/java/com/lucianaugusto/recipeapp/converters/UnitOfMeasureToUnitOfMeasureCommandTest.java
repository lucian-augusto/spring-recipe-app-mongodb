package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
	
	private static final Long ID_VALUE = 1L;
	private static final String DESCRIPTION = "description";
	
	UnitOfMeasureToUnitOfMeasureCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(ID_VALUE);
		uom.setDescription(DESCRIPTION);
		
		// when
		UnitOfMeasureCommand command = converter.convert(uom);
		
		// then
		assertEquals(ID_VALUE, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
	}

}
