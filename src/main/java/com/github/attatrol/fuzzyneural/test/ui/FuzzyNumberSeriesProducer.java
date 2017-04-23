package com.github.attatrol.fuzzyneural.test.ui;

import com.github.attatrol.fuzzyneural.fuzzynumber.FuzzyNumber;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 * Simple series derived from a fuzzy number
 * @author atta_troll
 *
 */
public final class FuzzyNumberSeriesProducer {

    /**
     * X axis granularity quantum.
     */
    private static final double X_STEP_VALUE = .01;

    /**
     * Not in use.
     */
    private FuzzyNumberSeriesProducer() {}

    /**
     * 
     * @param number produces equivalent series for a given fuzzy number.
     * @return ready-to-plot series
     */
    public static XYChart.Series<Number, Number> produceSeries(FuzzyNumber number) {
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        for (double i = number.leftSupportBorder(); i < number.rightSupportBorder(); i += X_STEP_VALUE) {
            series.getData().add(new Data<Number, Number>(i, number.getGradeFunction(i)));
        }
        return series;
    }

}
