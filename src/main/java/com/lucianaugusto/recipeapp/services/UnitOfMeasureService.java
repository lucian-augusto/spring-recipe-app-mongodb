package com.lucianaugusto.recipeapp.services;

import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listAllUoms();
}
