package com.learnreactivespring.fluxandmonoplayground;


import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	
	@Test
	void fluxTest() {
		
		Flux<String> stringFlux= Flux.just("Spring", "Spring Boot", "Reactive Spring")
				//.concatWith(Flux.error(new RuntimeException("Exception occurred")))
				.concatWith(Flux.just("... after Error"))
				.log();
		
		stringFlux
			.subscribe(System.out::println,
					e -> System.err.println(e)
					, () -> System.out.println("Completed"));
	}

	
	@Test
	void fluxTestElements_withoutError() {
		Flux<String> stringFlux= Flux.just("Spring", "Spring Boot", "Reactive Spring")
			.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring")
			.expectNext("Spring Boot")
			.expectNext("Reactive Spring")
			.verifyComplete();
		
		
	}
	
	@Test
	void fluxTestElements_withError() {
		Flux<String> stringFlux= Flux.just("Spring", "Spring Boot", "Reactive Spring")
			.concatWith(Flux.error(new RuntimeException("Exception occurred")))
			.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring")
			.expectNext("Spring Boot")
			.expectNext("Reactive Spring")
			.expectError(RuntimeException.class)
			.verify();
	}
	
	@Test
	void fluxTestElementsCount_withError() {
		Flux<String> stringFlux= Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception 253 occurred")))
				.log();
			
		StepVerifier.create(stringFlux)
			.expectNextCount(3)
			.expectErrorMessage("Exception 253 occurred")
			.verify();
		
	}
}
