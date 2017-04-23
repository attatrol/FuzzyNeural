package com.github.attatrol.fuzzyneural.ui.javafx.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.github.attatrol.preprocessing.ui.i18n.I18nProvider;

public class FuzzyNeuralI18nProvider implements I18nProvider {

    /**
     * Singleton instance.
     */
    public static final FuzzyNeuralI18nProvider INSTANCE = new FuzzyNeuralI18nProvider();

    public static final String UNKNOWN = "VALUE_MISSING";

    /**
     * Current bundle.
     */
    private ResourceBundle currentBundle;

    /**
     * Default ctor.
     */
    private FuzzyNeuralI18nProvider() {
        currentBundle = ResourceBundle.getBundle("fuzzyneuralui");
    }

    @Override
    public String getValue(String key) {
        try {
            return currentBundle.getString(key);
        }
        catch (MissingResourceException ex) {
            return UNKNOWN;
        }
    }

    @Override
    public String getValue(Object object) {
        return getValue(String.format("name.%s", object.getClass().getName()));
    }
}