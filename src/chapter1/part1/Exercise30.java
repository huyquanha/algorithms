package chapter1.part1;

public class Exercise30 {
    public static int gcd(int p, int q) {
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    //2 relatively prime numbers a, b when gcd(a,b)=1
    public static boolean[][] buildArray(int N) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                a[i][j] = gcd(i,j) ==1;
                a[j][i] = a[i][j];
            }
        }
        return a;
    }

    public static void print(boolean[][] a, int N) {
        for (int i=0; i<N; i++) {
            for (int j=0; j<N;j++) {
                if (a[i][j]) {
                    System.out.print("T ");
                }
                else {
                    System.out.print("F ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        boolean[][] a = buildArray(N);
        print(a,N);
    }
}
