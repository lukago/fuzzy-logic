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
        for (FuzzySet set : fuzzySets) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double number = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                number += Quality.truthfulnessDegree(quantyfier, set);
                number += Quality.sumarizerLenght(set);
                number += Quality.coverageSumarizerDegree(set);
                number += Quality.imprecisionSumarizerDegree(set);
                number += Quality.accuracySumarizerMeasurement(set);
                number /= 5.0;

                if (number > max) {
                    max = number;
                    selected = quantyfier;
                }
            }

            String output = String.format("%s of people have %s : %f\n", selected.getLabel(), set.getLabel(), max);
            answers.add(output);
        }

        return answers;
    }

    public static List<String> answerQualifiers(List<Qualifier> qualifiers, List<Quantyfier> quantyfiers) {
        List<String> answers = new ArrayList<>();
        for (Qualifier qua : qualifiers) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double sum = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                sum += Quality.truthfulnessDegree(quantyfier, qua.getOuter());
                sum += Quality.imprecisionQuantifierDegree(quantyfier, qua.getOuter());
                sum += Quality.cardinalityQuantifierDegree(quantyfier, qua.getOuter());
                sum += Quality.sumarizerLenght(qua.getOuter());
                sum += Quality.coverageSumarizerDegree(qua.getOuter());
                sum += Quality.cardinalitySumarizerDegree(qua.getOuter());
                sum += Quality.imprecisionSumarizerDegree(qua.getOuter());
                sum += Quality.accuracySumarizerMeasurement(qua.getOuter());
                sum += Quality.imprecisionQualifierDegree(qua);
                sum += Quality.cardinalityQualifierDegree(qua);
                sum += Quality.qualifierLength(qua);
                sum /= 11.0;

                if (sum > max) {
                    max = sum;
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
