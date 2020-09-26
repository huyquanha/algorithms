package web.week1.algoAnalysis;

public class BitonicSearch {
    public static boolean search(int[] a, int k) {
        int N = a.length;
        int lo = 0, hi = N - 1;
        // first we want to find the position of the largest element (intersection between increasing sequence & decreasing sequence)
        int bitonicIndex = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (mid < N - 1 && a[mid] < a[mid + 1]) {
                // mid is to the left of bitonicIndex
                lo = mid + 1;
            } else if (mid > 0 && a[mid] < a[mid - 1]) {
                // mid is to the right of bitonic
                hi = mid - 1;
            } else {
                bitonicIndex = mid;
                break;
            }
        }
        if (k > a[bitonicIndex]) {
            return false;
        } else if (k == a[bitonicIndex]) {
            return true;
        } else {
            // binary search left half
            lo = 0;
            hi = bitonicIndex - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo)/2;
                if (k < a[mid]) {
                    hi = mid - 1;
                } else if (k > a[mid]) {
                    lo = mid + 1;
                } else {
                    return true;
                }
            }
            // binary search right half
            lo = bitonicIndex + 1;
            hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo)/2;
                if (k < a[mid]) {
                    lo = mid + 1;
                } else if (k > a[mid]) {
                    hi = mid - 1;
                } else {
                    return true;
                }
            }
            return false;
        }
    }
}
