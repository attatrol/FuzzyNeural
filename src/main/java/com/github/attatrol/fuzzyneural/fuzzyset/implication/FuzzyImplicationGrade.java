package com.github.attatrol.fuzzyneural.fuzzyset.implication;

/**
 * There are may ways to calculate implication grade function
 * @author atta_troll
 *
 */
public interface FuzzyImplicationGrade {

    double calculate(double antecedentGradeValue, double consequentGradeValue);

}
