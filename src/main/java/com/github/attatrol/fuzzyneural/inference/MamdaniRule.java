package com.github.attatrol.fuzzyneural.inference;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.defuzzification.DefuzzificationMethod;
import com.github.attatrol.fuzzyneural.fuzzyset.implication.FuzzyImplicationGrade;
import com.github.attatrol.fuzzyneural.fuzzyset.operations.FuzzySetAggregation;

public abstract class MamdaniRule {

    private FuzzySetAggregation antecedent;

    private FuzzySet consequent;

    private FuzzyImplicationGrade implicationGrade;

    private DefuzzificationMethod defuzzificationMethod;

    public double execute(double[] arguments) {
        final double antecedentGradeValue =
                antecedent.getAggregatedGradeFunction(arguments);
        final double xMin = consequent.leftSupportBorder();
        final double xMax = consequent.rightSupportBorder();
        final int implicationSize = (int) ((xMax - xMin) / DefuzzificationMethod.EPS);
        final double xStep = (xMax - xMin) / implicationSize;
        final double[] implication = new double[implicationSize];
        double x = xMin;
        for (int i = 0; i <  implicationSize; i++) {
            implication[i] = implicationGrade.calculate(antecedentGradeValue,
                    consequent.getGradeFunction(x));
            x += xStep;
        }
        return defuzzificationMethod.defuzzify(implication, xMin, xStep);
    }
}
