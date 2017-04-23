package com.github.attatrol.fuzzyneural.fuzzyset.operations;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

/**
 * This class used to aggregate grade functions from fuzzy sets that
 * are combined into a relation.
 * @author atta_troll
 *
 */
public class FuzzySetAggregation {

    private FuzzySet[] sets;

    private TNorm tNorm;

    private FuzzyLogicOperation[] operations;

    private int[][] operationArgumentIndexes;

    private double[] temp = new double[FuzzyLogicOperation.MAX_OPERATION_ARITY];

    public double getAggregatedGradeFunction(double[] arguments) {
        double[] gradeValues = new double[sets.length + operations.length];
        for (int i = 0; i < sets.length; i++) {
            gradeValues[i] = sets[i].getGradeFunction(arguments[i]);
        }
        for (int i = 0; i < operations.length; i++) {
            for (int j = 0; j < operationArgumentIndexes[i].length; i++) {
                temp[j] = operationArgumentIndexes[i][j];
            }
            gradeValues[i + sets.length] =
                    operations[i].calculateAggregatedGrade(temp, tNorm);
        }
        return gradeValues[gradeValues.length - 1];
    }

}
