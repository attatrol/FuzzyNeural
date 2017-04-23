package com.github.attatrol.fuzzyneural.fuzzyset;

/**
 * Fuzzy set that supports modifications of its value
 * @author atta_troll
 *
 */
public interface ModifiableFuzzySet extends FuzzySet {

    void modify(double modificationPoint, double modificationPower);

}
