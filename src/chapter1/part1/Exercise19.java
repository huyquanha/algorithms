package chapter1.part1;

public class Exercise19 {
    private static long[] a = new long[100];

    public static long F(int N) {
        if (N == 0) return 0;
        if (N == 1) return 1;
        return a[N - 1] + a[N - 2];
    }

    public static void main(String[] args) {
        for (int N = 0; N < 100; N++) {
            a[N] = F(N);
            System.out.println(N + " " + a[N]);
        }
    }
}
