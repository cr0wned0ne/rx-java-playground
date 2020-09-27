package com.undevined.rxjava.chapter._6;

import io.reactivex.rxjava3.core.Observable;

/**
 * Windowing buffers emissions into Observable<Observable>
 */
public class Windowing {

    public static void main(String[] args) {
        windowSize();
        // same with time and boundary based!
    }

    private static void windowSize() {
        Observable.range(1, 50)
                .window(8)
                .flatMapSingle(
                        obs -> obs.reduce("", (total, next) -> total + (total.equals("") ? "" : "|") + next)
                )
                .subscribe(System.out::println);
    }
}
