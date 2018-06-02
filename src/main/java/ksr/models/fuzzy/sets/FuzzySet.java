package ksr.models.fuzzy.sets;

import ksr.models.fuzzy.functions.IMembershipFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FuzzySet implements IFuzzy {

    private String label;
    private List<FuzzySet> innerSets;
    private IMembershipFunction membershipFunction;
    private List<Double> values;
    private double[] membershipValues;

    public FuzzySet(String label, IMembershipFunction membershipFunction, List<Double> values) {
        this.label = label;
        this.membershipFunction = membershipFunction;
        this.values = values;
        this.innerSets = new ArrayList<>();
        this.membershipValues = new double[values.size()];

        for (int i = 0; i < membershipValues.length; i++) {
            this.membershipValues[i] = membership(values.get(i));
        }

    }

    @Override
    public double membership(double x) {
        return membershipFunction.countDegree(x);
    }

    @Override
    public double cardinalNumber() {
        double cardinal = 0;

        for (double membershipValue : membershipValues) {
            if (membershipValue > 0) {
                cardinal++;
            }
        }
        return cardinal / values.size();
    }

    public FuzzySet intersect(FuzzySet inter) {
        innerSets.add(inter);
        label = label + " and " + inter.getLabel();

        for (int i = 0; i < membershipValues.length; i++) {
            membershipValues[i] = Math.min(membershipValues[i], inter.getMembershipValues()[i]);
        }

        return this;
    }

    public FuzzySet union(FuzzySet union) {
        innerSets.add(union);
        label = label + " or " + union.getLabel();

        for (int i = 0; i < membershipValues.length; i++) {
            membershipValues[i] = Math.max(membershipValues[i], union.getMembershipValues()[i]);
        }

        return this;
    }
}
