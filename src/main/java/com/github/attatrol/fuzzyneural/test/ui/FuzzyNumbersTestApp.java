package com.github.attatrol.fuzzyneural.test.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.attatrol.fuzzyneural.fuzzynumber.FuzzyNumber;
import com.github.attatrol.fuzzyneural.fuzzynumber.TriangularFuzzyNumber;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FuzzyNumbersTestApp extends Application {

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("ru"));
        FuzzyNumbersChart chart = new FuzzyNumbersChart(getFuzzyNumbers());
        Pane chartPane = new Pane();
        chartPane.setPrefWidth(1000);
        chartPane.getChildren().add(chart);
        primaryStage.setScene(new Scene(chartPane));
        primaryStage.setTitle("Fuzzy numbers visual test");
        primaryStage.show();
    }

    /**
     * Entry point
     * @param strings not in use
     */
    public static final void main(String...strings) {
        launch(strings);
    }

    public static List<FuzzyNumber> getFuzzyNumbers() {
        List<FuzzyNumber> list  = new ArrayList<>();
        FuzzyNumber first = new TriangularFuzzyNumber(-1., 0., 1.);
        //list.add(first);
        FuzzyNumber second = new TriangularFuzzyNumber(1., 2., 3.);
        //list.add(second);
        FuzzyNumber third = first.multiply(second);
        list.add(third);
        FuzzyNumber fourth = second.multiply(third);
        //list.add(fourth);
        FuzzyNumber fifth = new TriangularFuzzyNumber(-3., 0., 2.);
        FuzzyNumber sixth = fifth.multiply(fourth);
        list.add(sixth);
        return list;
    }
}