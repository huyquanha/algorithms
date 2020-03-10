package chapter2.part5;

public class Vector implements Comparable<Vector> {
    private int d;
    private double[] components;

    public Vector(double[] components) {
        d = components.length;
        this.components = new double[d];
        for (int i = 0; i < d; i++) {
            this.components[i] = components[i];
        }
    }

    public int compareTo(Vector that) {
        int minD = Math.min(this.d, that.d);
        for (int i = 0; i < minD; i++) {
            if (this.components[i] < that.components[i]) {
                return -1;
            } else if (this.components[i] > that.components[i]) {
                return 1;
            }
        }
        if (this.d < that.d) {
            return -1;
        } else if (this.d > that.d) {
            return 1;
        } else {
            // same dimensions, and all dimenstions are equal
            return 0;
        }
    }
}
