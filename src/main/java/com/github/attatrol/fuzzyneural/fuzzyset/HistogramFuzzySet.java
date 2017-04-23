package com.github.attatrol.fuzzyneural.fuzzyset;

/**
 * Fuzzy set may be described as a histogram with enough small granularity.
 *
 * @author atta_troll
 *
 */
public class HistogramFuzzySet implements FuzzySet {

    public final double xStep;

    protected double xMin;

    protected double xMax;

    protected double[] histogram;

    /**
     * Default ctor
     * @param xMin set support left bound
     * @param histogram histogram
     */
    public HistogramFuzzySet(double xMin, double[] histogram, double xStep) {
        this.xMin = xMin;
        this.histogram = histogram;
        this.xStep = xStep;
        xMax = xMin + xStep * histogram.length;
    }

    @Override
    public double getGradeFunction(double xValue) {
        if (xValue < xMin || xValue >= xMax) {
            return 0.;
        }
        else {
            final int index = (int) ((xValue - xMin) / xStep);
            return histogram[index];
        }
    }

    @Override
    public FuzzySet getCopy() {
        double[] histogramCopy = new double[histogram.length];
        System.arraycopy(histogram, 0, histogramCopy, 0, histogram.length);
        return null;
    }

    @Override
    public double leftSupportBorder() {
        return xMin;
    }

    @Override
    public double rightSupportBorder() {
        return xMax;
    }

    
}
