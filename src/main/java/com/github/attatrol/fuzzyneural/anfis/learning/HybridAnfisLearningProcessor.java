package com.github.attatrol.fuzzyneural.anfis.learning;

import java.util.ArrayList;
import java.util.List;

import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public class HybridAnfisLearningProcessor extends BackpropagationOnlyAnfisLearningProcessor {

    protected double changeFactorMax;

    protected double changeFactorStep;

    protected int epochSize;

    protected int epochCounter;

    private List<Double> refValues = new ArrayList<>();

    private List<Double> resultValues = new ArrayList<>();

    private HybridAnfisLearningProcessor(double changeFactorMax, int epochsToReachMax, int epochSize) {
        super(0.);
        this.changeFactorMax = changeFactorMax;
        this.changeFactorStep = changeFactorMax / epochsToReachMax;
        this.epochSize = epochSize;
    }

    public static HybridAnfisLearningProcessor getHybridAnfisLearningProcessor(
            double changeFactorMax, int epochsToReachMax, int epochSize) {
        if (changeFactorMax < 0.) {
            throw new IllegalStateException("Negative change factor");
        }
        if (changeFactorMax >= 1.) {
            throw new IllegalStateException("Change factor over 1");
        }
        if (epochsToReachMax < 1) {
            throw new IllegalStateException("Epoch number is lesser than 1");
        }
        if (epochSize < 1) {
            throw new IllegalStateException("Epoch size is not a natural number");
        }
        return new HybridAnfisLearningProcessor(changeFactorMax, epochsToReachMax, epochSize);
    }

    @Override
    public void process(double[] inputVector, ModifiableFuzzySet[][] fuzzifierNeurons, TNorm tNorm,
            double[] antecedentNeuronWeights, int[][] antecedentParents, int[][] antecedentBasicParents, int[][] antecedentChildren,
            double[][] consequentCoefficients, double[] consequentBiases, double[] consequentValues, double result,
            double reference, double[] antedecentChangeValues, double[] fuzzifierNeuronChangeValues) {
        super.process(inputVector, fuzzifierNeurons, tNorm, antecedentNeuronWeights,
                antecedentParents, antecedentBasicParents, antecedentChildren, consequentCoefficients,
                consequentBiases, consequentValues, result, reference, antedecentChangeValues, fuzzifierNeuronChangeValues);
        refValues.add(reference);
        resultValues.add(result);
        epochCounter++;
        if (epochCounter == epochSize) {
            executeLse(refValues, resultValues, consequentCoefficients, consequentBiases);
            epochCounter = 0;
            if (changeFactor < changeFactorMax) {
                changeFactor += changeFactorStep;
            }
            resultValues.clear();
            refValues.clear();
        }
    }

    private void executeLse(List<Double> refValues2, List<Double> resultValues2, double[][] consequentCoefficients,
            double[] consequentBiases) {
        // TODO
    }
}
