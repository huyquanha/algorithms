package chapter1.part2;

import edu.princeton.cs.algs4.Point2D;

public class Exercise1 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Point2D[] points = new Point2D[N];
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < N; i++) {
            double x = Math.random();
            double y = Math.random();
            points[i] = new Point2D(x, y);
            for (int j = i - 1; j >= 0; j--) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        System.out.println(minDist);
    }
}
