package com.github.attatrol.fuzzyneural.test.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.attatrol.fuzzyneural.fuzzynumber.FuzzyNumber;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

class FuzzyNumbersChart extends LineChart<Number, Number> {

    FuzzyNumbersChart(Collection<FuzzyNumber> fuzzyNumbers) {
        super(new NumberAxis(getMinBound(fuzzyNumbers),  getMaxBound(fuzzyNumbers), .1),
                new NumberAxis(-.1, 1.1, .1));
        setCreateSymbols(false);
        this.setPrefWidth(1000);
        List<XYChart.Series<Number, Number>> series = new ArrayList<>();
        for (FuzzyNumber number : fuzzyNumbers) {
            series.add(FuzzyNumberSeriesProducer.produceSeries(number));
        }
        getData().addAll(series);
    }

    private static double getMaxBound(Collection<FuzzyNumber> fuzzyNumbers) {
        double maxBound = 0.1;
        for (FuzzyNumber number : fuzzyNumbers) {
            if (maxBound < number.rightSupportBorder()) {
                maxBound = number.rightSupportBorder();
            }
        }
        return maxBound;
    }

    private static double getMinBound(Collection<FuzzyNumber> fuzzyNumbers) {
        double minBound = -0.1;
        for (FuzzyNumber number : fuzzyNumbers) {
            if (minBound > number.leftSupportBorder()) {
                minBound = number.leftSupportBorder();
            }
        }
        return minBound;
    }

}
