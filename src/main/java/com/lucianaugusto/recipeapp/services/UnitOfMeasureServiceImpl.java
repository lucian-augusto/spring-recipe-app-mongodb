package com.lucianaugusto.recipeapp.services;

import org.springframework.stereotype.Service;

import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Flux<UnitOfMeasureCommand> listAllUoms() {
		return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);

	}

}
