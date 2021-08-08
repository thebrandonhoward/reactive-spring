package com.thebrandonhoward.demo.reactivespring;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Log4j2
public class FluxMix {

    public static void main(String... args) throws Exception {
        //doFlux();
        //doFlux2();
        //Thread.sleep(50000);
        findHighestSpeed();
    }

    static void doFlux() {
        Flux<Integer> mix = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                                .filter( idCheck(0) )
                                .map( Integer::valueOf )
                                .delayElements( Duration.ofSeconds(2) );
                                //.doOnNext( id -> print(id) );

        mix.subscribe(log::info);
    }

    static void doFlux2() {
        Flux<Integer> mix = Flux.just(11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
                                .filter( id -> id > 0 )
                                .map( Integer::valueOf )
                                .delayElements( Duration.ofSeconds(2) );
                                //.doOnNext( (final Integer id) -> print(id) );

        mix.subscribe( (final Integer id) -> log.info(id) );
    }

    static void print(int id) {
        System.out.println(id);
    }

    static final Predicate<Integer> idPredicate = id -> id > 0;

    static Predicate<Integer> idCheck(Integer ceiling) {
        return id -> {return id > ceiling;};
    }

    // --------------------------------------------------------

    public static void findHighestSpeed() {
        String[] speedArray = {"200", "100", "100", "500", "100", "940"};

        List<Integer> sorted = Stream.of(speedArray)
                .distinct()
                .sorted( (s1,s2) -> Integer.valueOf(s1) <= Integer.valueOf(s2) ? 1 : -1)
                .map(speed -> Integer.valueOf(speed))
                .collect(Collectors.toList());

//        List<Integer> sorted = Stream.of(speedArray)
//                .distinct()
//                .sorted(Comparator.reverseOrder())
//                .map(speed -> Integer.valueOf(speed))
//                .collect(Collectors.toList());

        sorted.forEach(log::info);

        log.info("Max: {}", sorted.stream().mapToInt(speed -> speed.intValue()).summaryStatistics().getMax());
        log.info("Min: {}", sorted.stream().mapToInt(speed -> speed.intValue()).summaryStatistics().getMin());
    }

}