package com.undevined.rxjava.chapter._6;

import com.undevined.rxjava.chapter.Util;
import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Switching {

    public static void main(String[] args) {
        Observable<String> items = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota");
        Observable<String> processedStrings = items.concatMap(s -> Observable.just(s).delay(ThreadLocalRandom.current().nextInt(2000), TimeUnit.MILLISECONDS));
        justSubscribe(processedStrings);
        replayEvery5SecondsWithDispose(processedStrings);
    }

    private static void justSubscribe(Observable obs) {
        System.out.println("## Just run ##");
        obs.subscribe(System.out::println);
        Util.sleep(20000);
    }

    private static void replayEvery5SecondsWithDispose(Observable obs) {
        System.out.println("## rerun every 5 seconds and dispose... ###");
        Observable.interval(5, TimeUnit.SECONDS)
                .switchMap(i -> obs.doOnDispose(() -> System.out.println("Disposing...")))
                .subscribe(System.out::println);
        Util.sleep(20000);
    }
}
