package com.github.attatrol.fuzzyneural.ui.javafx.i18n;

import com.github.attatrol.preprocessing.ui.i18n.I18nComboBox;

public class FuzzyNeuralI18nComboBox<V> extends I18nComboBox<V> {

    public FuzzyNeuralI18nComboBox() {
        super(FuzzyNeuralI18nProvider.INSTANCE);
    }
}
