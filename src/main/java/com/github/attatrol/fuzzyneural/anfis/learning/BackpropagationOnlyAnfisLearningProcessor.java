package com.github.attatrol.fuzzyneural.anfis.learning;

import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public class BackpropagationOnlyAnfisLearningProcessor implements AnfisLearningProcessor {

    protected double changeFactor = 0.05;

    public BackpropagationOnlyAnfisLearningProcessor(double changeFactor) {
        this.changeFactor = changeFactor;
    }

    @Override
    public void process(double[] inputVector, ModifiableFuzzySet[][] fuzzifierNeurons, TNorm tNorm,
            double[] antecedentNeuronWeights, int[][] antecedentParents, int[][] antecedentBasicParents, int[][] antecedentChildren,
            double[][] consequentCoefficients, double[] consequentBiases, double[] consequentValues, double result,
            double reference, double[] antedecentChangeValues, double[] fuzzifierNeuronChangeValues) {
        final double basicDifference = reference - result;
        double weightSum = 0.;
        for (int i = 0; i < antecedentNeuronWeights.length; i++) {
            weightSum += antecedentNeuronWeights[i];
        }
        if (weightSum == 0.) {
            System.out.println("Weight sum = 0, learning skipped");
            return;
        }
        final double invertedWeightSum = 1. / weightSum;
        final double coeff = changeFactor * basicDifference * invertedWeightSum;
        // calculate change values
        for (int i = 0; i < antecedentNeuronWeights.length; i++) {
            antedecentChangeValues[i] = coeff * (consequentValues[i] - result);
        }
        // calculate exact change values
        for (int i = 0; i < fuzzifierNeuronChangeValues.length; i++) {
            fuzzifierNeuronChangeValues[i] = 0.;
        }
        for (int i = 0; i < antecedentBasicParents.length; i++) {
            for (int j = 0; j < antecedentBasicParents[i].length; j++) {
                fuzzifierNeuronChangeValues[antecedentBasicParents[i][j]] += antedecentChangeValues[i]
                        / (antecedentBasicParents[i].length * antecedentBasicParents[i].length);
            }
        }
        // applying change values to the fuzzy sets
        int k = 0;
        for (int i = 0; i < inputVector.length; i++) {
            for (int j = 0; j < fuzzifierNeurons[i].length; j++) {
            fuzzifierNeurons[i][j].modify(antecedentNeuronWeights[k], fuzzifierNeuronChangeValues[k]);
            k++;
            }
        }
    }

}
