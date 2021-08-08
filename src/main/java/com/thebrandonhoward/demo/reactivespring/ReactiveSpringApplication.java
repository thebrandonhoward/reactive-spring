package com.thebrandonhoward.demo.reactivespring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringApplication.class, args);
    }

}

@Log4j2
@RestController
class ReactiveSpringController {

    @GetMapping( value = "/demo/start"
                ,produces = MediaType.TEXT_EVENT_STREAM_VALUE )
    public Flux<Price> streamData() {
        return Flux.fromStream(Stream.generate(() -> getPrice()))
                    .delayElements(Duration.ofSeconds(2))
                    .doOnNext(price -> log.info(price));
    }

    private Price getPrice() {
        return new Price("Product_".concat(String.valueOf(Math.random())), Math.random());
    }
}

@Data
@AllArgsConstructor
class Price {
    private String product;
    private double price;
}
