package ksr.models.fuzzy;

import ksr.models.fuzzy.functions.IMembershipFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FuzzySet {

    private String label;
    private IMembershipFunction membershipFunction;
    private List<Double> values;

    public FuzzySet(String label, IMembershipFunction membershipFunction, List<Double> values) {
        this.label = label;
        this.membershipFunction = membershipFunction;
        this.values = values;
    }
}
