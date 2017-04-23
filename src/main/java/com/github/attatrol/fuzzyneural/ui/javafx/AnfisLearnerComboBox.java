package com.github.attatrol.fuzzyneural.ui.javafx;

import com.github.attatrol.fuzzyneural.anfis.learning.AnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.ui.javafx.factories.AnfisLearningProcessorFactory;

import javafx.scene.control.ComboBox;

public class AnfisLearnerComboBox extends ComboBox<AnfisLearningProcessorFactory> {

    private AnfisLearningProcessor chosen;

    public AnfisLearnerComboBox() {
        super();
        setOnHidden(ev -> {
            final AnfisLearningProcessorFactory factory = getSelectionModel().getSelectedItem();
            final AnfisLearningProcessor lp = factory.create();
            if (lp != null) {
                chosen = lp;
            }
        });
        getItems().addAll(ModelRegisters.ANFIS_LEARNING_PROCESSOR_FACTORIES);
    }

    public AnfisLearningProcessor getChosenLearningProcessor() {
        return chosen;
    }
}
