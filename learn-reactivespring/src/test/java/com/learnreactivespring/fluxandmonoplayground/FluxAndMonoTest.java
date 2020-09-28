package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

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

}
