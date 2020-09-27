package com.undevined.rxjava.chapter._5;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelWithFlatMap {

    public static void main(String[] args) {
        System.out.println("Runtime: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Normal: " + normal());
        System.out.println("FlatMap: " + flatMap());
    }


    private static Long normal() {
        Long start = System.currentTimeMillis();
        Observable.range(1, 10)
                .map(i -> intenseCalculation(i))
                .subscribe(i -> System.out.println("received " + i + " " + LocalTime.now()));
        return System.currentTimeMillis() - start;
    }

    private static Long flatMap() {
        Long start = System.currentTimeMillis();
        Observable.range(1, 10)
                .flatMap(i -> Observable.just(i))
                .subscribeOn(Schedulers.computation())
                .map(i2 -> intenseCalculation(i2))
                .subscribe(i -> System.out.println("received " + i + " " + LocalTime.now() + " on thread " + Thread.currentThread().getName()));
        sleep(20000);
        return System.currentTimeMillis() - start;
    }

    private static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
