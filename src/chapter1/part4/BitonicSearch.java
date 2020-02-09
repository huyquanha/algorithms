package chapter1.part4;

public class BitonicSearch {
    public static boolean search(int[] a, int N, int key) {
        //first search for the bitonic item (the largest item in the array)
        int lo = 0;
        int hi = N-1;
        int bitonicIndex = -1;
        while (lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if (mid < N -1 && a[mid] < a[mid + 1]) {
                lo = mid + 1;
            } else if (mid > 0 && a[mid] < a[mid -1]) {
                hi = mid -1;
            } else {
                bitonicIndex = mid;
                break;
            }
        }
        if (key == a[bitonicIndex]) {
            return true;
        }
        if (key > a[bitonicIndex]) {
            return false;
        }
        return binarySearch(a, 0, bitonicIndex -1, key, true)  != -1 ||
                binarySearch(a, bitonicIndex + 1, N-1, key, false) != -1;
    }

    private static int binarySearch(int[] a, int lo, int hi, int key, boolean increase) {
        while (lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if (key < a[mid]) {
                if (increase) {
                    hi = mid -1;
                } else {
                    lo = mid + 1;
                }
            } else if (key > a[mid]) {
                if (increase) {
                    lo = mid + 1;
                } else {
                    hi = mid -1;
                }
            } else {
                return mid;
            }
        }
        return -1;
    }
}
