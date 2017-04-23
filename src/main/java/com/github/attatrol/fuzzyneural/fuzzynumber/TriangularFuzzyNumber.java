package com.github.attatrol.fuzzyneural.fuzzynumber;

public class TriangularFuzzyNumber implements FuzzyNumber {

    private double a;

    private double b;

    private double c;

    public TriangularFuzzyNumber(double a, double b, double c) {
        this.a  = a;
        this.b = b;
        this.c = c;
    }

    public double leftACut(double aLevel) {
        return (b - a) * aLevel + a;
    }

    public double rightACut(double aLevel) {
        return (b - c) * aLevel + c;
    }

    @Override
    public double leftACut(int aLevelIndex) {
        return leftACut(ACutFuzzyNumber.A_CUT_LEVELS[aLevelIndex]);
    }

    @Override
    public double rightACut(int aLevelIndex) {
        return rightACut(ACutFuzzyNumber.A_CUT_LEVELS[aLevelIndex]);
    }

    @Override
    public double leftSupportBorder() {
        return a;
    }

    @Override
    public double rightSupportBorder() {
        return c;
    }

    public FuzzyNumber add(FuzzyNumber summand) {
        if (summand instanceof TriangularFuzzyNumber) {
            TriangularFuzzyNumber triangularSummand = (TriangularFuzzyNumber) summand;
            a += triangularSummand.a;
            b += triangularSummand.b;
            c += triangularSummand.c;
            return this;
        }
        else {
            return FuzzyNumber.sumAsACut(this, summand);
        }
    }

    public FuzzyNumber multiply(FuzzyNumber factor) {
        return FuzzyNumber.multiplyAsACut(this, factor);
    }

    public FuzzyNumber multiply(double scalar) {
        b *= scalar;
        if (scalar >= 0) {
            a *= scalar;
            c *= scalar;
        }
        else {
            final double temp = a * scalar;
            a = c * scalar;
            c = temp;
        }
        return this;
    }

    public FuzzyNumber add(double scalar) {
        a += scalar;
        b += scalar;
        c += scalar;
        return this;
    }

    @Override
    public boolean isValid() {
        return a <= b && b <= c;
    }

    @Override
    public double getGradeFunction(double xValue) {
        if (xValue < a || xValue > c) {
            return 0.;
        }
        else if (xValue <= b) {
            return b != a ? (xValue - a) / (b - a) : 1.;
        }
        else {
            return b != c ? (xValue - c) / (b - c) : 1.;
        }
    }

	@Override
	public FuzzyNumber getCopy() {
		return new TriangularFuzzyNumber(a, b, c);
	}

}
