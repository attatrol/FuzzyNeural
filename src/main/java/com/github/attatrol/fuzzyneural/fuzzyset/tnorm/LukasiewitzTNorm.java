package com.github.attatrol.fuzzyneural.fuzzyset.tnorm;

public class LukasiewitzTNorm implements TNorm {

    @Override
    public double calculateTNorm(double gradeValue1, double gradeValue2) {
        final double a = gradeValue1 + gradeValue2 - 1.;
        return a > 0. ? a : 0.;
    }

    @Override
    public double calculateTConorm(double gradeValue1, double gradeValue2) {
        final double sum = gradeValue1 + gradeValue2;
        return sum < 1. ? sum : 1.;
    }

}
