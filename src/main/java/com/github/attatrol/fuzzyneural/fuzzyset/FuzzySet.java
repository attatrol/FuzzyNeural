package com.github.attatrol.fuzzyneural.fuzzyset;

public interface FuzzySet {

    /**
     * Returns grade function value for a current x axis value.
     * @param xValue x axis value, argument
     * @return grade function value
     */
    double getGradeFunction(double xValue);

    /**
     * Creates deep copy of the fuzzy set.
     * @return
     */
    FuzzySet getCopy();

    /**
     * @return left bound of the support set
     */
    double leftSupportBorder();

    /**
     * @return right bound of the support set
     */
    double rightSupportBorder();

}
