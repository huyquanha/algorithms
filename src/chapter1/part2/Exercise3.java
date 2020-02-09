package chapter1.part2;

import edu.princeton.cs.algs4.*;

import java.awt.*;

public class Exercise3 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double min = Double.parseDouble(args[1]);
        double max = Double.parseDouble(args[2]);
        StdDraw.setScale(min,max);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.BLACK);
        Interval2D[] intervals = new Interval2D[N];
        int[] intersects = new int[N];
        int[] contains = new int[N];
        Interval1D[] widths = new Interval1D[N];
        Interval1D[] heights = new Interval1D[N];
        for (int i=0; i< N; i++) {
            double xLo = StdRandom.uniform(min,max);
            double xHi = StdRandom.uniform(min,max);
            double yLo = StdRandom.uniform(min,max);
            double yHi = StdRandom.uniform(min,max);
            if (xLo > xHi) {
                double tmp = xLo;
                xLo = xHi;
                xHi = tmp;
            }
            if (yLo > yHi) {
                double tmp = yLo;
                yLo = yHi;
                yHi = tmp;
            }
            Interval1D x = new Interval1D(xLo, xHi);
            Interval1D y = new Interval1D(yLo, yHi);
            widths[i] = x;
            heights[i] = y;
            intervals[i] = new Interval2D(x,y);
            intervals[i].draw();
            for (int j=i-1; j>=0; j--) {
                if (intervals[i].intersects(intervals[j])) {
                    intersects[i]++;
                    intersects[j]++;
                }
                if (intervals[j].contains(new Point2D(xLo, yLo)) &&
                        intervals[j].contains(new Point2D(xHi,yHi))) {
                    contains[j]++;
                }
                if (intervals[i].contains(new Point2D(widths[j].min(),heights[j].min())) &&
                        intervals[i].contains(new Point2D(widths[j].max(),heights[j].max()))) {
                    contains[i]++;
                }
            }
        }
        System.out.println("Intersects And Contains: ");
        for (int i=0; i< N ;i++) {
            System.out.println(i+" "+ intersects[i] + " "+ contains[i]);
        }
    }
}
