package com.github.attatrol.fuzzyneural.ui.javafx.factories;

import java.util.Optional;

import com.github.attatrol.fuzzyneural.anfis.learning.AnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.anfis.learning.HybridAnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.ui.javafx.utils.PositiveDoubleParsingTextField;
import com.github.attatrol.fuzzyneural.ui.javafx.utils.PositiveIntegerParsingTextField;
import com.github.attatrol.preprocessing.ui.misc.GenericValueReturnDialog;
import com.github.attatrol.preprocessing.ui.misc.UiUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HybridAnfisLearningProcessorFactory implements AnfisLearningProcessorFactory {

    @Override
    public AnfisLearningProcessor create() {
        Optional<HybridAnfisLearningProcessor> optional =
                (new FactoryDialog()).showAndWait();
        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            return null;
        }
    }

    private static class FactoryDialog extends GenericValueReturnDialog<HybridAnfisLearningProcessor> {

        private GridPane pane = UiUtils.getGridPane();

        private PositiveDoubleParsingTextField changeSpeedTextField = new PositiveDoubleParsingTextField();

        private PositiveIntegerParsingTextField epochSizeTextField = new PositiveIntegerParsingTextField();

        private PositiveIntegerParsingTextField epochsToMaxChangeSpeedTextField = new PositiveIntegerParsingTextField();

        private HybridAnfisLearningProcessor result;

        public FactoryDialog() {
            super();
            this.setTitle("Hybrid learning processor");
            pane.add(new Label("Set max change speed factor:"), 0, 0);
            pane.add(changeSpeedTextField, 0, 1);
            pane.add(new Label("Set number of records in learning epoch:"), 0, 2);
            pane.add(epochSizeTextField, 0, 3);
            pane.add(new Label("Set number of epochs to reach max change speed factor:"), 0, 4);
            pane.add(epochsToMaxChangeSpeedTextField, 0, 5);
            changeSpeedTextField.setTextAndValue(0.1);
            epochSizeTextField.setTextAndValue(100);
            epochsToMaxChangeSpeedTextField.setTextAndValue(5);
            getDialogPane().setContent(pane);
        }

        @Override
        protected HybridAnfisLearningProcessor createResult() {
            return result;
        }

        @Override
        protected void validate() throws Exception {
            double changeSpeed = changeSpeedTextField.getValueProperty().get();
            int epochSize = epochSizeTextField.getValueProperty().get();
            int epochsToMaxChangeSpeed = epochsToMaxChangeSpeedTextField.getValueProperty().get();
            if (changeSpeed <= 0. || changeSpeed >= 1.) {
                throw new IllegalArgumentException("Change speed must be greater than 0 and lesser than 1");
            }
            if (epochSize < 1) {
                throw new IllegalArgumentException("Epoch size must be a natural number");
            }
            if (epochsToMaxChangeSpeed < 1) {
                throw new IllegalArgumentException("Number of epochs to reach max change speed must be a natural number");
            }
            result = HybridAnfisLearningProcessor.getHybridAnfisLearningProcessor(changeSpeed, epochSize, epochsToMaxChangeSpeed);
        }
        
    }

}
