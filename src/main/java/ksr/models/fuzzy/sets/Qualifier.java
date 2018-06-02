package ksr.models.fuzzy.sets;

import ksr.models.fuzzy.functions.IMembershipFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Qualifier implements IFuzzy {

    private FuzzySet data;
    private FuzzySet outer;
    private List<Qualifier> innerQualifiers;
    private double[] membershipValues;

    public Qualifier(String name, IMembershipFunction func, List<Double> values, FuzzySet fuzzySet) {
        this.data = new FuzzySet(name, func, values);
        this.outer = new FuzzySet(fuzzySet.getLabel(), fuzzySet.getMembershipFunction(),
                new ArrayList<>(fuzzySet.getValues()));
        this.innerQualifiers = new ArrayList<>();
        this.membershipValues = new double[outer.getValues().size()];


        for (int i = 0; i < membershipValues.length; i++) {
            this.membershipValues[i] = membership(values.get(i));
        }

        for (int i = 0; i < membershipValues.length; i++) {
            if (membershipValues[i] > outer.getMembershipValues()[i]) {
                membershipValues[i] = outer.getMembershipValues()[i];
            }
        }

        outer.setMembershipValues(this.membershipValues);
    }


    @Override
    public double membership(double x) {
        return data.getMembershipFunction().countDegree(x);
    }

    @Override
    public double cardinalNumber() {
        double cardinal = 0;

        for (double value : membershipValues) {
            if (value > 0) {
                cardinal++;
            }
        }

        return cardinal / outer.getMembershipValues().length;
    }
}
