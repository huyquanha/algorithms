package chapter2.part3;

public class QuickBestCase {
    public static int[] generate(int N) {
        int[] a = new int[N];
        for (int i = 1; i <= N; i++) {
            a[i-1] = i;
        }
        rearrange(a, 0, N - 1, -1, -1);
        return a;
    }

    private static void rearrange(int[] a, int lo, int hi, int prevMid, int tmp) {
        if (hi - lo < 2) {
            if (prevMid != -1) {
                a[prevMid] = tmp;
            }
            return;
        }
        int mid = lo + (hi-lo)/2;
        if (tmp == -1) {
            tmp = a[lo];
            a[lo] = a[mid];
        }
        if (prevMid != -1) {
            a[prevMid] = a[mid];
        }
        rearrange(a, lo, mid - 1, mid, tmp);
        rearrange(a, mid + 1, hi, -1, -1);
    }

    public static void main(String[] args) {
        int N = 1;
        int[] a = generate(N);
        for (int i = 0; i < N; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
