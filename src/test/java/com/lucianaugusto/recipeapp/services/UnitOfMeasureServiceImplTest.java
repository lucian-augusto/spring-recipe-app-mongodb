package com.lucianaugusto.recipeapp.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;
import com.lucianaugusto.recipeapp.repositories.UnitOfMeasureRepository;

public class UnitOfMeasureServiceImplTest {
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService unitOfMeasureService;

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}
	
	@Test
	public void testListAllUoms() throws Exception {
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<UnitOfMeasure>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		unitOfMeasures.add(uom1);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom1.setId(2L);
		unitOfMeasures.add(uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
		
		Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();
		
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository).findAll();
	}

}
