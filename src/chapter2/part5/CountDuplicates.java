package chapter2.part5;

/**
 * Ex2.5.31
 */
public class CountDuplicates {
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int T = Integer.parseInt(args[2]);
        for (int i = 0; i < T; i ++) {
            int[] a = new int[N];
            for (int j = 0; j < N; j++) {
                a[j] = (int) (Math.random() * M); // between 0 and M - 1
            }
            int duplicates = countDuplicates(a, N);
            double alpha = N/M;
            System.out.println(alpha);
            int estimate = (int) (N * (1 - Math.exp(-alpha)));
            System.out.println("Actual number of duplicates: " + duplicates);
            System.out.println("Estimated number of duplicates: " + estimate);
            System.out.println("-----------------------------------");
        }
    }

    private static int countDuplicates(int[] a, int N) {
        sort(a, 0, N -1);
        int count = 0;
        for (int i = 1; i < N; i++) {
            if (a[i] == a[i-1]) {
                count++;
            }
        }
        return count;
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo, gt = hi, i = lo + 1;
        int v = a[lo];
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
}
