package com.learnreactivespring.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {
	
	List<String> names= Arrays.asList("Adam", "Anna", "Jack", "Jenny");
	
	@Test
	void fluxUsingIterable() {
		
		Flux<String> namesFlux= Flux.fromIterable(names)
				.log();
		
		StepVerifier.create(namesFlux)
			.expectNext("Adam", "Anna", "Jack", "Jenny")
			.verifyComplete();
		
	}
	
	@Test
	void fluxUsingArray() {
		String[] names= new String[] {"Adam", "Anna", "Jack", "Jenny"};
		
		Flux<String> namesFlux= Flux.fromArray(names)
				.log();
		
		StepVerifier.create(namesFlux)
		.expectNext("Adam", "Anna", "Jack", "Jenny")
		.verifyComplete();
	}
	
	@Test
	void monoUsingSupplier() {
		Supplier<String> stringSupplier= () -> "Adam";
		
		Mono<String> stringMono= Mono.fromSupplier(stringSupplier)
				.log();
		
		StepVerifier.create(stringMono)
			.expectNext("Adam")
			.verifyComplete();
		
		
	}

}
