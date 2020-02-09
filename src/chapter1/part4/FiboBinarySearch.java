package chapter1.part4;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class FiboBinarySearch {
    //1,1,2,3,5,8,13,21,34,55,...
    //[i, i + 49]
    /*
        N = 50
        F(k) = 55, F(k-1) = 34
        examine F(k-2) = 55 -34 = 21 => a[i+20]
        if (key > a[i+20])
            [i+21, i + 49]
            F(k) = F(k-1) = 34
            F(k-1) = F(k-2) = 21
     */
    //Fk = 55, F(k-1) = 34
    //compute F(k-2) = 55 -34 = 21
    //if key > F(k-2), update F(k) = F(k-1) = 34, F(k-1) = F(k-2) = 21
    //the range is now [i+21, i + 55]
    //continue, compute F(k-2) = 34 -21 = 13
    //check i+21 + 13 = i + 34
    //if key < a[i+34] => update range [i+21, i + 34]
    //F(k) = F(k-2) = 13, F(k-1) = 21 - 13 = 8
    public static int search(int[] a, int key) {
        int N = a.length;
        int fk1 = 1;
        int fk = 1;
        while (fk < N) {
            int tmp = fk;
            fk = fk1 + fk;
            fk1 = tmp;
        }
        //now Fk >= N
        int lo = 0;
        int hi = N - 1;
        while (lo <= hi) {
            int fk2 = fk - fk1;
            //start from 0 => fk2 th element corresponds to index fk2-1 in the array
            if (key < a[lo + fk2 - 1]) {
                hi = lo + fk2 - 2;
                fk = fk2;
                fk1 = fk1 - fk2;
            } else if (key > a[lo + fk2 - 1]) {
                lo = lo + fk2;
                fk = fk1;
                fk1 = fk2;
            } else {
                return lo + fk2 - 1;
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
        System.out.println(search(a,key));
    }
}
