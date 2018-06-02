package ksr.models.fuzzy.functions;

public class TriangularFunction implements IMembershipFunction {
    private double a;
    private double b;
    private double c;

    public TriangularFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double countDegree(double x) {
        if (x == b) {
            return 1;
        } else if (x > a && x < b) {
            return 1.0 / (b - a) * x + 1.0 - (1.0 / (b - a)) * b;
        } else if (x > b && x < c) {
            return 1.0 / (b - c) * x + 1.0 - (1.0 / (b - c)) * b;
        } else {
            return 0;
        }
    }

    @Override
    public double distance() {
        return c;
    }

    @Override
    public double square() {
        return c - a;
    }
}
