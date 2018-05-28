package ksr.models.fuzzy;

import ksr.models.fuzzy.functions.IMembershipFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quantyficator implements IFuzzy {
    private IMembershipFunction membershipFunction;
    private String label;

    @Override
    public double membership(double x) {
        return membershipFunction.countDegree(x);
    }

    @Override
    public double cardinalNumber() {
        return membershipFunction.distance();
    }

}
