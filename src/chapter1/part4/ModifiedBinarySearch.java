package chapter1.part4;

import edu.princeton.cs.algs4.In;

import java.util.Arrays;

//Ex 1.4.10
public class ModifiedBinarySearch {
    public static int search(int[] a, int key) {
        //Arrays.sort(a);
        int lo = 0;
        int hi = a.length - 1;
        int smallestIndex = a.length;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] > key) {
                hi = mid - 1;
            } else if (a[mid] < key) {
                lo = mid + 1;
            } else {
                smallestIndex = mid;
                hi = mid-1;
            }
        }
        return smallestIndex != a.length ? smallestIndex : -1;
    }

    private static void print(int[] a) {
        for (int i=0; i< a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] a = In.readInts(args[0]);
        Arrays.sort(a);
        print(a);
        System.out.println(search(a,6));
    }
}
