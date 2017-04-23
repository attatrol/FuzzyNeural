package com.github.attatrol.fuzzyneural.anfis.analyzer;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

/**
 * Produces ANFIS result
 * @author atta_troll
 *
 */
public interface AnfisAnalyticalProcessor {

    /**
     * Processes ANFIS analysis.
     * @param inputVector
     * @param fuzzifierNeurons
     * @param tNorm
     * @param antecedentNeuronWeights
     * @param antecedentParents
     * @param antecedentChildren
     * @param consequentCoefficients
     * @param consequentBiases
     * @param consequentValues
     * @return
     */
    double process(double[] inputVector, FuzzySet[][] fuzzifierNeurons, TNorm tNorm,
            double[] antecedentNeuronWeights, int[][] antecedentParents, int[][] antecedentChildren,
            double[][] consequentCoefficients, double[] consequentBiases, double[] consequentValues);

}
