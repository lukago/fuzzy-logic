package ksr.models.fuzzy;

import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Qualifier;
import ksr.models.fuzzy.sets.Quantyfier;

import java.util.ArrayList;
import java.util.List;

public class LinguisticAnswer {

    private LinguisticAnswer() {
    }

    public static List<String> answerSets(List<FuzzySet> fuzzySets, List<Quantyfier> quantyfiers, List<Double> weights) {
        List<String> answers = new ArrayList<>();
        for (FuzzySet set : fuzzySets) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double sum = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                sum += Quality.t1TruthfulnessDegree(quantyfier, set) * weights.get(0);
                sum += Quality.t2ImprecisionSumarizerDegree(set) * weights.get(1);
                sum += Quality.t3CoverageSumarizerDegree(set) * weights.get(2);
                sum += Quality.t4AccuracySumarizerMeasurement(set) * weights.get(3);
                sum += Quality.t5SumarizerLenght(set) * weights.get(4);
                sum += Quality.t6imprecisionQuantifierDegree(quantyfier, set) * weights.get(5);
                sum += Quality.t7CardinalityQuantifierDegree(quantyfier, set) * weights.get(6);
                sum += Quality.t8cardinalitySumarizerDegree(set) * weights.get(7);
                sum /= weights.stream().mapToDouble(d -> d).sum();

                if (sum > max) {
                    max = sum;
                    selected = quantyfier;
                }
            }

            String output = String.format("%s of people have %s : %f\n", selected.getLabel(), set.getLabel(), max);
            answers.add(output);
        }

        return answers;
    }

    public static List<String> answerQualifiers(List<Qualifier> qualifiers, List<Quantyfier> quantyfiers, List<Double> weights) {
        List<String> answers = new ArrayList<>();
        for (Qualifier qua : qualifiers) {
            Quantyfier selected = new Quantyfier();
            double max = Double.MIN_VALUE;
            double sum = 0;

            for (Quantyfier quantyfier : quantyfiers) {
                sum += Quality.t1TruthfulnessDegree(quantyfier, qua.getOuter()) * weights.get(0);
                sum += Quality.t2ImprecisionSumarizerDegree(qua.getOuter()) * weights.get(1);
                sum += Quality.t3CoverageSumarizerDegree(qua.getOuter()) * weights.get(2);
                sum += Quality.t4AccuracySumarizerMeasurement(qua.getOuter()) * weights.get(3);
                sum += Quality.t5SumarizerLenght(qua.getOuter()) * weights.get(4);
                sum += Quality.t6imprecisionQuantifierDegree(quantyfier, qua.getOuter()) * weights.get(5);
                sum += Quality.t7CardinalityQuantifierDegree(quantyfier, qua.getOuter()) * weights.get(6);
                sum += Quality.t8cardinalitySumarizerDegree(qua.getOuter()) * weights.get(7);
                sum += Quality.t9ImprecisionQualifierDegree(qua) * weights.get(8);
                sum += Quality.t10CardinalityQualifierDegree(qua) * weights.get(9);
                sum += Quality.t11QualifierLength(qua) * weights.get(10);
                sum /= weights.stream().mapToDouble(d -> d).sum();

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
