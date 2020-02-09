package chapter1.part4;

import java.util.Arrays;

//Ex 1.4.15
public class ThreeSumFaster {
    public static int count(int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int count = 0;
        for (int i = 0; i < N-2; i++) {
            int l = i + 1;
            int r = N - 1;
            int sum;
            while (l < r) {
                sum = a[l] + a[r];
                if (sum + a[i] > 0) {
                    r--;
                } else if (sum + a[i] < 0) {
                    l++;
                } else {
                    //sum + a[i] == 0
                    count++;
                    int m = l +1;
                    while (m < r && a[m] == a[l]) {
                        count++;
                        m++;
                    }
                    int n = r -1;
                    while (n > l && a[n] == a[r]) {
                        count++;
                        n--;
                    }
                    l++;
                    r--;
                }
            }
        }
        return count;
    }
}
