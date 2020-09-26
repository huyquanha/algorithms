package web.week1.unionFind;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    private double m, s;
    private final double[] x;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n has to be larger than 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials has to be larger than 0");
        }
        t = trials;
        x = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                p.open(r, c);
            }
            x[i] = ((double) p.numberOfOpenSites()) / (n * n);
        }
        m = -1;
        s = -1;
    }

    // sample mean of percolation threshold
    public double mean() {
        if (m == -1) {
            m = StdStats.mean(x);
        }
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (s == -1) {
            s = StdStats.stddev(x);
        }
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (m == -1) {
            m = StdStats.mean(x);
        }
        if (s == -1) {
            s = StdStats.stddev(x);
        }
        return m - CONFIDENCE_95 * s / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (m == -1) {
            m = StdStats.mean(x);
        }
        if (s == -1) {
            s = StdStats.stddev(x);
        }
        return m + CONFIDENCE_95 * s / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.confidenceLo());
        System.out.println(percolationStats.confidenceHi());
    }
}
