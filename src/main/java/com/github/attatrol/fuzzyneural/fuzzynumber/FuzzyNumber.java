package com.github.attatrol.fuzzyneural.fuzzynumber;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;

/**
 * Interface for any fuzzy number.
 *
 * @author atta_troll
 *
 */
public interface FuzzyNumber extends FuzzySet {

    /**
     * Step size on X axis.
     */
    double X_AXIS_EPS = .001;

    /**
     * Number of quants on Y axis.
     */
    int Y_AXIS_STEP_NUMBER = 128;

    /**
     * Quant size on Y axis.
     */
    double Y_AXIS_EPS =  1. / Y_AXIS_STEP_NUMBER;

    /**
     * Left alpha-cut border for a defined alpha-cut value.
     * @param aLevel alpha-level value
     * @return left border
     */
    double leftACut(double aLevel);

    /**
     * Right alpha-cut border for a defined alpha-cut value.
     * @param aLevel alpha-level value
     * @return right border
     */
    double rightACut(double aLevel);

    /**
     * Left alpha-cut border for a defined alpha-cut index.
     * Indexes are translated into alpha-cut levels by
     * multiplying them on {@link #Y_AXIS_EPS}
     * @param aLevel alpha-level value
     * @return left border
     */
    double leftACut(int aLevelIndex);

    /**
     * Right alpha-cut border for a defined alpha-cut index.
     * Indexes are translated into alpha-cut levels by
     * multiplying them on {@link #Y_AXIS_EPS}
     * @param aLevel alpha-level value
     * @return right border
     */
    double rightACut(int aLevelIndex);

    /**
     * Adds a summand to this number.
     * <b>May mutate this number.</b>
     * @param summand fuzzy number summand
     * @return fuzzy sum
     */
    FuzzyNumber add(FuzzyNumber summand);

    /**
     * Multiplies a factor to this number.
     * <b>May mutate this number.</b>
     * @param summand fuzzy number summand
     * @return fuzzy product
     */
    FuzzyNumber multiply(FuzzyNumber factor);

    /**
     * Multiplies this number on a scalar.
     * <b>Mutates this number.</b>
     * @param scalar scalar value
     * @return fuzzy product
     */
    FuzzyNumber multiply(double scalar);

    /**
     * Adds a scalar to this number (performs shift).
     * <b>Mutates this number.</b>
     * @param scalar scalar value
     * @return fuzzy sum
     */
    FuzzyNumber add(double scalar);

    /**
     * @return true if this number is well-formed
     */
    boolean isValid();

    /**
     * Default sum method for some 2 fuzzy numbers.
     * Should be performed as rare as possible because it creates another fuzzy number in memory.
     * @param summand1 a fuzzy summand
     * @param summand2 a fuzzy summand
     * @return fuzzy sum
     */
    static ACutFuzzyNumber sumAsACut(FuzzyNumber summand1, FuzzyNumber summand2) {
        double leftACutBorders[] = new double[Y_AXIS_STEP_NUMBER];
        double rightACutBorders[] = new double[Y_AXIS_STEP_NUMBER];
        for (int i = 0; i < Y_AXIS_STEP_NUMBER; i++) {
            leftACutBorders[i] = summand1.leftACut(i) + summand2.leftACut(i);
            rightACutBorders[i] = summand1.rightACut(i) + summand2.rightACut(i);
        }
        return new ACutFuzzyNumber(leftACutBorders, rightACutBorders);
    }

    /**
     * Default product method for some 2 fuzzy numbers.
     * Should be performed as rare as possible because it creates another fuzzy number in memory.
     * @param factor1 a fuzzy factor
     * @param factor2 a fuzzy factor
     * @return fuzzy product
     */
    static ACutFuzzyNumber multiplyAsACut(FuzzyNumber factor1, FuzzyNumber factor2) {
        double leftACutBorders[] = new double[Y_AXIS_STEP_NUMBER];
        double rightACutBorders[] = new double[Y_AXIS_STEP_NUMBER];
        for (int i = 0; i < Y_AXIS_STEP_NUMBER; i++) {
            final double left1 = factor1.leftACut(i);
            final double left2 = factor2.leftACut(i);
            final double right1 = factor1.rightACut(i);
            final double right2 = factor2.rightACut(i);
            final double possible1 = left1 * left2;
            final double possible2 = right1 * right2;
            final double possible3 = left1 * right2;
            final double possible4 = right1 * left2;
            double min = possible1;
            double max = possible1;
            min = min < possible2 ? min : possible2;
            min = min < possible3 ? min : possible3;
            min = min < possible4 ? min : possible4;
            max = max > possible2 ? max : possible2;
            max = max > possible3 ? max : possible3;
            max = max > possible4 ? max : possible4;
            leftACutBorders[i] = min;
            rightACutBorders[i] = max;
        }
        return new ACutFuzzyNumber(leftACutBorders, rightACutBorders);
    }

}
