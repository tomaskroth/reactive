package com.waes.reactive.service;

import com.waes.reactive.model.Quote;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class QuoteGeneratorServiceImplTest {

    private QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();

    @Test
    public void fetchQuoteStreamCountDown() throws InterruptedException {

        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        Consumer<Quote> println = System.out::println;

        Consumer<Throwable> errorHandler = e -> System.out.println(e.getMessage());

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable allDone = () -> countDownLatch.countDown();

        quoteFlux.take(10)
                 .subscribe(println, errorHandler, allDone);

        countDownLatch.await();
    }

}