package chapter1.part4;

import java.util.Arrays;

//Ex 1.4.15
public class TwoSumFaster {
    public static int count(int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int count = 0;
        int left = 0;
        int right = N - 1;
        int sum;
        while (left < right) {
            sum = a[left] + a[right];
            if (sum > 0) {
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                //a[left] + a[right] == 0
                count++;
                //since a[left+1], a[left+2]... can == a[left], we do this
                int i = left+1;
                while (i < right && a[i] == a[left]) {
                    count++;
                    i++;
                }
                //since a[right-1], a[right-2]... can == a[right], we do this
                int j = right-1;
                while (j > left && a[j] == a[right]) {
                    count++;
                    j--;
                }
                left++;
                right--;
            }
            if (sum == 0) {
                count++;
                left++;
                right--;
            } else if (sum > 0) {
                right--;
            } else {
                left++;
            }
        }
        return count;
    }
}
