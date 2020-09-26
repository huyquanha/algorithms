package web.week5;

public class Point {
    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (this.getClass() != other.getClass()) return false;
        Point that = (Point) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
