package com.github.attatrol.fuzzyneural.fuzzyset.implication;

public class ZadeImplication implements FuzzyImplicationGrade {

    @Override
    public double calculate(double antecedentGradeValue, double consequentGradeValue) {
        final double negatedAntecedent = 1. - antecedentGradeValue;
        final double min = antecedentGradeValue < consequentGradeValue ? antecedentGradeValue
                : consequentGradeValue;
        return negatedAntecedent > min ? negatedAntecedent : min;
    }

}
