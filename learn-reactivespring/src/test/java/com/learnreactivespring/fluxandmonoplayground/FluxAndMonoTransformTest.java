package com.learnreactivespring.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformTest {

	List<String> names= Arrays.asList("Adam", "Anna", "Jack", "Jenny");
	
	@Test
	void transformUsingMap() {
		Flux<String> namesFlux= Flux.fromIterable(names)
				.map(String::toUpperCase)
				.log();
		
		StepVerifier.create(namesFlux)
			.expectNext("ADAM", "ANNA", "JACK", "JENNY")
			.verifyComplete();
	}
	
	@Test
	void transformUsingFlatMap() {
		Flux<String> stringFlux= Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
				.flatMap(s -> {  // for external-service call (e.g. ddbb), that returns a Flux
					return Flux.fromIterable(convertToList(s));
				})
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNextCount(12)
			.verifyComplete();
	}

	private List<String> convertToList(String s) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList(s, s + "newValue");
	}
	
	@Test 
	@DisplayName("parallel")
	void transformUsingFlatMap_usingParallel() {
		Flux<String> stringFlux= Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
				.window(2) // Flux<Flux<String>>  ->   (A,B), (C,D), (E,F)
				.flatMap( s -> 
					s.map(this::convertToList).subscribeOn(Schedulers.parallel()) // Flux<List<String>>
						.flatMap(s2 -> Flux.fromIterable(s2)) // Flux<String>
				)
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNextCount(12)
			.verifyComplete();
	}
	
	@Test
	@DisplayName("parallel & order")
	void transformUsingFlatMap_usingParallel_order() {
		Flux<String> stringFlux= Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
				.window(2) // Flux<Flux<String>>  ->   (A,B), (C,D), (E,F)
				.flatMapSequential( s -> 
					s.map(this::convertToList).subscribeOn(Schedulers.parallel()) // Flux<List<String>>
						.flatMap(s2 -> Flux.fromIterable(s2)) // Flux<String>
				)
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNextCount(12)
			.verifyComplete();
	}
	
}
