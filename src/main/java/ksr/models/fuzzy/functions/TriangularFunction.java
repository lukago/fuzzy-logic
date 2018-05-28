package ksr.models.fuzzy.functions;

public class TriangularFunction implements IMembershipFunction {

    private double left;
    private double right;

    public TriangularFunction(double left, double right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double countDegree(double x) {
        if (x > left - right && x < left + right) {
            if (x <= left) {
                return Math.abs(Math.abs(x) - Math.abs(left) - right) / right;
            } else {
                return Math.abs(Math.abs(left) + right - Math.abs(x)) / right;
            }
        }

        return 0;
    }

    @Override
    public double distance() {
        return right;
    }

    @Override
    public double square() {
        return right - left;
    }
}
