package com.github.attatrol.fuzzyneural.anfis.factory;

import com.github.attatrol.fuzzyneural.anfis.Anfis;
import com.github.attatrol.fuzzyneural.fuzzyset.AnfisHistogramFuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;

/**
 * Produces a histogram fuzzy set with a a defined support set numeric bounds
 * @author atta_troll
 *
 */
public final class TriangularHistogramFuzzySetFactory implements FuzzifierLayerFactory {

    public static final double COVERAGE_ABURDANCE_COEFFICIENT = 1.4;

    private double supportMin;

    private double supportMax;

    private double supportStep;

    private int histogramSize;

    public TriangularHistogramFuzzySetFactory(double supportMin, double supportMax, double supportStep) {
        if (supportMax <= supportMin) {
            throw new IllegalArgumentException("Bad support set bounds");
        }
        this.supportMin = supportMin;
        this.supportMax = supportMax;
        this.supportStep = supportStep;
        histogramSize = (int) ((supportMax - supportMin) / supportStep);
    }

    /**
     * Produces a triangular fuzzy set.
     * @param xMin
     * @param maximum
     * @param xMax
     * @return
     */
    public AnfisHistogramFuzzySet produceSet(double xMin, double maximum, double xMax) {
        if (xMin >= maximum || maximum >= xMax) {
            throw new IllegalArgumentException("Bad triangle points");
        }
        double[] histogram = new double[histogramSize];
        double x = supportMin;
        for (int i = 0; i < histogramSize; i++) {
            if (x > xMin && x <= maximum) {
                histogram[i] = (x - xMin) / (maximum - xMin);
            }
            else if (x > maximum && x <= xMax) {
                histogram[i] = (x - xMax) / (maximum - xMin);
            }
            x += supportStep;
        }
        return new AnfisHistogramFuzzySet(supportMin, histogram);
    }

    /**
     * Covers support set with uniformely distributed fuzzy sets.
     * @param setFootprint
     * @param setNumber
     * @return
     */
    public ModifiableFuzzySet[] coverSupport(double setFootprint, int setNumber) {
        if (setFootprint <= 0.) {
            throw new IllegalArgumentException("Non-positive footprint size");
        }
        if (setNumber < 1) {
            throw new IllegalArgumentException("Non-positive number of sets");
        }
        if (supportMax - supportMin < setFootprint * setNumber) {
            throw new IllegalArgumentException("Full cover is impossble");
        }
        double[] maxima = new double[setNumber];
        double[] minX = new double[setNumber];
        double[] maxX = new double[setNumber];
        final double baseHalf = setFootprint / 2.;
        final double xStep = (supportMax - supportMin) / setNumber;
        double x = supportMin + xStep / 2;
        for (int i = 0; i < setNumber; i++) {
            maxima[i] = x;
            minX[i] = x - baseHalf;
            maxX[i] = x + baseHalf;
        }
        ModifiableFuzzySet[] coverage = new ModifiableFuzzySet[setNumber];
        for (int i = 0; i < setNumber; i++) {
            coverage[i] = produceSet(minX[i], maxima[i], maxX[i]);
        }
        return coverage;
    }

    /**
     * Covers support set with uniformely distributed fuzzy sets.
     * Calculates footprint of sets using {@link #COVERAGE_ABURDANCE_COEFFICIENT}.
     * @param setNumber
     * @return
     */
    public ModifiableFuzzySet[] coverSupport(int setNumber) {
        return coverSupport((supportMax - supportMin) / setNumber
                * COVERAGE_ABURDANCE_COEFFICIENT, setNumber);
    }

    public static FuzzifierLayerFactory produceFactory() {
        return new TriangularHistogramFuzzySetFactory(0., Anfis.INPUT_AMPLITUDE,
                AnfisHistogramFuzzySet.ANFIS_FUZZY_SET_X_QUANTUM);
    }
}
