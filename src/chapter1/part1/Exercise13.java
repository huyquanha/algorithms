package chapter1.part1;

public class Exercise13 {
    public static int[][] transpose(int[][] matrix, int rows, int cols) {
        int newRows = cols;
        int newCols = rows;
        int[][] newMatrix = new int[newRows][newCols];
        for (int i=0; i< newRows; i++) {
            for (int j=0; j< newCols; j++) {
                newMatrix[i][j] = matrix[j][i];
            }
        }
        return newMatrix;
    }

    public static void print(int[][] matrix, int rows, int cols) {
        for (int i = 0; i< rows; i++) {
            for (int j= 0; j< cols; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int[][] matrix = new int[M][N];
        for (int i=0; i< M; i++) {
            for (int j=0; j< N; j++) {
                matrix[i][j] = (int) (Math.random()*M*N);
            }
        }
        print(matrix,M,N);
        int[][] transposed = transpose(matrix, M, N);
        print(transposed, N, M);
    }
}
