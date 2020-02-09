package chapter1.part1;

import java.util.Arrays;

public class Exercise22 {
    public static int rankRecursiveWithTrace(int[] a, int key, int lo, int hi, String depth) {
        System.out.println(depth + lo + " " + hi);
        if (lo>hi) return -1;
        int mid = lo + (hi-lo)/2;
        depth += "\t";
        if (key < a[mid]) {
            return rankRecursiveWithTrace(a, key, lo, mid-1, depth);
        }
        else if (key > a[mid]) {
            return rankRecursiveWithTrace(a, key, mid+1, hi, depth);
        }
        else {
            return mid;
        }
    }

    public static void main(String[] args) {
        int key = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = (int) (Math.random() * n);
        }
        Arrays.sort(a);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
        int rankR = rankRecursiveWithTrace(a, key, 0, n - 1, "");
        System.out.println(key + " " + rankR + " " + rankR);
    }
}
