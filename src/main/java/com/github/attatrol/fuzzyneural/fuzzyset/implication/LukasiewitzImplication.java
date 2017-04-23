package com.github.attatrol.fuzzyneural.fuzzyset.implication;

public class LukasiewitzImplication implements FuzzyImplicationGrade {

    @Override
    public double calculate(double antecedentGradeValue, double consequentGradeValue) {
        final double a = 1 - antecedentGradeValue + consequentGradeValue;
        return 1. < a ? 1. : a;
    }

}
