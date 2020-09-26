package chapter3.part4;

import java.util.ArrayList;
import java.util.List;

public class MaxProduct {
    public static void main(String[] args) {
        int[] arr = {-10, -2, -3, 1, 20, 30};
        int[] result = find3MaxProduct(arr);
        if (result == null) System.out.println("NULL");
        else {
            for (int i : result) {
                System.out.println(i);
            }
        }
    }

    public static int[] find3MaxProduct(int[] arr) {
        if (arr.length < 3) {
            return null;
        }
        if (arr.length == 3) {
            return arr;
        }
        // arr.length > 3
        List<Integer> nonNeg = new ArrayList<>();
        List<Integer> neg = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                nonNeg.add(arr[i]);
            } else {
                neg.add(arr[i]);
            }
        }
        if (nonNeg.size() == 0) {
            // no 0 or positive elements, but arr.length > 3 => we must have more than 3 negative elements
            return findLargest3(neg);
        } else {
            // there's at least 1 non-negative element
            if (neg.size() < 2) {
                // because arr's size > 3, this means there must be at least 3 non-negative values, find the largest 3
                return findLargest3(nonNeg);
            } else if (nonNeg.size() < 3) {
                // we have at least 2 negative elements and at least 1 non-negative elements (but < 3)
                return get2Neg1Pos(neg, nonNeg);
            } else {
                // we have at least 2 negative elements and at least 3 positive elements => compare
                int[] twoNeg1Pos = get2Neg1Pos(neg, nonNeg);
                int[] threePos = findLargest3(nonNeg);
                long product1 = twoNeg1Pos[0] * twoNeg1Pos[1] * twoNeg1Pos[2];
                long product2 = threePos[0] * threePos[1] * threePos[2];
                if (product1 > product2) {
                    return twoNeg1Pos;
                } else {
                    return threePos;
                }
            }
        }
    }

    public static int[] get2Neg1Pos(List<Integer> neg, List<Integer> nonNeg) {
        int[] smallestNegs = findSmallest2(neg);
        int largestPos = findLargest(nonNeg);
        return new int[]{largestPos, smallestNegs[0], smallestNegs[1]};
    }

    public static int findLargest(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        for (int i : list) {
            if (i > max) max = i;
        }
        return max;
    }

    public static int[] findSmallest2(List<Integer> list) {
        int[] group = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i : list) {
            if (i < group[0]) {
                group[1] = group[0];
                group[0] = i;
            } else if (i < group[1]) {
                group[1] = i;
            }
        }
        return group;
    }

    public static int[] findLargest3(List<Integer> list) {
        int[] group = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        for (int i : list) {
            if (i > group[0]) {
                group[2] = group[1];
                group[1] = group[0];
                group[0] = i;
            } else if (i > group[1]) {
                group[2] = group[1];
                group[1] = i;
            } else if (i > group[2]) {
                group[2] = i;
            }
        }
        return group;
    }
}
