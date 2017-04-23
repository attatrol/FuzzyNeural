package com.github.attatrol.fuzzyneural.fuzzyset.implication;

public class MamdaniImplication implements FuzzyImplicationGrade {

    @Override
    public double calculate(double antecedentGradeValue, double consequentGradeValue) {
        return antecedentGradeValue < consequentGradeValue ? antecedentGradeValue : consequentGradeValue;
    }

}
