package com.undevined.rxjava.chapter._6;

import com.undevined.rxjava.chapter.Util;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Throttling {

    public static void main(String[] args) {
        Observable<String> source1 = Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 100)
                .map(i -> "Source 1: " + i)
                .take(10);

        Observable<String> source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300)
                .map(i -> "Source 2: " + i)
                .take(3);

        Observable<String> source3 = Observable.interval(2000, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 2000)
                .map(i -> "Source 3: " + i)
                .take(2);

        noThrottle(source1, source2, source3);
        throttleLast(source1, source2, source3);
        throttleFirst(source1, source2, source3);
        throttleWithTimeout(source1, source2, source3);
    }

    private static void noThrottle(Observable source1, Observable source2, Observable source3) {
        System.out.println("## No throttle ##");
        Observable.concat(source1, source2, source3)
                .subscribe(System.out::println);
        Util.sleep(6000);
    }

    private static void throttleLast(Observable source1, Observable source2, Observable source3) {
        System.out.println("## throttle last / sample ##");
        Observable.concat(source1, source2, source3)
                .throttleLast(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        Util.sleep(6000);
    }

    private static void throttleFirst(Observable source1, Observable source2, Observable source3) {
        System.out.println("## throttle first ##");
        Observable.concat(source1, source2, source3)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        Util.sleep(6000);
    }

    private static void throttleWithTimeout(Observable source1, Observable source2, Observable source3) {
        System.out.println("## throttle with timeout / debounce ##");
        Observable.concat(source1, source2, source3)
                .throttleWithTimeout(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        Util.sleep(6000);
    }
}
