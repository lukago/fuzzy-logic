package ksr.models.fuzzy.functions;

public interface IMembershipFunction {
    double countDegree(double x);

    double distance();

    double square();
}
