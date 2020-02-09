package chapter1.part1;

public class Exercise37 {
    public static void badShuffle(int[] a, int M) {
        for (int i = 0; i < M; i++) {
            int r = (int) (Math.random() * M);
            int tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
    }

    private static void print(int[][] table, int M) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int[] a = new int[M];
        int[][] table = new int[M][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                a[j] = j;
            }
            badShuffle(a, M);
            for (int k = 0; k < M; k++) {
                table[a[k]][k]++;
            }
        }
        print(table, M);
    }
}
