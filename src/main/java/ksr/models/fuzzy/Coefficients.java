package ksr.models.fuzzy;

public class Coefficients {

    private Coefficients() {
    }

    public static double truthfulnessDegree(Quantyficator quantyficator, FuzzySet fuzzySet) {
        return quantyficator.membership(fuzzySet.cardinalNumber() * fuzzySet.getValues().size());
    }

    public static double imprecisionAdderDegree(FuzzySet fuzzySet) {
        double cardinalNumber = fuzzySet.cardinalNumber();

        for (FuzzySet set : fuzzySet.getInnerSets()) {
            cardinalNumber *= set.cardinalNumber();
        }

        return Math.pow(cardinalNumber, 1.0 / (fuzzySet.getInnerSets().size() + 1));
    }

    public static double coverageAdderDegree(FuzzySet fuzzySet) {
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

    public static double accuracyAdderMeasurement(FuzzySet fuzzySet) {
        double cardinalNumber = fuzzySet.cardinalNumber();

        for (FuzzySet set : fuzzySet.getInnerSets()) {
            if (set.cardinalNumber() > 0) {
                cardinalNumber *= set.cardinalNumber();
            }
        }

        return Math.abs(cardinalNumber - coverageAdderDegree(fuzzySet));
    }

    public static double adderLenght(FuzzySet fuzzySet) {
        return 2.0 * Math.pow(0.5, fuzzySet.getInnerSets().size() + 1);
    }

    public static double CardinalityAdderDegree(FuzzySet fuzzySet) {
        double sum = fuzzySet.cardinalNumber();

        for (FuzzySet set : fuzzySet.getInnerSets()) {
            sum *= set.cardinalNumber();
        }

        return 1 - Math.pow(sum, 1.0 / fuzzySet.getMembershipValues().length);
    }

    public static double cardinalityQuantifierDegree(Quantyficator quantyficator, FuzzySet fuzzySet) {
        return 1.0 - quantyficator.cardinalNumber() / fuzzySet.getMembershipValues().length;
    }

    public static double imprecisionQuantyficatorDegree(Quantyficator quantyficator, FuzzySet fuzzySet) {
        double[] arr = fillarr(fuzzySet);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = quantyficator.membership(arr[i]);
        }

        double cnt = 0;
        for (double val : arr) {
            if (val > 0) {
                cnt++;
            }
        }

        return cnt / fuzzySet.getValues().size();
    }

    private static double[] fillarr(FuzzySet fuzzySet) {
        double[] arr = new double[fuzzySet.getValues().size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        return arr;
    }
}
