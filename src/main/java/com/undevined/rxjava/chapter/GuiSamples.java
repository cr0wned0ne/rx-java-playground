package com.undevined.rxjava.chapter;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;


public class GuiSamples extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setMinSize(200, 100);

        Label headlineKeystrokes = new Label("Grouping Keystrokes:");
        Label typedTextLabel = new Label("");

        Label headlineTimer = new Label("Timer:");
        Label counterLabel = new Label("");
        ToggleButton button = new ToggleButton();

        Observable<Boolean> state = JavaFxObservable.valuesOf(button.selectedProperty())
                .publish()
                .autoConnect(2);

        state.switchMap(selected -> {
            if (selected) {
                return Observable.interval(1, TimeUnit.MILLISECONDS);
            } else {
                return Observable.empty();
            }
        }).observeOn(JavaFxScheduler.platform())
                .map(Object::toString)
                .subscribe(counterLabel::setText);
        state.subscribe(selected -> button.setText( selected ? "Stop" : "Start"));



        root.getChildren().addAll(headlineKeystrokes, typedTextLabel, headlineTimer, counterLabel, button);

        Scene scene = new Scene(root);
        Observable<String> typed = JavaFxObservable.eventsOf(scene, KeyEvent.KEY_TYPED)
                .map(KeyEvent::getCharacter)
                .share();
        Observable<String> restSignal = typed
                .throttleWithTimeout(500, TimeUnit.MILLISECONDS)
                .startWith("");
        restSignal.switchMap(s -> typed.scan("", (rolling, next) -> rolling + next))
                .observeOn(JavaFxScheduler.platform())
                .subscribe(s -> {
                    typedTextLabel.setText(s);
                    System.out.println(s);
                });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
