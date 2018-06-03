package ksr.models.fuzzy.functions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrapezoidalFunction implements IMembershipFunction {

    private double a;
    private double b;
    private double c;
    private double d;

    @Override
    public double countDegree(double temp) {
        if (temp >= b && temp <= c) {
            return 1;
        }

        if (temp > a && temp < b) {
            return 1.0 / (b - a) * temp + 1.0 - (1.0 / (b - a)) * b;
        }

        if (temp > c && temp < d) {
            return 1.0 / (c - d) * temp + 1.0 - (1.0 / (c - d)) * c;
        }

        return 0;
    }

    @Override
    public double distance() {
        return d - a;
    }
}
