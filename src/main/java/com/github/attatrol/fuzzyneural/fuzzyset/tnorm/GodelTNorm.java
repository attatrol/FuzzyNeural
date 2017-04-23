package com.github.attatrol.fuzzyneural.fuzzyset.tnorm;

/**
 * Godel t-norm also known as minimum t-norm is a default t-norm for weak conjecture.
 * Its t-conorm is known as a maximum t-conorm.
 * @author atta_troll
 *
 */
public class GodelTNorm implements TNorm {

    @Override
    public double calculateTNorm(double gradeValue1, double gradeValue2) {
        return gradeValue1 < gradeValue2 ? gradeValue1 : gradeValue2;
    }

    @Override
    public double calculateTConorm(double gradeValue1, double gradeValue2) {
        return gradeValue1 > gradeValue2 ? gradeValue1 : gradeValue2;
    }

}
