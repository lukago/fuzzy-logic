package ksr.models.fuzzy;

import ksr.models.fuzzy.functions.IMembershipFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FuzzySet {

    private String label;
    private List<FuzzySet> innerSets;
    private IMembershipFunction membershipFunction;
    private List<Double> values;
    private double membershipValues[];

    public FuzzySet(String label, IMembershipFunction membershipFunction, List<Double> values) {
        this.label = label;
        this.membershipFunction = membershipFunction;
        this.values = values;
        this.innerSets = new ArrayList<>();
        this.membershipValues = new double[values.size()];

        for (int i = 0; i < membershipValues.length; i++) {
            this.membershipValues[i] = membershipFunction.countDegree(values.get(i));
        }

    }

    public FuzzySet substract(FuzzySet plus) {
        innerSets.add(plus);
        label = label + " and " + plus.getLabel();

        for (int i = 0; i < membershipValues.length; i++) {
            if (membershipValues[i] > plus.membershipValues[i]) {
                membershipValues[i] = plus.membershipValues[i];
            }
        }

        return this;
    }

    public FuzzySet sum(FuzzySet minus) {
        innerSets.add(minus);
        label = label + " or " + minus.getLabel();

        for (int i = 0; i < membershipValues.length; i++) {
            if (membershipValues[i] > minus.membershipValues[i]) {
                membershipValues[i] = minus.membershipValues[i];
            }
        }

        return this;
    }

    public double cardinalNumber() {
        double cardinal = 0;

        for (double membershipValue : membershipValues) {
            if (membershipValue > 0) {
                cardinal++;
            }
        }
        return cardinal / values.size();
    }
}
