package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;

public class IngredientToIngredientCommandTest {

	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";
	public static final BigDecimal AMOUNT = new BigDecimal(1);
	public static final Long UOM_ID = 2L;
	
	IngredientToIngredientCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testConvertEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Ingredient()));
	}

	@Test
	public void testConvert() throws Exception {
		// Given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(UOM_ID);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		ingredient.setUom(uom);
		
		// When
		IngredientCommand command = converter.convert(ingredient);
		
		// Then
		assertNotNull(command);
		assertNotNull(command.getUnitOfMeasure());
		assertEquals(ID_VALUE, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(AMOUNT, command.getAmount());
		
	}
	
	@Test
	public void testConvertNullUOM() throws Exception {
		// Given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		
		// When
		IngredientCommand command = converter.convert(ingredient);
		
		// Then
		assertNotNull(command);
		assertNull(command.getUnitOfMeasure());
		assertEquals(ID_VALUE, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(AMOUNT, command.getAmount());
		
	}

}
