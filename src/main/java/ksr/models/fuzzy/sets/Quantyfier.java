package ksr.models.fuzzy.sets;

import ksr.models.fuzzy.functions.IMembershipFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quantyfier implements IFuzzy {
    private IMembershipFunction membershipFunction;
    private String label;
    private boolean relative;

    @Override
    public double membership(double x) {
        return membershipFunction.countDegree(x);
    }

    @Override
    public double cardinalNumber() {
        return membershipFunction.distance();
    }

}
