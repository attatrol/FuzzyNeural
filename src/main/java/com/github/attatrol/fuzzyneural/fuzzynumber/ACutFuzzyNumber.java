package com.github.attatrol.fuzzyneural.fuzzynumber;

public class ACutFuzzyNumber implements FuzzyNumber {

    /**
     * Standard alpha-cut levels used in alpha-cut fuzzy numbers.
     */
    public static final double[] A_CUT_LEVELS = new double[FuzzyNumber.Y_AXIS_STEP_NUMBER];
    static {
        A_CUT_LEVELS[0] = FuzzyNumber.Y_AXIS_EPS;
        for (int i = 1; i < Y_AXIS_STEP_NUMBER; i++) {
            A_CUT_LEVELS[i] = A_CUT_LEVELS[i - 1] + FuzzyNumber.Y_AXIS_EPS;
        }
    }

    private double[] leftACutBorders;

    private double[] rightACutBorders;

    public ACutFuzzyNumber(double[] leftACutBorders, double[] rightACutBorders) {
        this.leftACutBorders = leftACutBorders;
        this.rightACutBorders = rightACutBorders;
    }

    @Override
    public double leftACut(double aLevel) {
        int aLevelIndex = (int) (aLevel / FuzzyNumber.Y_AXIS_EPS);
        return leftACutBorders[aLevelIndex];
    }

    @Override
    public double rightACut(double aLevel) {
        int aLevelIndex = (int) (aLevel / FuzzyNumber.Y_AXIS_EPS);
        return rightACutBorders[aLevelIndex];
    }

    @Override
    public double leftACut(int aLevelIndex) {
        return leftACutBorders[aLevelIndex];
    }

    @Override
    public double rightACut(int aLevelIndex) {
        return rightACutBorders[aLevelIndex];
    }

    @Override
    public double leftSupportBorder() {
        return leftACutBorders[0];
    }

    @Override
    public double rightSupportBorder() {
        return rightACutBorders[0];
    }

    @Override
    public FuzzyNumber add(FuzzyNumber summand) {
        if (summand instanceof ACutFuzzyNumber) {
            ACutFuzzyNumber aCutSummand = (ACutFuzzyNumber) summand;
            for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
                leftACutBorders[i] += aCutSummand.leftACutBorders[i];
                rightACutBorders[i] += aCutSummand.rightACutBorders[i];
            }
        } else {
            for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
                leftACutBorders[i] += summand.leftACut(i);
                rightACutBorders[i] += summand.rightACut(i);
            }
        }
        return this;
    }

    @Override
    public FuzzyNumber multiply(FuzzyNumber factor) {
        if (factor instanceof ACutFuzzyNumber) {
            ACutFuzzyNumber aCutFactor = (ACutFuzzyNumber) factor;
            for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
                final double possible1 = leftACutBorders[i] * aCutFactor.leftACutBorders[i];
                final double possible2 = rightACutBorders[i] * aCutFactor.rightACutBorders[i];
                final double possible3 = leftACutBorders[i] * aCutFactor.rightACutBorders[i];
                final double possible4 = rightACutBorders[i] * aCutFactor.leftACutBorders[i];
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
        } else {
            for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
                final double possible1 = leftACutBorders[i] * factor.leftACut(i);
                final double possible2 = rightACutBorders[i] * factor.rightACut(i);
                final double possible3 = leftACutBorders[i] * factor.rightACut(i);
                final double possible4 = rightACutBorders[i] * factor.leftACut(i);
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
        }
        return this;
    }

    @Override
    public FuzzyNumber multiply(double scalar) {
        if (scalar >= 0) {
            for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
                leftACutBorders[i] *= scalar;
                rightACutBorders[i] *= scalar;
            }
        } else {
            for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
                final double temp = leftACutBorders[i] * scalar;
                leftACutBorders[i] = rightACutBorders[i] * scalar;
                rightACutBorders[i] = temp;
            }
        }
        return this;
    }

    @Override
    public FuzzyNumber add(double scalar) {
        for (int i = 0; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
            leftACutBorders[i] += scalar;
            rightACutBorders[i] += scalar;
        }
        return this;
    }

    @Override
    public boolean isValid() {
        if (leftACutBorders.length != FuzzyNumber.Y_AXIS_STEP_NUMBER
                && rightACutBorders.length != FuzzyNumber.Y_AXIS_STEP_NUMBER) {
            return false;
        }
        if (leftACutBorders[0] > rightACutBorders[0]) {
            return false;
        }
        for (int i = 1; i < FuzzyNumber.Y_AXIS_STEP_NUMBER; i++) {
            if (leftACutBorders[i] > rightACutBorders[i] || leftACutBorders[i] < leftACutBorders[i - 1]
                    || rightACutBorders[i] > rightACutBorders[i - 1]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double getGradeFunction(double xValue) {
        if (xValue < leftACutBorders[0] || xValue > rightACutBorders[0]) {
            return 0.;
        } else if (xValue < leftACutBorders[FuzzyNumber.Y_AXIS_STEP_NUMBER - 1]) {
            int minLimit = 0;
            int maxLimit = FuzzyNumber.Y_AXIS_STEP_NUMBER - 2;
            while (maxLimit - minLimit > 1) {
                int median = (maxLimit - minLimit) / 2 + minLimit;
                if (leftACutBorders[median] == xValue) {
                    return A_CUT_LEVELS[median];
                } else if (leftACutBorders[median] > xValue) {
                    maxLimit = median;
                } else {
                    minLimit = median;
                }
            }
            return A_CUT_LEVELS[minLimit];
        } else if (xValue > rightACutBorders[FuzzyNumber.Y_AXIS_STEP_NUMBER - 1]) {
            int minLimit = 0;
            int maxLimit = FuzzyNumber.Y_AXIS_STEP_NUMBER - 2;
            while (maxLimit - minLimit > 1) {
                int median = (maxLimit - minLimit) / 2 + minLimit;
                if (rightACutBorders[median] == xValue) {
                    return A_CUT_LEVELS[median];
                } else if (rightACutBorders[median] < xValue) {
                    maxLimit = median;
                } else {
                    minLimit = median;
                }
            }
            return A_CUT_LEVELS[minLimit];
        } else {
            return 1.;
        }
    }

    @Override
    public FuzzyNumber getCopy() {
        double[] leftACutBordersCopy = new double[FuzzyNumber.Y_AXIS_STEP_NUMBER];
        double[] rightACutBordersCopy = new double[FuzzyNumber.Y_AXIS_STEP_NUMBER];
        System.arraycopy(leftACutBorders, 0, leftACutBordersCopy, 0, leftACutBordersCopy.length);
        System.arraycopy(rightACutBorders, 0, rightACutBordersCopy, 0, rightACutBordersCopy.length);
        return new ACutFuzzyNumber(leftACutBordersCopy, rightACutBordersCopy);
    }

}
