package com.undevined.rxjava.chapter._6;

import com.undevined.rxjava.chapter.Util;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Buffering buffers emissions into Observable<Collection>.
 */
public class Buffering {

    public static void main(String[] args) {
        bufferValues();
        bufferTimes();
        bufferBoundary();

    }

    private static void bufferValues() {
        System.out.println("## Buffer values ##");
        Observable.range(1, 150)
                .buffer(8)
                //.buffer(8, 2) skip 2
                .subscribe(System.out::println);
    }


    private static void bufferTimes() {
        System.out.println("## Buffer times ##");
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300)
                .buffer(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        Util.sleep(4000);
    }

    private static void bufferBoundary() {
        System.out.println("## Buffer boundary by other Observable ##");
        Observable<Long> cutOffs = Observable.interval(1, TimeUnit.SECONDS);

        Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300)
                .buffer(cutOffs)
                .subscribe(System.out::println);
        Util.sleep(5000);
    }
}
