package chapter1.part4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatrixLocalMinimum {
    public static int[] findLocalMinimum(int[][] m) {
        int N = m.length;
        int minRow, minCol, maxRow, maxCol, midRow, midCol;
        minRow = minCol = 0;
        maxRow = maxCol = N-1;
        midRow = minRow + (maxRow - minRow) /2;
        midCol = minCol + (maxCol - minCol) /2;
        while (true) {
            int[] minPos = findMinOfMidAndBoundaries(m, minRow, minCol, maxRow, maxCol, midRow, midCol);
            //return [-1,-1] if nothing is smaller
            int[] smallerThanMinPos = findSmallerThanMinPos(m, minPos);
            if (smallerThanMinPos[0] == -1 && smallerThanMinPos[1] == -1) {
                return minPos;
            } else {
                int smallerThanMinPosRow = smallerThanMinPos[0];
                int smallerThanMinPosCol = smallerThanMinPos[1];
                if (smallerThanMinPosRow > midRow) {
                    minRow = midRow;
                } else {
                    maxRow = midRow;
                }
                if (smallerThanMinPosCol > midCol) {
                    minCol = midCol;
                } else {
                    maxCol = midCol;
                }
            }
        }
    }

    private static int[] findMinOfMidAndBoundaries(int[][] m, int minR, int minC, int maxR, int maxC, int midR, int midC) {
        int [] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        int smallest = Integer.MAX_VALUE;
        for (int i = minC; i <= maxC; i++) {
            if (m[minR][i] < smallest) {
                result[0] = minR;
                result[1] = i;
                smallest = m[minR][i];
            }
            if (m[midR][i] < smallest) {
                result[0] = midR;
                result[1] = i;
                smallest = m[midR][i];
            }
            if (m[maxR][i] < smallest) {
                result[0] = maxR;
                result[1] = i;
                smallest = m[maxR][i];
            }
        }
        for (int i = minR; i <= maxR; i++) {
            if (m[i][minC] < smallest) {
                result[0] = i;
                result[1] = minC;
                smallest = m[i][minC];
            }
            if (m[i][midC] < smallest) {
                result[0] = i;
                result[1] = midC;
                smallest = m[i][midC];
            }
            if (m[i][maxC] < smallest) {
                result[0] = i;
                result[1] = maxC;
                smallest = m[i][maxC];
            }
        }
        return result;
    }

    private static int[] findSmallerThanMinPos(int[][] m, int[] pos) {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        int r = pos[0];
        int c = pos[1];
        int minValue = m[r][c];
        if (r > 0 && m[r-1][c] < minValue) {
            result[0] = r-1;
            result[1] = c;
        } else if (r < m.length -1 && m[r+1][c] < minValue) {
            result[0] = r + 1;
            result[1] = c;
        } else if (c > 0 && m[r][c-1] < minValue) {
            result[0] = r;
            result[1] = c-1;
        } else if (c < m[0].length - 1 && m[r][c+1] < minValue) {
            result[0] = r;
            result[1] = c+1;
        }
        return result;
    }

    private static void print(int[][] m, int N) {
        for (int i = 0; i< N; i++) {
            for (int j = 0; j<N; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[][] matrix = new int[N][N];
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i< N*N; i++) {
            all.add(i);
        }
        Collections.shuffle(all);
        int index = 0;
        for (int i=0; i< N; i++) {
            for (int j = 0; j< N; j++) {
                matrix[i][j] = all.get(index++);
            }
        }
        //print(matrix, N);
        int[] localMin = findLocalMinimum(matrix);
        int row = localMin[0];
        int col = localMin[1];
        System.out.println("Min Row: " + row);
        System.out.println("Min Col: " + col);
        System.out.println("Value: " + matrix[row][col]);
        if (row > 0) {
            System.out.println(matrix[row-1][col]);
        }
        if (row < N -1) {
            System.out.println(matrix[row+1][col]);
        }
        if (col > 0) {
            System.out.println(matrix[row][col-1]);
        }
        if (col < N -1) {
            System.out.println(matrix[row][col+1]);
        }
    }
}
