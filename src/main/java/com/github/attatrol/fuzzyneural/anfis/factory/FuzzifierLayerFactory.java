package com.github.attatrol.fuzzyneural.anfis.factory;

import com.github.attatrol.fuzzyneural.fuzzyset.ModifiableFuzzySet;

/**
 * ANFIS fuzzifier layer factory
 * @author atta_troll
 *
 */
public interface FuzzifierLayerFactory {

    /**
     * Covers support set with a defined number of fuzzy sets.
     * @param setFootprint
     * @param setNumber
     * @return
     */
    public ModifiableFuzzySet[] coverSupport(int setNumber);

}
