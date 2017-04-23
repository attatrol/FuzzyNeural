package com.github.attatrol.fuzzyneural.ui.javafx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.GodelTNorm;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.HamacherTNorm;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.LukasiewitzTNorm;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.ProductTNorm;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;
import com.github.attatrol.fuzzyneural.ui.javafx.factories.AnfisLearningProcessorFactory;
import com.github.attatrol.fuzzyneural.ui.javafx.factories.BackpropagationOnlyAnfisLearningProcessorFactory;
import com.github.attatrol.fuzzyneural.ui.javafx.factories.HybridAnfisLearningProcessorFactory;

public final class ModelRegisters {

    private ModelRegisters() { }

    public static final List<TNorm> T_NORMS;
    static {
        List<TNorm> set = new ArrayList<>();
        set.add(new ProductTNorm());
        set.add(new HamacherTNorm());
        set.add(new GodelTNorm());
        set.add(new LukasiewitzTNorm());
        T_NORMS = Collections.unmodifiableList(set);
    }

    public static final List<AnfisLearningProcessorFactory> ANFIS_LEARNING_PROCESSOR_FACTORIES;
    static {
        List<AnfisLearningProcessorFactory> set = new ArrayList<>();
        set.add(new HybridAnfisLearningProcessorFactory());
        set.add(new BackpropagationOnlyAnfisLearningProcessorFactory());
        ANFIS_LEARNING_PROCESSOR_FACTORIES = Collections.unmodifiableList(set);
    }
}
