package chapter1.part4;

import java.util.Arrays;

//Ex 1.4.21
public class StaticSetOfIntsDistinct {
    private int[] a;

    public StaticSetOfIntsDistinct(int[] keys) {
        a = new int[keys.length];
        for (int i=0; i< keys.length; i++) {
            a[i] = keys[i]; //defensive copy
        }
        Arrays.sort(a); //now the same integers stay together
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

    private int rank(int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if (key < a[mid]) {
                //we move hi to pass all elements that == a[mid]
                while (mid > 0 && a[mid-1] == a[mid]) {
                    mid--;
                }
                hi = mid - 1;
            }
            else if (key > a[mid]) {
                //we move low pass all elements that == a[mid]
                while (mid < a.length && a[mid+1] == a[mid]) {
                    mid++;
                }
                lo = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }
}
