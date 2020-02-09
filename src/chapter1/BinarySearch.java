package chapter1;

import java.util.Arrays;

public class BinarySearch {
    //recursive version
    public static int rankRecursive(int[] a, int key, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) {
            return rankRecursive(a, key, lo, mid - 1);
        } else if (key > a[mid]) {
            return rankRecursive(a, key, mid + 1, hi);
        } else {
            return mid;
        }
    }

    //iterative version
    public static int rankIterative(int[] a, int key, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
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

    public static void main(String[] args) {
        int key = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = (int) (Math.random() * n);
        }
        Arrays.sort(a);
        /*for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }*/
        System.out.println();
        int rankR = rankRecursive(a, key, 0, n - 1);
        int rankI = rankIterative(a, key, 0, n - 1);
        System.out.println(key + " " + rankR + " " + rankI);
    }
}
