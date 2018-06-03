package ksr.models.fuzzy;

import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Qualifier;
import ksr.models.fuzzy.sets.Quantyfier;

public class Quality {

    private Quality() {
    }

    public static double t1TruthfulnessDegree(Quantyfier quantyfier, FuzzySet fuzzySet) {
        return quantyfier.membership(fuzzySet.cardinalNumber() * fuzzySet.getValues().size());
    }

    public static double t2ImprecisionSumarizerDegree(FuzzySet fuzzySet) {
        double cardinalNumber = fuzzySet.cardinalNumber();

        for (FuzzySet set : fuzzySet.getInnerSets()) {
            cardinalNumber *= set.cardinalNumber();
        }

        return Math.pow(cardinalNumber, 1.0 / (fuzzySet.getInnerSets().size() + 1.0));
    }

    public static double t3CoverageSumarizerDegree(FuzzySet fuzzySet) {
        double posValuesCnt = 0;
        double cnt = 0;

        for (int i = 0; i < fuzzySet.getValues().size(); i++) {
            double value = 0;

            if (fuzzySet.getValues().get(i) < fuzzySet.getMembershipValues()[i]) {
                value = fuzzySet.getValues().get(i);
            }

            if (fuzzySet.getValues().get(i) >= fuzzySet.getMembershipValues()[i]) {
                value = fuzzySet.getMembershipValues()[i];
            }

            if (value > 0) {
                cnt++;
            }
        }

        for (Double value : fuzzySet.getValues()) {
            if (value > 0) {
                posValuesCnt++;
            }
        }

        return cnt / posValuesCnt;
    }

    public static double t4AccuracySumarizerMeasurement(FuzzySet fuzzySet) {
        double cardinalNumber = fuzzySet.cardinalNumber();

        for (FuzzySet set : fuzzySet.getInnerSets()) {
            if (set.cardinalNumber() > 0) {
                cardinalNumber *= set.cardinalNumber();
            }
        }

        return Math.abs(cardinalNumber - t3CoverageSumarizerDegree(fuzzySet));
    }

    public static double t5SumarizerLenght(FuzzySet fuzzySet) {
        return 2.0 * Math.pow(0.5, fuzzySet.getInnerSets().size() + 1.0);
    }

    public static double t8cardinalitySumarizerDegree(FuzzySet fuzzySet) {
        double sum = fuzzySet.cardinalNumber();

        for (FuzzySet set : fuzzySet.getInnerSets()) {
            sum *= set.cardinalNumber();
        }

        return 1.0 - Math.pow(sum, 1.0 / fuzzySet.getMembershipValues().length + 1.0);
    }

    public static double t7CardinalityQuantifierDegree(Quantyfier quantyfier, FuzzySet fuzzySet) {
        return 1.0 - quantyfier.cardinalNumber() / fuzzySet.getMembershipValues().length;
    }

    public static double t6imprecisionQuantifierDegree(Quantyfier quantyfier, FuzzySet fuzzySet) {
        double[] arr = fillarr(fuzzySet);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = quantyfier.membership(arr[i]);
        }

        // alpha cut, support
        double cnt = 0;
        for (double val : arr) {
            if (val > 0) {
                cnt++;
            }
        }

        return cnt / fuzzySet.getValues().size();
    }

    public static double t9ImprecisionQualifierDegree(Qualifier qualifier) {
        double sum = qualifier.cardinalNumber();

        for (Qualifier q : qualifier.getInnerQualifiers()) {
            sum *= q.cardinalNumber();
        }

        return 1.0 - Math.pow(sum, 1.0 / (qualifier.getInnerQualifiers().size() + 1.0));
    }

    public static double t10CardinalityQualifierDegree(Qualifier qualifier) {
        return qualifier.cardinalNumber();
    }

    public static double t11QualifierLength(Qualifier qua) {
        return 2.0 * Math.pow(0.5, qua.getInnerQualifiers().size() + 1.0);
    }

    private static double[] fillarr(FuzzySet fuzzySet) {
        double[] arr = new double[fuzzySet.getValues().size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        return arr;
    }
}
