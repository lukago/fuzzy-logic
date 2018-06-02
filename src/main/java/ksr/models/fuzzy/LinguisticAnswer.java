package ksr.models.fuzzy;

import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Qualificator;
import ksr.models.fuzzy.sets.Quantyfier;

import java.util.ArrayList;
import java.util.List;

public class LinguisticAnswer {

    private LinguisticAnswer() {
    }

    public static List<String> answerSets(List<FuzzySet> fuzzySets, List<Quantyfier> quantyfiers) {
        List<String> answers = new ArrayList<>();
        for (FuzzySet lab : fuzzySets) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double number = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                number += Coefficients.truthfulnessDegree(quantyfier, lab);
                number += Coefficients.adderLenght(lab);
                number += Coefficients.coverageAdderDegree(lab);
                number += Coefficients.imprecisionAdderDegree(lab);
                number += Coefficients.accuracyAdderMeasurement(lab);
                number /= 5.0;

                if (number > max) {
                    max = number;
                    selected = quantyfier;
                }
            }

            String output = String.format("%s of people are %s : %f", selected.getLabel(), lab.getLabel(), max);
            answers.add(output);
        }

        return answers;
    }

    public static List<String> answerQualificators(List<Qualificator> qualificators, List<Quantyfier> quantyfiers) {
        List<String> answers = new ArrayList<>();
        for (Qualificator qua : qualificators) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double number = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                number += Coefficients.truthfulnessDegree(quantyfier, qua.getData());
                number += Coefficients.imprecisionQuantyficatorDegree(quantyfier, qua.getData());
                number += Coefficients.cardinalityQuantifierDegree(quantyfier, qua.getData());
                number += +Coefficients.adderLenght(qua.getData());
                number += Coefficients.coverageAdderDegree(qua.getData());
                number += +Coefficients.cardinalityAdderDegree(qua.getData());
                number += Coefficients.imprecisionAdderDegree(qua.getData());
                number += Coefficients.accuracyAdderMeasurement(qua.getData());
                number += Coefficients.imprecisionQualificatorDegree(qua);
                number += Coefficients.cardinalityQualificatorDegree(qua);
                number += Coefficients.qualificatorLength(qua);
                number /= 11.0;

                if (number > max) {
                    max = number;
                    selected = quantyfier;
                }
            }

            String output = String.format("%s of people are %s : %f", selected.getLabel(), qua.getData().getLabel(), max);
            answers.add(output);
        }

        return answers;
    }
}
