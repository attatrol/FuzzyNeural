package com.github.attatrol.fuzzyneural.ui.javafx;

import com.github.attatrol.fuzzyneural.anfis.Anfis;
import com.github.attatrol.fuzzyneural.anfis.analyzer.AnfisAnalyticalProcessor;
import com.github.attatrol.fuzzyneural.anfis.analyzer.DefaultAnfisAnalyticalProcessor;
import com.github.attatrol.fuzzyneural.anfis.factory.DefaultAnfisFactory;
import com.github.attatrol.fuzzyneural.anfis.factory.FuzzifierLayerFactory;
import com.github.attatrol.fuzzyneural.anfis.factory.TriangularHistogramFuzzySetFactory;
import com.github.attatrol.fuzzyneural.anfis.learning.AnfisLearningProcessor;
import com.github.attatrol.fuzzyneural.fuzzyset.tnorm.TNorm;
import com.github.attatrol.fuzzyneural.ui.javafx.i18n.FuzzyNeuralI18nComboBox;
import com.github.attatrol.fuzzyneural.ui.javafx.utils.PositiveIntegerParsingTextField;
import com.github.attatrol.preprocessing.ui.misc.GenericValueReturnDialog;
import com.github.attatrol.preprocessing.ui.misc.UiUtils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AnfisCreationDialog extends GenericValueReturnDialog<Anfis> {
    /*
     * GUI components.
     */
    private PositiveIntegerParsingTextField fuzzificationGranularityTextField = new PositiveIntegerParsingTextField();
    {
        fuzzificationGranularityTextField.getValueProperty().addListener(
                (observable, oldValue,newValue) -> fuzzificationGranularity = newValue);
    }

    private PositiveIntegerParsingTextField antecedentDepthTextField = new PositiveIntegerParsingTextField();
    {
        antecedentDepthTextField.getValueProperty().addListener(
                (observable, oldValue,newValue) -> antecedentDepth = newValue);
    }

    private ComboBox<TNorm> tNormComboBox = new FuzzyNeuralI18nComboBox<>();
    {
        tNormComboBox.getItems()
            .addAll(ModelRegisters.T_NORMS);
        tNormComboBox.valueProperty().addListener(
            (observable, oldValue,newValue) -> tNorm = newValue);
    }

    private AnfisLearnerComboBox learnerComboBox = new AnfisLearnerComboBox();

    /*
     * Immutable internal state.
     */
    private int inputVectorSize;

    private AnfisAnalyticalProcessor analyzer = new DefaultAnfisAnalyticalProcessor();

    private FuzzifierLayerFactory flFactory = TriangularHistogramFuzzySetFactory.produceFactory();

    /*
     * Mutable internal state.
     */
    private int fuzzificationGranularity;

    private int antecedentDepth;

    private TNorm tNorm;

    private Anfis anfis;

    public AnfisCreationDialog(int inputVectorSize) {
        super();
        this.inputVectorSize = inputVectorSize;
        GridPane pane = UiUtils.getGridPane();
        pane.add(new Label("Set fuzzification granularity:"), 0, 0);
        pane.add(fuzzificationGranularityTextField, 0, 1);
        pane.add(new Label("Set antecedent depth:"), 0, 2);
        pane.add(antecedentDepthTextField, 0, 3);
        pane.add(new Label("Select t-norm:"), 0, 4);
        pane.add(tNormComboBox, 0, 5);
        pane.add(new Label("Select anfis learning processor:"), 0, 6);
        pane.add(learnerComboBox, 0, 7);
        getDialogPane().setContent(pane);
        fuzzificationGranularityTextField.setTextAndValue(20);
        antecedentDepthTextField.setTextAndValue(2);
        setTitle("Anfis creation dialog");
    }

    @Override
    protected Anfis createResult() {
        return anfis;
    }

    @Override
    protected void validate() throws Exception {
        if (fuzzificationGranularity < 1) {
            throw new IllegalArgumentException("Fuzzification granularity should be a positive number");
        }
        if (antecedentDepth < 1) {
            throw new IllegalArgumentException("Antecedent depth should be a positive number");
        }
        if (tNorm == null) {
            throw new IllegalArgumentException("T-Norm is not chosen");
        }
        AnfisLearningProcessor learner = learnerComboBox.getChosenLearningProcessor();
        if (learner == null) {
            throw new IllegalArgumentException("Learner is not chosen");
        }
        anfis = DefaultAnfisFactory.create(inputVectorSize, fuzzificationGranularity,
                antecedentDepth, flFactory, tNorm,
                analyzer, learner);
    }

}
