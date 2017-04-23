package com.github.attatrol.fuzzyneural.fuzzyset.operations;

import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public enum FuzzyLogicOperation {

    AND {
        @Override
        public double calculateAggregatedGrade(double[] grades, TNorm tNorm) {
            return tNorm.calculateTNorm(grades[0], grades[1]);
        }
    }, OR {
        @Override
        public double calculateAggregatedGrade(double[] grades, TNorm tNorm) {
            return tNorm.calculateTConorm(grades[0], grades[1]);
        }
    }, NOT {
        @Override
        public double calculateAggregatedGrade(double[] grades, TNorm tNorm) {
            return 1. - grades[0];
        }
    };

    public static final int MAX_OPERATION_ARITY = 2;

    public abstract double calculateAggregatedGrade(double[] grades, TNorm tNorm);

}
