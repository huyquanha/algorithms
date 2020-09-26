package web.week1.algoAnalysis;

/**
 * Web exercise
 */
public class Quadratic3Sum {
    /**
     * the assumption is all elements in a[] are different
     * @param a
     * @return
     */
    public static int count(int[] a) {
        sort(a);
        int N = a.length;
        // print out the sorted array
        System.out.print("Sorted array: ");
        for (int i = 0; i < N; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
        int count = 0;
        for (int i = 0; i < N; i++) {
            int j = i + 1;
            int k = N - 1;
            while (j < k) {
                if (a[i] + a[j] + a[k] == 0) {
                    System.out.println(i + "," + j + "," + k);
                    count++;
                    j++;
                } else if (a[i] + a[j] + a[k] > 0) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return count;
    }

    private static void sort(int[] a) {
        // shuffle to avoid worst case scneario
        shufle(a);
        // we use quicksort here because there's no need for stability
        sort(a, 0, a.length - 1);
    }

    private static void shufle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            int tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        // do 3 way partition first
        int v = a[lo];
        int lt = lo, gt = hi;
        int i = lo + 1;
        /**
         * Remember [lo, lt - 1] < v, [lt, gt] = v, and [gt + 1, hi] > v
         */
        while (i <= gt) {
            if (a[i] < v) {
                exch(a, i++, lt++);
            } else if (a[i] > v) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void exch(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = (int) (Math.random() * N);
            a[i] = Math.random() >= 0.5 ? a[i] : -a[i];
            System.out.print(a[i] + " ");
        }
        System.out.println();
        int count = count(a);
        System.out.println("Number of 3-tuple that sums to 0 is: " + count);
    }
}
