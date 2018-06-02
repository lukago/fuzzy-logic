package ksr.models.fuzzy.functions;

public class DiscreteFunction implements IMembershipFunction {

    private double value;

    public DiscreteFunction(double value) {
        this.value = value;
    }

    @Override
    public double countDegree(double x) {
        return x == value ? 1 : 0;
    }

    @Override
    public double distance() {
        return 0;
    }
}
