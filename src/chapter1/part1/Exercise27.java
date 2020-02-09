package chapter1.part1;

public class Exercise27 {
    private static double p = 0.4;
    private static double a[][];

    public static double binomial(int N, int k)
    {
        if ((N == 0) || (k < 0)) return 1.0;
        if (k==0) {
            return (1.0 - p)*a[N-1][k] + p*binomial(N-1,k-1);
        }
        else {
            return (1.0 - p)*a[N-1][k] + p*a[N-1][k-1];
        }
    }

    public static double oldBinomial(int N, int k) {
        if ((N == 0) || (k < 0)) return 1.0;
        return (1.0 - p)*oldBinomial(N-1, k) + p*oldBinomial(N-1, k-1);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        a = new double[N][k+1];
        for (int i=0; i< N; i++) {
            for (int j=0; j< k+1; j++) {
                a[i][j]  = binomial(i,j);
            }
        }
        System.out.println(binomial(N,k));
        System.out.println(oldBinomial(N,k));
    }
}
