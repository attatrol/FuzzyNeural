package com.github.attatrol.fuzzyneural.fuzzyset;

import com.github.attatrol.fuzzyneural.anfis.Anfis;

public class AnfisHistogramFuzzySet extends HistogramFuzzySet implements ModifiableFuzzySet {

    public static final int ANFIS_FUZZY_SET_GRANULARITY = 256;

    public static final double ANFIS_FUZZY_SET_X_QUANTUM = Anfis.INPUT_AMPLITUDE / ANFIS_FUZZY_SET_GRANULARITY;

    public static final double MODIFICATION_FADING_POWER = 0.03;

    public AnfisHistogramFuzzySet(double xMin, double[] histogram) {
        this(xMin, histogram, ANFIS_FUZZY_SET_X_QUANTUM);
    }

    public AnfisHistogramFuzzySet(double xMin, double[] histogram, double xStep) {
        super(xMin, histogram, xStep);
    }

    @Override
    public void modify(double modificationPoint, double modificationPower) {
        if (modificationPoint < xMin || modificationPoint >= xMax) {
            return;
        }
        else {
            final int index = (int) ((modificationPoint - xMin) / xStep);
            modifyHistogram(index, modificationPower);
            if (modificationPower > 0) {
                double currentModPower = modificationPower - MODIFICATION_FADING_POWER;
                for (int i = index + 1; i < ANFIS_FUZZY_SET_GRANULARITY; i++) {
                    if (currentModPower < 0.) {
                        break;
                    }
                    modifyHistogram(i, currentModPower);
                    currentModPower -= MODIFICATION_FADING_POWER;
                }
                currentModPower = modificationPower - MODIFICATION_FADING_POWER;
                for (int i = index - 1; i > 0; i--) {
                    if (currentModPower < 0.) {
                        break;
                    }
                    modifyHistogram(i, currentModPower);
                    currentModPower -= MODIFICATION_FADING_POWER;
                }
            }
            else {
                double currentModPower = modificationPower + MODIFICATION_FADING_POWER;
                for (int i = index + 1; i < ANFIS_FUZZY_SET_GRANULARITY; i++) {
                    if (currentModPower < 0.) {
                        break;
                    }
                    modifyHistogram(i, currentModPower);
                    currentModPower += MODIFICATION_FADING_POWER;
                }
                currentModPower = modificationPower - MODIFICATION_FADING_POWER;
                for (int i = index - 1; i > 0; i--) {
                    if (currentModPower < 0.) {
                        break;
                    }
                    modifyHistogram(i, currentModPower);
                    currentModPower += MODIFICATION_FADING_POWER;
                }
            }
        }
    }

    private void modifyHistogram(int index, double power) {
        histogram[index] -= power;
        if (histogram[index] < 0.) {
            histogram[index] = 0.;
        }
        else if (histogram[index] > 1.) {
            histogram[index] = 1.;
        }
    }
}
