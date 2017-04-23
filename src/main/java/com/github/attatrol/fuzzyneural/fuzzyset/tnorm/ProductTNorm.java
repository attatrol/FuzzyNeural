package com.github.attatrol.fuzzyneural.fuzzyset.tnorm;

/**
 * Product t-norm is the standard semantics for strong conjunction in product fuzzy logic.
 * It is a strict Archimedean t-norm. Also known as a probabalistic t-norm.
 * @author atta_troll
 *
 */
public class ProductTNorm implements TNorm {

    @Override
    public double calculateTNorm(double gradeValue1, double gradeValue2) {
        return gradeValue1 * gradeValue2;
    }

    @Override
    public double calculateTConorm(double gradeValue1, double gradeValue2) {
        return gradeValue1 + gradeValue2 - gradeValue1 * gradeValue2;
    }

}
