package chapter1.part4;

import java.util.Arrays;

public class StaticSETofInts {
    private int[] a;

    public StaticSETofInts(int[] keys) {
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            a[i] = keys[i]; // defensive copy
        Arrays.sort(a);
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

    public int howMany(int key) {
        int rank = rank(key);
        if (rank == -1) {
            return 0;
        } else {
            int smallestIndex = findSmallest(key, rank);
            int largestIndex = findLargest(key, rank);
            return largestIndex - smallestIndex + 1;
        }
    }

    private int findSmallest(int key, int rank) {
        int lo = 0;
        int hi = rank - 1;
        int smallest = rank;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            //no need to check key < a[mid] here because the array is already sorted, so it never happens
            if (key > a[mid]) lo = mid + 1;
            else {
                smallest = mid;
                hi = mid - 1;
            }
        }
        return smallest;
    }

    private int findLargest(int key, int rank) {
        int lo = rank + 1;
        int hi = a.length - 1;
        int largest = rank;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else {
                largest = mid;
                lo = mid + 1;
            }
        }
        return largest;
    }

    private int rank(int key) { // Binary search.
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) { // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
}
