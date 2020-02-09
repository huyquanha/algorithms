package chapter1.part4;

public class LocalMinimumArray {
    public static int findLocalMin(int[] a, int N) {
        if (N == 0) {
            return -1;
        }
        int lo = 0;
        int hi = N -1;
        while (true) { //an array of distinct ints will always have a local minimum
            int mid = lo + (hi-lo)/2;
            if ((mid == 0 || a[mid] < a[mid-1]) && (mid==N-1 || a[mid] < a[mid+1])) {
                return mid;
            } else if (mid > 0 && a[mid] > a[mid-1]) {
                hi = mid -1;
            } else {
                lo = mid + 1;
            }
        }
    }
}
