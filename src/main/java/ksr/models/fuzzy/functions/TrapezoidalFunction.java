package ksr.models.fuzzy.functions;

public class TrapezoidalFunction implements IMembershipFunction {
    
    private double topLeft;
    private double bottomLeft;
    private double topRight;
    private double bottomRight;

    public TrapezoidalFunction(double topLeft, double bottomLeft, double topRight, double bottomRight) {
        this.topLeft = topLeft;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
    }

    @Override
    public double countDegree(double x) {
        if (x >= topLeft && x <= topRight) return 1;
        if (x > bottomLeft && x < topLeft) return (x - bottomLeft) / (topLeft - bottomLeft);
        if (x > topRight && x < bottomRight) return (bottomRight - x) / (bottomRight - topRight);
        return 0;
    }

    @Override
    public double distance() {
        return bottomRight - bottomLeft;
    }
}
