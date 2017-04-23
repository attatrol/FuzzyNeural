package com.github.attatrol.fuzzyneural.inference;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public class SugenoRule {

    private FuzzySet[] antecedents;

    private double[][] coefficients;

    private double[] biases;

    

    public SugenoRule(FuzzySet[] antecedents, double[][] coefficients, double[] biases) {
        this.antecedents = antecedents;
        this.coefficients = coefficients;
        this.biases = biases;
    }

    public double execute(double[] arguments) {
        double[] weights = new double[antecedents.length];
        double weightSum = 0.;
        for (int i = 0; i < antecedents.length; i++) {
            weights[i] = antecedents[i].getGradeFunction(arguments[i]);
            weightSum += weights[i];
        }
        double linearSum = 0.;
        for (int i = 0; i < antecedents.length; i++) {
            double consequent = biases[i];
            for (int j = 0; j < antecedents.length; j++) {
                consequent += coefficients[i][j] * arguments[j];
            }
            linearSum += weightSum == 0. ? consequent : consequent * weights[i];
        }
        return weightSum == 0. ? linearSum : linearSum / weightSum;
    }

}
