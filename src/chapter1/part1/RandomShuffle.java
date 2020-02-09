package chapter1.part1;

/**
 * Ex1.1.36
 */
public class RandomShuffle {
    public static void shuffle(int[] a, int M) {
        for (int i = 0; i < M; i++) {
            int r = i + (int) (Math.random() * (M - i));
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
            shuffle(a, M);
            for (int k = 0; k < M; k++) {
                table[a[k]][k]++;
            }
        }
        print(table, M);
    }
}
