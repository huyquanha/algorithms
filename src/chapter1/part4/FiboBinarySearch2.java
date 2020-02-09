package chapter1.part4;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class FiboBinarySearch2 {
    public static int find(int[] a, int key) {
        int N = a.length;
        if (N == 0) {
            return -1;
        }
        if (key < a[0] || key > a[N - 1]) {
            return -1;
        }
        int f1 = 0;
        int f2 = 1;
        int f = f1 + f2;
        while (f < N) {
            f1 = f2;
            f2 = f;
            f = f1 + f2;
        }
        System.out.println("First fibo number >= " + N + " is: " + f);
        int lo = 0;
        int hi = N - 1;
        while (lo <= hi) {
            int mid = lo + f1 - 1;
            if (key == a[mid]) {
                return mid;
            } else if (key > a[mid]) {
                lo = mid + 1;
                f = f2;
                f2 = f1;
                f1 = f - f2;
            } else {
                hi = mid - 1;
                f = f1;
                f2 = f2 - f1;
                f1 = f - f2;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int key = Integer.parseInt(args[1]);
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = i;
        }
        //random shuffle (to mimic real world
        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniform(i, N);
            int tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
        //now sort to prepare for the search
        Arrays.sort(a);
        System.out.println(find(a,key));
    }
}
