package com.github.attatrol.fuzzyneural.anfis.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.attatrol.fuzzyneural.anfis.Anfis;
import com.github.attatrol.fuzzyneural.anfis.analyzer.AnfisAnalyticalProcessor;
import com.github.attatrol.fuzzyneural.anfis.learning.AnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;

public final class DefaultAnfisFactory {

    private DefaultAnfisFactory() {}

    public static Anfis create(int inputVectorSize, int fuzzificationGranularity,
            int antecedentDepth, FuzzifierLayerFactory flFactory, TNorm tNorm,
            AnfisAnalyticalProcessor analyzer, AnfisLearningProcessor learner) {
        // 0. Input checks
        if (inputVectorSize < 1) {
            throw new IllegalArgumentException("Bad input vector size");
        }
        if (fuzzificationGranularity < 1) {
            throw new IllegalArgumentException("Fuzzification granularity should be a positive number");
        }
        if (antecedentDepth < 1) {
            throw new IllegalArgumentException("Antecedent depth should be a positive number");
        }
        // 1. produce fuzzifier layer
        ModifiableFuzzySet[][] fuzzifierNeurons = new ModifiableFuzzySet[inputVectorSize][];
        for (int i = 0; i < inputVectorSize; i++) {
            fuzzifierNeurons[i] = flFactory.coverSupport(fuzzificationGranularity);
        }
        // 2. produce antecedent layer
        final int basicAntecedentLayerSize = inputVectorSize * fuzzificationGranularity;
        final int[][] antecedentParents = getAntecedentParents(basicAntecedentLayerSize, antecedentDepth);
        final int[][] antecedentBasicParents = getAntecedentBasicParents(antecedentParents, basicAntecedentLayerSize);
        final int[][] antecedentChildren = getAntecedentChildren(antecedentParents);
        final double[] antecedentNeuronWeights =new double[antecedentParents.length];
        // 3. produce consequent layer
        final double[][] consequentCoefficients = new double[antecedentParents.length][inputVectorSize];
        final double[] consequentBiases = new double[antecedentParents.length];
        final double[] consequentValues = new double[antecedentParents.length];
        Random random = new Random();
        for (int i = 0; i < antecedentParents.length; i++) {
            for (int j = 0; j < inputVectorSize; j++) {
                consequentCoefficients[i][j] = random.nextDouble() - 0.5;
            }
            consequentBiases[i] = random.nextDouble() - 0.5;
        }
        
        return new Anfis(fuzzifierNeurons, tNorm, antecedentNeuronWeights,
                antecedentParents, antecedentBasicParents, antecedentChildren,
                consequentCoefficients, consequentBiases, consequentValues,
                analyzer, learner);
    }

    private static int[][] getAntecedentParents(int basicAntecedentLayerSize, int antecedentDepth) {
        final int[] antecedentLayerBorders = getAntecedentLayerBorders(basicAntecedentLayerSize, antecedentDepth);
        final int[][] antecedentParents = new int[antecedentLayerBorders[antecedentLayerBorders.length - 1]][];
        // set first layer
        final int[] temporalFirstLayer = new int[basicAntecedentLayerSize];
        for (int i = 0; i < basicAntecedentLayerSize; i++) {
            antecedentParents[i] = new int[1];
            antecedentParents[i][0] = i;
            temporalFirstLayer[i] = i;
        }
        // set complex layers
        for (int i = 1; i < antecedentLayerBorders.length; i++) {
            final int previousLayerBeginning = i == 1 ? 0 :antecedentLayerBorders[i - 2];
            final int newLayerBeginning = antecedentLayerBorders[i - 1];
            int k = newLayerBeginning;
            for (int j = previousLayerBeginning; j < newLayerBeginning; j++) {
                List<Integer> basicParents = getBasicParents(antecedentParents, j, basicAntecedentLayerSize);
                for (int l = 0; l < basicAntecedentLayerSize; l++) {
                    if (!basicParents.contains(l)) {
                        antecedentParents[k] = new int[2];
                        antecedentParents[k][0] = j;
                        antecedentParents[k][1] = l;
                        k++;
                    }
                }
            }
        }
        return antecedentParents;
    }

    private static int[][] getAntecedentBasicParents(int[][] antecedentParents, int basicAntecedentLayerSize) {
        int[][] basicParents = new int[antecedentParents.length][];
        for (int i = 0; i < antecedentParents.length; i++) {
            List<Integer> basicParentsAsList = getBasicParents(antecedentParents, i, basicAntecedentLayerSize);
            basicParents[i] = new int[basicParentsAsList.size()];
            for (int j = 0; j < basicParentsAsList.size(); j++) {
                basicParents[i][j] = basicParentsAsList.get(j);
            }
        }
        return basicParents;
    }

    private static int[][] getAntecedentChildren(int[][] antecedentParents) {
        int[][] children = new int[antecedentParents.length][];
        for (int i = 0; i < antecedentParents.length; i++) {
            List<Integer> childrenAsList = new ArrayList<>();
            for (int j = 0; j < antecedentParents.length; j++) {
                for (int parent : antecedentParents[j]) {
                    if (parent == i) {
                        childrenAsList.add(j);
                    }
                }
            }
            children[i] = new int[childrenAsList.size()];
            for (int j = 0; j < childrenAsList.size(); j++) {
                children[i][j] = childrenAsList.get(j);
            }
        }
        return children;
    }

    private static int[] getAntecedentLayerBorders(int basicAntecedentLayerSize, int antecedentDepth) {
        final int[] antecedentLayerSizes = new int[antecedentDepth];
        antecedentLayerSizes[0] = basicAntecedentLayerSize;
        for (int i = 1; i < antecedentDepth; i++) {
            long numerator = basicAntecedentLayerSize;
            long denominator = 1L;
            for (int j = 1; j <= i; j++) {
                numerator = Math.multiplyExact(numerator, basicAntecedentLayerSize - j);
                denominator *= Math.multiplyExact(denominator, j + 1);
            }
            antecedentLayerSizes[i] = Math.toIntExact(numerator / denominator);
        }
        final int[] relativeAntecedentLayerSizes = new int[antecedentDepth];
        relativeAntecedentLayerSizes[0] = basicAntecedentLayerSize;
        for (int i = 1; i < antecedentDepth; i++) {
            relativeAntecedentLayerSizes[i] = relativeAntecedentLayerSizes[i - 1]
                    + antecedentLayerSizes[i];
        }
        return relativeAntecedentLayerSizes;
    }

    private static List<Integer> getBasicParents(int[][] antecedentParents,
            int antecedentIndex, int basicAntecedentLayerSize) {
        List<Integer> result = new ArrayList<>();
        for (int parentIndex : antecedentParents[antecedentIndex]) {
            if (parentIndex < basicAntecedentLayerSize) {
                result.add(parentIndex);
            }
            else {
                result.addAll(getBasicParents(antecedentParents, parentIndex, basicAntecedentLayerSize));
            }
        }
        return result;
    }
}
