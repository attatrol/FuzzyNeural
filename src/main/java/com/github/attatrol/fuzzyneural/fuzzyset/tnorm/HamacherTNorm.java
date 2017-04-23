package com.github.attatrol.fuzzyneural.fuzzyset.tnorm;

public class HamacherTNorm implements TNorm {

    @Override
    public double calculateTNorm(double gradeValue1, double gradeValue2) {
        final double prod = gradeValue1 * gradeValue2;
        return prod / (gradeValue1 + gradeValue2 - prod);
    }

    @Override
    public double calculateTConorm(double gradeValue1, double gradeValue2) {
        final double prod = gradeValue1 * gradeValue2;
        return prod != 1. ? (gradeValue1 + gradeValue2 - 2 * prod) / (1. - prod) : 1.;
    }

}
