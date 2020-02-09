package chapter1.part4;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourSum {
    public static int count(int[] a) {
        Map<Integer, Map<Integer, List<Integer>>> sumMap = new HashMap<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        int N = a.length;
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int nextSum = a[i] + a[j];
                if (sumMap.containsKey(-nextSum)) {
                    Map<Integer, List<Integer>> innerMap = sumMap.get(-nextSum);
                    int total = countMap.get(-nextSum);
                    if (innerMap.containsKey(i)) {
                        total -= innerMap.get(i).size();
                    }
                    if (innerMap.containsKey(j)) {
                        total -= innerMap.get(j).size();
                    }
                    count += total;
                }
                if (sumMap.containsKey(nextSum)) {
                    Map<Integer, List<Integer>> innerMap = sumMap.get(nextSum);
                    if (innerMap.containsKey(i)) {
                        innerMap.get(i).add(j);
                    }
                    else {
                        innerMap.put(i, new ArrayList<>());
                        innerMap.get(i).add(j);
                    }
                    if (innerMap.containsKey(j)) {
                        innerMap.get(j).add(i);
                    }
                    else {
                        innerMap.put(j, new ArrayList<>());
                        innerMap.get(j).add(i);
                    }
                    countMap.put(nextSum, countMap.get(nextSum)+1);
                } else {
                    Map<Integer, List<Integer>> innerMap = new HashMap<>();
                    innerMap.put(i, new ArrayList<>());
                    innerMap.get(i).add(j);
                    innerMap.put(j, new ArrayList<>());
                    innerMap.get(j).add(i);
                    sumMap.put(nextSum, innerMap);
                    countMap.put(nextSum, 1);
                }
            }
        }
        return count;
    }

    private static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        /*int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform(-max, max);
        }*/
        //int[] a = {-7, 4, 8, -5, -10, 3, 9, -7, -1, -6}; //33
        //(-4,3)(-4,4),(-3,3),(-3,4), (-4,-3), (3,4) => 6 different sets of 2.
        int[] a = {-4,-3,-2,2,3,4};
        print(a);
        System.out.println(count(a));
    }
}
