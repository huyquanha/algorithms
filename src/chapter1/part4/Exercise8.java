package chapter1.part4;

import edu.princeton.cs.algs4.In;

import java.util.Arrays;

public class Exercise8 {
    public static int countEqualPairs(int[] a) {
        Arrays.sort(a);
        int count = 0;
        int i = 0;
        int j = i + 1;
        int N = a.length;
        while (i < N) {
            if (j < N && a[j] == a[i]) {
                j++;
            }
            else {
                count += (j - i) * (j - i - 1) / 2;
                i=j++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] a = In.readInts(args[0]);
        System.out.println(countEqualPairs(a));
    }
}
