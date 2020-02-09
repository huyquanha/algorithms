package chapter1.part2;

import edu.princeton.cs.algs4.StdDraw;

//Visual Counter
public class Exercise10 {
    private int N, max, operations, value;

    public Exercise10(int N, int max) {
        this.N = N;
        this.max = max;
        operations =0;
        value=0;
        StdDraw.setXscale(0,N);
        StdDraw.setYscale(-max, max);
        StdDraw.setPenRadius(0.005);
    }

    public void increment() {
        if (possible()) {
            operations++;
            value++;
        }
    }

    public void decrement() {
        if (possible()) {
            operations++;
            value--;
        }
    }

    private boolean possible() {
        return operations < N && Math.abs(value) < max;
    }

    public void addDataValue() {
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(operations, value);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        Exercise10 visualCounter = new Exercise10(N, max);
        for (int i=0; i< trials; i++) {
            boolean increment = Math.random() < 0.5;
            if (increment) {
                visualCounter.increment();
            }
            else {
                visualCounter.decrement();
            }
            visualCounter.addDataValue();
        }
    }
}
