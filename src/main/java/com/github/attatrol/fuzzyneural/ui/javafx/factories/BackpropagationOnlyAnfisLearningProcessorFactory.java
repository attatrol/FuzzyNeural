package com.github.attatrol.fuzzyneural.ui.javafx.factories;

import java.util.Optional;

import com.github.attatrol.fuzzyneural.anfis.learning.AnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.anfis.learning.BackpropagationOnlyAnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.ui.javafx.utils.PositiveDoubleParsingTextField;
import com.github.attatrol.preprocessing.ui.misc.GenericValueReturnDialog;
import com.github.attatrol.preprocessing.ui.misc.UiUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class BackpropagationOnlyAnfisLearningProcessorFactory implements AnfisLearningProcessorFactory {

    @Override
    public AnfisLearningProcessor create() {
        Optional<BackpropagationOnlyAnfisLearningProcessor> optional =
                (new FactoryDialog()).showAndWait();
        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            return null;
        }
    }

    private static class FactoryDialog extends GenericValueReturnDialog<BackpropagationOnlyAnfisLearningProcessor> {

        private GridPane pane = UiUtils.getGridPane();

        private PositiveDoubleParsingTextField changeSpeedTextField = new PositiveDoubleParsingTextField();

        private BackpropagationOnlyAnfisLearningProcessor result;

        public FactoryDialog() {
            super();
            this.setTitle("Backpropagation only learning processor");
            pane.add(new Label("Set change speed factor:"), 0, 0);
            pane.add(changeSpeedTextField, 0, 1);
            changeSpeedTextField.setTextAndValue(0.1);
            getDialogPane().setContent(pane);
        }

        @Override
        protected BackpropagationOnlyAnfisLearningProcessor createResult() {
            return result;
        }

        @Override
        protected void validate() throws Exception {
            double changeSpeed = changeSpeedTextField.getValueProperty().get();
            if (changeSpeed <= 0. || changeSpeed >= 1.) {
                throw new IllegalArgumentException("Change speed must be greater than 0 and lesser than 1");
            }
            result = new BackpropagationOnlyAnfisLearningProcessor(changeSpeed);
        }
        
    }
}
