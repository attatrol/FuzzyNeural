package com.github.attatrol.fuzzyneural.fuzzyset.tnorm;

/**
 * Contains some triangular norm and its dual co-norm calculators.
 * <b>Must be state-less for multithread usage!</b>
 * @author atta_troll
 *
 */
public interface TNorm {

    double calculateTNorm(double gradeValue1, double gradeValue2);

    double calculateTConorm(double gradeValue1, double gradeValue2);

}
