package chapter1.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Arrays;

public class Exercise32 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double l = Double.parseDouble(args[1]);
        double r = Double.parseDouble(args[2]);
        //double[] values = In.readDoubles("data.txt");
        double[] values = new double[100];
        for (int i=0; i< 100; i++) {
            values[i] = l + Math.random()* (r-l);
        }

        double interval = (r-l)/N;
        int[] baskets = new int[N];
        for (int i=0; i< values.length; i++) {
            int index = (int) ((values[i]-l)/interval);
            if (index >=0 && index < N) {
                baskets[index]++;
            }
        }

        StdDraw.setXscale(l,r);
        StdDraw.setYscale(0,values.length);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.BLACK);
        //now draw each basket as a column(rectangle)
        for (int i=0; i< N; i++) {
            double height = baskets[i];
            double left = l+i*interval;
            double right = l+(i+1)*interval;
            double center = (left+right)/2;
            StdDraw.rectangle(center, height/2, interval/2, height/2);
        }
    }
}
