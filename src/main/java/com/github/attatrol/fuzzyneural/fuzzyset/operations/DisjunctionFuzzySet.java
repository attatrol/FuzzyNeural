package com.github.attatrol.fuzzyneural.fuzzyset.operations;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public class DisjunctionFuzzySet implements FuzzySet {

    private TNorm tNorm;

    private FuzzySet operand1;

    private FuzzySet operand2;

    public DisjunctionFuzzySet(TNorm tNorm, FuzzySet operand1, FuzzySet operand2) {
        this.tNorm = tNorm;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public TNorm gettNorm() {
        return tNorm;
    }

    public FuzzySet getOperand1() {
        return operand1;
    }

    public FuzzySet getOperand2() {
        return operand2;
    }

    @Override
    public double getGradeFunction(double xValue) {
        return tNorm.calculateTConorm(operand1.getGradeFunction(xValue),
                operand2.getGradeFunction(xValue));
    }

    @Override
    public FuzzySet getCopy() {
        final FuzzySet operand1Copy = operand1.getCopy();
        final FuzzySet operand2Copy = operand2.getCopy();
        return new DisjunctionFuzzySet(tNorm, operand1Copy,
                operand2Copy);
    }

    @Override
    public double leftSupportBorder() {
        final double operand1LeftSupportBorder = operand1.leftSupportBorder();
        final double operand2LeftSupportBorder = operand2.leftSupportBorder();
        return operand1LeftSupportBorder < operand2LeftSupportBorder
                ? operand1LeftSupportBorder : operand2LeftSupportBorder;
    }

    @Override
    public double rightSupportBorder() {
        final double operand1RightSupportBorder = operand1.rightSupportBorder();
        final double operand2RightSupportBorder = operand2.rightSupportBorder();
        return operand1RightSupportBorder > operand2RightSupportBorder
                ? operand1RightSupportBorder : operand2RightSupportBorder;
    }

}
