package com.github.attatrol.fuzzyneural.anfis.learning;

import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public interface AnfisLearningProcessor {

    void process(double[] inputVector, ModifiableFuzzySet[][] fuzzifierNeurons, TNorm tNorm,
            double[] antecedentNeuronWeights, int[][] antecedentParents, int[][] antecedentBasicParents,
            int[][] antecedentChildren, double[][] consequentCoefficients,
            double[] consequentBiases, double[] consequentValues, double result,
            double reference, double[] antedecentChangeValues, double[] fuzzifierNeuronChangeValues);

    

}
