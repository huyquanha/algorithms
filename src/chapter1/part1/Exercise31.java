package chapter1.part1;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Exercise31 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double p = Double.parseDouble(args[1]);
        double dotSize = 0.05;
        StdDraw.setPenRadius(dotSize/2);
        double[] x = new double[N];
        double[] y = new double[N];
        double r = 100;
        StdDraw.setXscale(-r,r);
        StdDraw.setYscale(-r,r);
        //we draw a circle at the center of the (x,y)
        StdDraw.circle(0,0,r);
        //the first dot we dictate it at (r,0)
        x[0]=r;
        y[0]=0;
        StdDraw.point(x[0],y[0]);
        //each equally spaced dot lies 2pi*r/N from each other
        //therefore, they create an angle of 2pi/N from each other
        //from this angle, we can use Math.sin() and Math.cos() to calculate x and y
        for (int i=1; i< N; i++) {
            double angleToXAxis = 2*Math.PI/N*i;
            x[i] = r * Math.cos(angleToXAxis);
            y[i] = r * Math.sin(angleToXAxis);
            StdDraw.point(x[i],y[i]);
        }
        StdDraw.setPenColor(Color.GRAY);
        for (int i=0; i<N; i++) {
            for (int j=i+1; j<N; j++) {
                double probability = Math.random();
                if (probability >= p) {
                    StdDraw.line(x[i],y[i],x[j],y[j]);
                }
            }
        }
    }
}
