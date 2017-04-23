package com.github.attatrol.fuzzyneural.anfis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.attatrol.fuzzyneural.anfis.analyzer.AnfisAnalyticalProcessor;
import com.github.attatrol.fuzzyneural.anfis.learning.AnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public class Anfis implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1442582181925011690L;

    public static final double INPUT_AMPLITUDE = 1.;

    /*
     * ANFIS internal state
     */
    /**
     * Fuzzy sets used to fuzzify input vector.
     */
    public ModifiableFuzzySet[][] fuzzifierNeurons;

    /**
     * T-norm used to produce complex antecedents.
     */
    private TNorm tNorm;

    /**
     * Antecedents.
     */
    private double[] antecedentNeuronWeights;

    /**
     * Parents of the antecedent neurons
     */
    private int[][] antecedentParents;

    private int[][] antecedentBasicParents;

    private int[][] antecedentChildren;

    private double[][] consequentCoefficients;

    private double[] consequentBiases;

    private double[] consequentValues;

    /**
     * Learner-specific antecedent change values.
     */
    private double[] antedecentChangeValues;

    /**
     * Learner-specific fuzzifier neurons change values
     */
    private double[] fuzzifierNeuronChangeValues;


    /*
     * Analytical and learning processors
     */
    private AnfisAnalyticalProcessor analyzer;

    private AnfisLearningProcessor learner;

    public Anfis(ModifiableFuzzySet[][] fuzzifierNeurons, TNorm tNorm, double[] antecedentNeuronWeights,
            int[][] antecedentParents, int[][] antecedentBasicParents, int[][] antecedentChildren,
            double[][] consequentCoefficients, double[] consequentBiases, double[] consequentValues,
            AnfisAnalyticalProcessor analyzer, AnfisLearningProcessor learner) {
        this.fuzzifierNeurons = fuzzifierNeurons;
        this.tNorm = tNorm;
        this.antecedentNeuronWeights = antecedentNeuronWeights;
        this.antecedentParents = antecedentParents;
        this.antecedentBasicParents = antecedentBasicParents;
        this.antecedentChildren = antecedentChildren;
        this.consequentCoefficients = consequentCoefficients;
        this.consequentBiases = consequentBiases;
        this.consequentValues = consequentValues;
        this.analyzer = analyzer;
        this.learner = learner;
        antedecentChangeValues = new double[antecedentNeuronWeights.length];
        fuzzifierNeuronChangeValues = new double[fuzzifierNeurons.length * fuzzifierNeurons[0].length];
    }

    public synchronized double map(double[] inputVector) throws IllegalArgumentException {
        checkInputVector(inputVector);
        return analyzer.process(inputVector, fuzzifierNeurons, tNorm, antecedentNeuronWeights,
                antecedentParents, antecedentChildren,
                consequentCoefficients, consequentBiases, consequentValues);
    }

    public synchronized double learn(double[] inputVector, double reference) throws IllegalArgumentException {
        checkInputVector(inputVector);
        final double result = analyzer.process(inputVector, fuzzifierNeurons, tNorm, antecedentNeuronWeights,
                antecedentParents, antecedentChildren,
                consequentCoefficients, consequentBiases, consequentValues);
        learner.process(inputVector, fuzzifierNeurons, tNorm, antecedentNeuronWeights,
                antecedentParents, antecedentBasicParents, antecedentChildren,
                consequentCoefficients, consequentBiases, consequentValues, result, reference,
                antedecentChangeValues, fuzzifierNeuronChangeValues);
        return result;
    }

    public void checkValidity() throws IllegalStateException {
        
    }

    /**
     * Checks if input vector is valid.
     * @param inputVector input vector
     * @throws NeuralNetworkRuntimeException on invalid input vector
     */
    private void checkInputVector(double[] inputVector) throws IllegalArgumentException {
        if (inputVector.length != fuzzifierNeurons.length) {
            throw new IllegalArgumentException(
                    String.format("Incoming vector has cardinality of %d, network accepts only %d",
                            inputVector.length, fuzzifierNeurons.length));
        }
        for (int i = 0; i < inputVector.length; i++) {
            if (inputVector[i] < 0. || inputVector[i] > INPUT_AMPLITUDE) {
                throw new IllegalArgumentException(
                        String.format("Input vector coordinate %d has value of %f, which is out of bounds [0, %f]", 
                               i, inputVector[i], INPUT_AMPLITUDE));
            }
        }
    }
}
