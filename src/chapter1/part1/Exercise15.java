package chapter1.part1;

public class Exercise15 {
    public static int[] histogram(int[] a, int M) {
        int[] result = new int[M];
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= 0 && a[i] < M) {
                result[a[i]]++;
            }
        }
        return result;
    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        int length = Integer.parseInt(args[1]);
        int[] a = new int[length];
        for (int i = 0; i < length; i++) {
            a[i] = (int) (Math.random() * M);
        }
        print(a);
        int[] histogram = histogram(a, M);
        print(histogram);
    }
}
