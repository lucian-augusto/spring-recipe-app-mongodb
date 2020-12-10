package com.lucianaugusto.recipeapp.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;
import com.lucianaugusto.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTest {

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService unitOfMeasureService;

	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository,
				unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void testListAllUoms() throws Exception {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("1");

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom1.setId("2");

		when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

		List<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms().collectList().block();

		assertEquals(2, commands.size());
		verify(unitOfMeasureReactiveRepository).findAll();
	}

}
