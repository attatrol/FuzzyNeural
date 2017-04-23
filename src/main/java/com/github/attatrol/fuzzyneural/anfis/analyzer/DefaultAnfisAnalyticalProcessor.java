package com.github.attatrol.fuzzyneural.anfis.analyzer;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

/**
 * Classical ANFIS propagation machine.
 * @author atta_troll
 *
 */
public class DefaultAnfisAnalyticalProcessor implements AnfisAnalyticalProcessor {

    public double process(double[] inputVector, FuzzySet[][] fuzzifierNeurons, TNorm tNorm,
            double[] antecedentNeuronWeights, int[][] antecedentParents, int[][] antecedentChildren,
            double[][] consequentCoefficients, double[] consequentBiases, double[] consequentValues) {
        // 1. fuzzify and write results into first of antecedent weights.
        int k = 0;
        double antecedentWeightSum = 0.;
        for (int i = 0; i < inputVector.length; i++) {
            for (int j = 0; j < fuzzifierNeurons[i].length; j++) {
            antecedentNeuronWeights[k] = fuzzifierNeurons[i][j].getGradeFunction(inputVector[i]);
            antecedentWeightSum += antecedentNeuronWeights[k];
            k++;
            }
        }
        // 2. form non-trivial antecedents
        for (int i = k; i < antecedentNeuronWeights.length; i++) {
            double value = antecedentNeuronWeights[antecedentParents[i][0]];
            for (int j = 1; j < antecedentParents[i].length; j++) {
                value = tNorm.calculateTNorm(antecedentParents[i][j], value);
            }
            antecedentNeuronWeights[i] = value;
        }
        // 3. calculate consequents
        for (int i = 0; i < antecedentNeuronWeights.length; i++) {
            double linearCombination = consequentBiases[i];
            for (int j = 0; j < consequentCoefficients[i].length; j++) {
                linearCombination += consequentCoefficients[i][j] * inputVector[j];
            }
            consequentValues[i] = linearCombination;
        }
        // 4. form result as a weighted sum
        if (antecedentWeightSum == 0.) {
            System.out.println("Bad, weight sum is zero!");
            double result = 0.;
            for (int i = 0; i < consequentValues.length; i++) {
                result += consequentValues[i];
            }
            return result;
        }
        else {
            double result = 0.;
            for (int i = 0; i < consequentValues.length; i++) {
                result += consequentValues[i] * antecedentNeuronWeights[i];
            }
            return result / antecedentWeightSum;
        }
    }
}
