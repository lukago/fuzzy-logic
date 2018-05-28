package ksr.models.fuzzy;

import java.util.ArrayList;
import java.util.List;

public class LinguisticAnswer {

    private LinguisticAnswer() {
    }

    public static List<String> answer(List<FuzzySet> fuzzySets, List<Quantyficator> quantyficators) {
        List<String> answers = new ArrayList<>();
        for (FuzzySet lab : fuzzySets) {
            Quantyficator selected = new Quantyficator();
            double max = Double.MIN_VALUE;
            double number = 0;

            for (Quantyficator quantyficator : quantyficators) {
                number += Coefficients.truthfulnessDegree(quantyficator, lab);
                number += Coefficients.adderLenght(lab);
                number += Coefficients.coverageAdderDegree(lab);
                number += Coefficients.imprecisionAdderDegree(lab);
                number += Coefficients.accuracyAdderMeasurement(lab);
                number /= 5.0;

                if (number > max) {
                    max = number;
                    selected = quantyficator;
                }
            }

            String output = String.format("%s of people are %s : %f", selected.getLabel(), lab.getLabel(), max);
            answers.add(output);
        }

        return answers;
    }
}
