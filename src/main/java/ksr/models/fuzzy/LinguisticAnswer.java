package ksr.models.fuzzy;

import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Qualifier;
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
                number += Quality.truthfulnessDegree(quantyfier, lab);
                number += Quality.sumarizerLenght(lab);
                number += Quality.coverageSumarizerDegree(lab);
                number += Quality.imprecisionSumarizerDegree(lab);
                number += Quality.accuracySumarizerMeasurement(lab);
                number /= 5.0;

                if (number > max) {
                    max = number;
                    selected = quantyfier;
                }
            }

            String output = String.format("%s of people have %s : %f\n", selected.getLabel(), lab.getLabel(), max);
            answers.add(output);
        }

        return answers;
    }

    public static List<String> answerSets2(List<FuzzySet> fuzzySets, List<Quantyfier> quantyfiers) {
        List<String> answers = new ArrayList<>();
        for (FuzzySet lab : fuzzySets) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double number = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                number += Quality.truthfulnessDegree(quantyfier, lab);
                number /= 1.0;

                if (number > max) {
                    max = number;
                    selected = quantyfier;
                }
            }

            String output = String.format("%s of people have %s : %f\n", selected.getLabel(), lab.getLabel(), max);
            answers.add(output);
        }

        return answers;
    }

    public static List<String> answerQualifiers(List<Qualifier> qualifiers, List<Quantyfier> quantyfiers) {
        List<String> answers = new ArrayList<>();
        for (Qualifier qua : qualifiers) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double number = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                number += Quality.truthfulnessDegree(quantyfier, qua.getOuter());
                number += Quality.imprecisionQuantifierDegree(quantyfier, qua.getOuter());
                number += Quality.cardinalityQuantifierDegree(quantyfier, qua.getOuter());
                number += Quality.sumarizerLenght(qua.getOuter());
                number += Quality.coverageSumarizerDegree(qua.getOuter());
                number += Quality.cardinalitySumarizerDegree(qua.getOuter());
                number += Quality.imprecisionSumarizerDegree(qua.getOuter());
                number += Quality.accuracySumarizerMeasurement(qua.getOuter());
                number += Quality.imprecisionQualifierDegree(qua);
                number += Quality.cardinalityQualifierDegree(qua);
                number += Quality.qualifierLength(qua);
                number /= 11.0;

                if (number > max) {
                    max = number;
                    selected = quantyfier;
                }
            }

            String output = String.format(
                    "%s of people having %s are also %s: %f\n",
                    selected.getLabel(), qua.getOuter().getLabel(), qua.getData().getLabel(), max);
            answers.add(output);
        }

        return answers;
    }
}
