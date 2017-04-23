package com.github.attatrol.fuzzyneural.fuzzyset.implication;

public class GoedelImplication implements FuzzyImplicationGrade {

    @Override
    public double calculate(double antecedentGradeValue, double consequentGradeValue) {
        return antecedentGradeValue <= consequentGradeValue ? 1. : consequentGradeValue;
    }

}
