package com.undevined.rxjava.chapter._4;

import com.undevined.rxjava.chapter.Util;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Multicasting allows to cache and replay values. Attention: this consumes Memory.
 */
public class Replaying {

    public static void main(String[] args) {

        // Observable is set up with replay, hence it will replay all values to each new Observer.
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS)
                // replay also accepts an argument (how many values to cache)
                .replay()
                .autoConnect();

        //Observer 1 will receive values from 0-2 and beyond
        seconds.subscribe(s -> System.out.println("Observer 1: " + s));

        Util.sleep(3000);

        //Observer 2 After will receive all replayed values from 0-2 and jump in at 3
        seconds.subscribe(s -> System.out.println("Observer 2: " + s));

        Util.sleep(3000);
    }



}
