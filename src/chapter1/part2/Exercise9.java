package chapter1.part2;

import edu.princeton.cs.algs4.Counter;

import java.util.Arrays;

public class Exercise9 {
    public static int rank(int[] a, int key, Counter c) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            c.increment();
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static void print(int[] a, int N) {
        for (int i = 0; i < N; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int key = Integer.parseInt(args[1]);
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = (int) (Math.random() * N);
        }
        Arrays.sort(a);
        print(a, N);
        Counter c = new Counter("keys examined");
        System.out.println(rank(a, key, c));
        System.out.println(c);
    }
}
