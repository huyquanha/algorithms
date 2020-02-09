package chapter1.part1;

import java.util.Arrays;

public class Exercise29 {
    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else { //key == a[mid]. We check mid-1, mid-2,..to find the last element that equals key
                while (mid >= lo && key == a[mid]) {
                    mid--;
                }
                return mid + 1;
            }
        }
        return lo;
    }

    public static int count(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else { //key == a[mid]. We check mid-1, mid-2,mid+1,mid+2,..to find the number of elemenets ==key
                int count = 1;
                int left=mid-1;
                int right=mid+1;
                while (left >= lo && key == a[left--]) {
                    count++;
                }
                while (right <= hi && key == a[right++]) {
                    count++;
                }
                return count;
            }
        }
        return 0;
    }

    public static void print(int[] a, int N) {
        for (int i=0; i< N; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[] a = new int[N];
        for (int i=0; i< N; i++) {
            a[i] = (int) (Math.random()*N);
        }
        Arrays.sort(a);
        print(a,N);
        int key = (int) (Math.random()*N);
        System.out.println("Key: " + key);
        System.out.println(rank(key,a));
        System.out.println(count(key,a));
    }
}
