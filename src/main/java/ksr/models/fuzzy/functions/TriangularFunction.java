package ksr.models.fuzzy.functions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TriangularFunction implements IMembershipFunction {
    private double a;
    private double b;
    private double c;

    @Override
    public double countDegree(double x) {
        if (x == b) {
            return 1;
        }

        if (x > a && x < b) {
            return 1.0 / (b - a) * x + 1.0 - (1.0 / (b - a)) * b;
        }

        if (x > b && x < c) {
            return 1.0 / (b - c) * x + 1.0 - (1.0 / (b - c)) * b;
        }

        return 0;
    }

    @Override
    public double distance() {
        return c - a;
    }
}
