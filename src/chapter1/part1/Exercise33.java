package chapter1.part1;

public class Exercise33 {
    //Matrix
    public static double dot(double[] x, double[] y) {
        assert (x.length == y.length);
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result += x[i] * y[i];
        }
        return result;
    }

    public static double[][] mult(double[][] a, double[][] b) {
        int m = a.length; //row of a
        int n = a[0].length; //col of a
        assert (n == b.length); //col of a has to be equal to row of b
        int p = b[0].length;
        double[][] result = new double[m][p];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += a[i][k] * a[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] transpose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = a[j][i];
            }
        }
        return result;
    }

    public static double[][] mult(double[][] a, double[] x) {
        //a: m rows x n cols, x has to be nx1
        double[][] xMatrix = new double[x.length][1];
        for (int i = 0; i < x.length; i++) {
            xMatrix[i][1] = x[i];
        }
        return mult(a, xMatrix);
    }

    public static double[][] mult(double[] y, double[][] a) {
        double[][] yMatrix = new double[y.length][1];
        for (int i = 0; i < y.length; i++) {
            yMatrix[i][1] = y[i];
        }
        return mult(yMatrix, a);
    }
}
