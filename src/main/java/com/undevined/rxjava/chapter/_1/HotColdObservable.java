package com.undevined.rxjava.chapter._1;



import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;


/**
 * Hot and Cold Observables is a major characteristic of the relationship
 * between Observable and Observer, when there are multiple Observers.
 * This is called multicasting.
 */
public class HotColdObservable {

    public static void main(String[] args) {

        try {
            coldObservable();
            hotObservable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectableObservable();
    }

    /**
     * Cold Observables are used for finite datasets such as reading data from json, file or database.
     * They will sequentially serve each observer. In case of a database, this can mean the data in the table can change
     * between two subscriptions! They are like a static medium like a CD that can be played over and over
     * to Observers.
     */
    private  static void coldObservable() throws InterruptedException {
        System.out.println("### Cold Observers ###");
        Observable<String> samples = Observable.just("1", "2", "3");

        samples.subscribe(s -> System.out.println("First Observer: " + s));
        samples.subscribe(s -> System.out.println("Second Observer: " + s));

        Thread.sleep(5000);

        samples.subscribe(t -> System.out.println("Observer 3: " + t));
    }


    /**
     * Hot Observables are like a radio station and can be understood as infinite stream of events.
     * Each event is pushed to each Observer. Observers that subscribe after an event is emitted,
     * will not get the event.
     */
    private static void hotObservable() throws InterruptedException {
        System.out.println("### Hot Observers ###");
        Observable<Long> tick = Observable.interval(1, TimeUnit.SECONDS);

        tick.subscribe(t -> System.out.println("Observer 1: " + t));
        tick.subscribe(t -> System.out.println("Observer 2: " + t));
        Thread.sleep(5000);

        tick.subscribe(t -> System.out.println("Observer 3: " + t));
    }

    /**
     * ConnectableObservable turn cold into hot Observables.
     * Therefore Observables offer a publish() method that create a ConnectableObservable.
     * After setting up the Observers, the connect() method fires the events to all.
     */
    private static void connectableObservable() {
        System.out.println("### ConnectableObservers ###");
        ConnectableObservable<String> source = Observable.just("One", "Two", "Three").publish();

        source.subscribe(s -> System.out.println("Observer 1: " + s));
        source.subscribe(s -> System.out.println("Observer 2: " + s));

        source.connect();
    }
}
