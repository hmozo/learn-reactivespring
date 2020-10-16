package com.learnreactivespring.fluxandmonoplayground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoWithTimeTest {
	
	@Test
	void infiniteSequence() throws InterruptedException {
		
		Flux<Long> infiniteFlux= Flux.interval(Duration.ofMillis(200))
				.log(); //long values (0,...)
		
		infiniteFlux.subscribe(element-> System.out.println("Value is: " + element));
		
		Thread.sleep(3000);
		
	}
	
	@Test
	void infiniteSequenceTest() throws InterruptedException {
		
		Flux<Long> infiniteFlux= Flux.interval(Duration.ofMillis(200))
				.take(3)
				.log(); //long values (0,...)
		
		infiniteFlux.subscribe(element-> System.out.println("Value is: " + element));
		
		StepVerifier.create(infiniteFlux)
			.expectSubscription()
			.expectNext(0L, 1L, 2L)
			.expectComplete();
	}
	
	@Test
	void infiniteSequenceMap() throws InterruptedException {
		
		Flux<Integer> infiniteFlux= Flux.interval(Duration.ofMillis(200))
				.map(l -> l.intValue())
				.take(3)
				.log(); //long values (0,...)
		
		infiniteFlux.subscribe(element-> System.out.println("Value is: " + element));
		
		StepVerifier.create(infiniteFlux)
			.expectSubscription()
			.expectNext(0, 1, 2)
			.expectComplete();
		
	}

}
