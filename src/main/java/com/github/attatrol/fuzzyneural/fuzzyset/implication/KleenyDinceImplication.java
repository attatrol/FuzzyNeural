package com.github.attatrol.fuzzyneural.fuzzyset.implication;

public class KleenyDinceImplication implements FuzzyImplicationGrade {

    @Override
    public double calculate(double antecedentGradeValue, double consequentGradeValue) {
        final double negatedAntecedent = 1. - antecedentGradeValue;
        return negatedAntecedent > consequentGradeValue? negatedAntecedent : consequentGradeValue;
    }

}
