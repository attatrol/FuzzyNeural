package com.github.attatrol.fuzzyneural.fuzzyset.defuzzification;

import com.github.attatrol.fuzzyneural.fuzzyset.FuzzySet;

/**
 * Fuzzy sets may be mapped to some numeric value by variety of methods.
 * @author atta_troll
 *
 */
public enum DefuzzificationMethod {

    FIRST_OF_MAXIMA {
        @Override
        public double defuzzify(FuzzySet set) {
            double x = set.leftSupportBorder();
            final double rightSupportBorder = set.rightSupportBorder();
            double xMax = x;
            double xMaxGrade = set.getGradeFunction(xMax);
            while (x < rightSupportBorder) {
                final double gradeValue = set.getGradeFunction(x);
                if (gradeValue > xMaxGrade) {
                    xMaxGrade = gradeValue;
                    xMaxGrade = x;
                }
                x += EPS;
            }
            return xMax;
        }

        @Override
        public double defuzzify(double[] gradeValues, double xMin, double xStep) {
            int fom = 0;
            double maxValue = gradeValues[0];
            for (int  i = 1; i < gradeValues.length; i++) {
                if(gradeValues[i] > maxValue) {
                    fom = i;
                    maxValue = gradeValues[i];
                }
            }
            return xMin + fom * xStep;
        }
    },
    LAST_OF_MAXIMA {
        @Override
        public double defuzzify(FuzzySet set) {
            double x = set.leftSupportBorder();
            final double rightSupportBorder = set.rightSupportBorder();
            double xMax = x;
            double xMaxGrade = set.getGradeFunction(xMax);
            while (x < rightSupportBorder) {
                final double gradeValue = set.getGradeFunction(x);
                if (gradeValue >= xMaxGrade) {
                    xMaxGrade = gradeValue;
                    xMaxGrade = x;
                }
                x += EPS;
            }
            return xMax;
        }

        @Override
        public double defuzzify(double[] gradeValues, double xMin, double xStep) {
            int fom = 0;
            double maxValue = gradeValues[0];
            for (int  i = 1; i < gradeValues.length; i++) {
                if(gradeValues[i] >= maxValue) {
                    fom = i;
                    maxValue = gradeValues[i];
                }
            }
            return xMin + fom * xStep;
        }
    },
    CENTER_OF_GRAVITY {
        @Override
        public double defuzzify(FuzzySet set) {
            double gradeSum = 0.;
            double gradeCoordinateSum = 0.;
            double x = set.leftSupportBorder();
            final double rightSupportBorder = set.rightSupportBorder();
            while (x < rightSupportBorder) {
                final double gradeValue = set.getGradeFunction(x);
                gradeSum += gradeValue;
                gradeCoordinateSum += gradeValue * x;
                x += EPS;
            }
            return gradeSum != 0 ? gradeCoordinateSum / gradeSum
                    : set.leftSupportBorder() + (set.rightSupportBorder()
                            - set.leftSupportBorder()) / 2.;
        }

        @Override
        public double defuzzify(double[] gradeValues, double xMin, double xStep) {
            double gradeSum = 0.;
            double gradeCoordinateSum = 0.;
            double x = xMin;
            for (int i = 0; i < gradeValues.length; i++) {
                gradeSum += gradeValues[i];
                gradeCoordinateSum += gradeValues[i] * x;
                x += xStep;
            }
            return gradeSum != 0 ? gradeCoordinateSum / gradeSum
                    : xMin + xStep * gradeValues.length / 2;
        }
    };

    public static final double EPS = 0.001;

    public abstract double defuzzify(FuzzySet set);

    public abstract double defuzzify(double[] gradeValues, double xMin, double xStep);

}
