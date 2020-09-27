package com.undevined.rxjava.chapter._1;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObservablesIntro {

    /**
     * @param args
     */
    public static void main(String[] args) {
        createObservablesWithEmitter();
        createObservableWithJustAndCustomObserver();
    }


    /**
     * Observer.create creates an Observable from an emitter (emitts emissions). An Emitter has onNext(),
     * onError() and onComplete() method to push emissions. <br>
     * Emitters can be used to wrap non reactive sources, but are not used very often.<br>
     * The Subsrcriber / Observer also implements onNext(), onError() and onComplete() methods,
     * specified each as a lambda expression below.
     * <ul>
     *     <li>onNext(): s -> System.out.println(s)</li>
     *     <li>onError(): Throwable::printStackTrace</li>
     *     <li>onComplete(): () -> System.out.println("Done")</li>
     * </ul>
     * The onError() and onComplete() are optional, onError() should be there in productive code.
     */
    private static void createObservablesWithEmitter() {
        Observable<String> source = Observable.create(emitter -> {
            try {
                emitter.onNext("First");
                emitter.onNext("Second");
                emitter.onNext("Third");
                emitter.onComplete();
            } catch (Throwable e) {
                System.out.println("Emitter on error!");
                emitter.onError(e);
            }
        });
        source.subscribe(s -> System.out.println(s), Throwable::printStackTrace, () -> System.out.println("Done"));
    }


    /**
     * Observables can also be created with Observable.just() or Observable.from(Iterable).
     * The Observable can be further manipulated, for example by map() or filter() function.
     * They also implement an Observer interface internally and subscribe to the Observable.
     * They return an Observable of the type they return. For Example the map(String::length)
     * maps the Observable<String> to and Observable<Integer>.
     * The Subscriber / final Observer can also be implemented as custom Implementation of Observer
     * Interface with onNext(), onComplete() and onError() method.
     */
    private static void createObservableWithJustAndCustomObserver() {

        Observable<String> just = Observable.just("Alpha", "Beta", "Gamma");

        Observer<Integer> myObserver = new Observer<Integer>() {
            @Override
            public void onComplete() {
                System.out.println("Custom observer is done!");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onSubscribe(Disposable d) {
                // Ignore for now
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Custom Observer: " + integer);
            }
        };
        just.map(String::length).filter(s -> s > 4).subscribe(myObserver);
    }
}
