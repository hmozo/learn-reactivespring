package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {
	
	@Test
	void fluxErrorHandling_onErrorResumen() {
		
		Flux<String> stringFlux= Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occurred!")))
				.concatWith(Flux.just("D"))
				.onErrorResume(e->{  // this block gets executed
					System.out.println("Exception is: " + e);
					return Flux.just("default", "default1");
				})
				.log();
		
		StepVerifier.create(stringFlux)
			.expectSubscription()
			.expectNext("A", "B", "C")
			//.expectError(RuntimeException.class)
			//.verify();
			.expectNext("default", "default1")
			.verifyComplete();
	}
	
	@Test
	void fluxErrorHandling_OnErrorReturn() {
		
		Flux<String> stringFlux= Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occurred!")))
				.concatWith(Flux.just("D"))
				.onErrorReturn("default")
				.log();
		
		StepVerifier.create(stringFlux)
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("default")
			.verifyComplete();
	}
	
	@Test
	void fluxErrorHandling_OnErrorMap() {
		
		Flux<String> stringFlux= Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occurred!")))
				.concatWith(Flux.just("D"))
				.onErrorMap(e-> new CustomException(e))
				.log();
		
		StepVerifier.create(stringFlux)
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectError(CustomException.class)
			.verify();
	}
	
	@Test
	void fluxErrorHandling_WithRetry() {
		
		Flux<String> stringFlux= Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occurred!")))
				.concatWith(Flux.just("D"))
				.onErrorMap(e-> new CustomException(e))
				.retry(2)
				.log();
		
		StepVerifier.create(stringFlux)
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectError(CustomException.class)
			.verify();
	}

}
