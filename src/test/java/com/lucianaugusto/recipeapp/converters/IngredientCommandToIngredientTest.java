package com.lucianaugusto.recipeapp.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.Ingredient;

public class IngredientCommandToIngredientTest {
	
	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";
	public static final BigDecimal AMOUNT = new BigDecimal(1);
	public static final Long UOM_ID = 2L;
	
	IngredientCommandToIngredient converter;

	@Before
	public void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setDescription(DESCRIPTION);
		command.setAmount(AMOUNT);
		
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(UOM_ID);
		
		command.setUom(uomCommand);
		
		// when
		Ingredient ingredient = converter.convert(command);
		
		// then
		assertNotNull(ingredient);
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertNotNull(ingredient.getUom());
	}
	
	@Test
	public void testConvertWithNullUOM() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setDescription(DESCRIPTION);
		command.setAmount(AMOUNT);
		
		// when
		Ingredient ingredient = converter.convert(command);
		
		// then
		assertNotNull(ingredient);
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertNull(ingredient.getUom());
	}

}
