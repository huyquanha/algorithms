package chapter1.part4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Ex 1.4.19
/*
 * Idea: starting from any element, if it is the local minimum, we return. Otherwise
 * we roll to a smaller neighbor. This process is guaranteed to finish because we only
 * have a fixed amount of numbers.
 * First we find the smallest number among all elements in middle row, col and the boundary
 * If that element is a local minimum we return
 * Otherwise, we roll to one of the four quadrants created by middle row crossing middle col.
 * Every step we halve N => time is ~ N + N/2 + N/4 + ... + 1 ~ (2N -1) ~ N
 */
public class LocalMinimumMatrix {
    private class Square {
        int r, c, val;
    }

    public void print(int[][] a, int N) {
        for (int i=0; i< N; i++) {
            for (int j = 0; j<N; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }

    public Square forMatrix(int[][] a, int N) {
        return forMatrix(a, 0, N - 1, 0, N - 1);
    }

    private Square forMatrix(int[][] a, int rs, int re, int cs, int ce) {
        int midRow = rs + (re - rs) / 2;
        int midCol = cs + (ce - cs) / 2;
        Square minMidRow = findMinRow(a, midRow, cs, ce);
        Square minMidCol = findMinCol(a, midCol, rs, re);
        Square minTop = findMinRow(a, rs, cs, ce);
        Square minBot = findMinRow(a, re, cs, ce);
        Square minLeft = findMinCol(a, cs, rs, re);
        Square minRight = findMinCol(a, ce, rs, re);
        Square min = findMin(minMidRow, minMidCol, minTop, minBot, minLeft, minRight);
        if (checkLocalMin(a, min)) {
            //eventually it will stop here
            return min;
        } else {
            if (min.c == midCol) {
                if (min.r < midRow) {
                    //top
                    if (a[min.r][min.c - 1] < a[min.r][min.c + 1]) {
                        //left
                        return forMatrix(a, rs, midRow - 1, cs, midCol - 1);
                    } else {
                        //right
                        return forMatrix(a, rs, midRow - 1, midCol + 1, ce);
                    }
                } else {
                    //bottom
                    if (a[min.r][min.c - 1] < a[min.r][min.c + 1]) {
                        //left
                        return forMatrix(a, midRow + 1, re, cs, midCol - 1);
                    } else {
                        //right
                        return forMatrix(a, midRow + 1, re, midCol + 1, ce);
                    }
                }
            } else if (min.r == midRow) {
                if (min.c < midCol) {
                    //left
                    if (a[min.r - 1][min.c] < a[min.r + 1][min.c]) {
                        //top
                        return forMatrix(a, rs, midRow - 1, cs, midCol - 1);
                    } else {
                        //bottom
                        return forMatrix(a, midRow + 1, re, cs, midCol - 1);
                    }
                } else {
                    //right
                    if (a[min.r - 1][min.c] < a[min.r + 1][min.c]) {
                        //top
                        return forMatrix(a, rs, midRow - 1, midCol + 1, ce);
                    } else {
                        //bottom
                        return forMatrix(a, midRow + 1, re, midCol + 1, ce);
                    }
                }
            }
            else {
                if (min.r < midRow && min.c < midCol) {
                    //top & left
                    return forMatrix(a, rs, midRow -1, cs, midCol -1);
                }
                else if (min.r < midRow && min.c > midCol) {
                    //top & right
                    return forMatrix(a, rs, midRow -1, midCol + 1, ce);
                }
                else if (min.r > midRow && min.c < midCol) {
                    //bottom & left
                    return forMatrix(a, midRow + 1, re, cs, midCol -1);
                }
                else {
                    //bottom & right
                    return forMatrix(a, midRow + 1, re, midCol + 1, ce);
                }
            }
        }
    }

    private boolean checkLocalMin(int[][] a, Square s) {
        int r = s.r;
        int c = s.c;
        int val = s.val;
        if (r - 1 >= 0 && a[r - 1][c] < val) {
            return false;
        }
        if (r + 1 < a.length && a[r + 1][c] < val) {
            return false;
        }
        if (c - 1 >= 0 && a[r][c - 1] < val) {
            return false;
        }
        if (c + 1 < a[0].length && a[r][c + 1] < val) {
            return false;
        }
        return true;
    }

    private Square findMin(Square minMidRow, Square minMidCol, Square minTop,
                           Square minBot, Square minLeft, Square minRight) {
        Square min = new Square();
        min.val = Integer.MAX_VALUE;
        if (minMidRow.val < min.val) {
            min = minMidRow;
        }
        if (minMidCol.val < min.val) {
            min = minMidCol;
        }
        if (minTop.val < min.val) {
            min = minTop;
        }
        if (minBot.val < min.val) {
            min = minBot;
        }
        if (minLeft.val < min.val) {
            min = minLeft;
        }
        if (minRight.val < min.val) {
            min = minRight;
        }
        return min;
    }

    private Square findMinRow(int[][] a, int row, int start, int end) {
        Square s = new Square();
        s.r = row;
        s.val = Integer.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            if (a[row][i] < s.val) {
                s.val = a[row][i];
                s.c = i;
            }
        }
        return s;
    }

    private Square findMinCol(int[][] a, int col, int start, int end) {
        Square s = new Square();
        s.c = col;
        s.val = Integer.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            if (a[i][col] < s.val) {
                s.val = a[i][col];
                s.r = i;
            }
        }
        return s;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[][] a = new int[N][N];
        List<Integer> all = new ArrayList<>();
        for (int i= 0; i< N*N; i++) {
            all.add(i);
        }
        Collections.shuffle(all);
        int index = 0;
        for (int i=0; i< N; i++) {
            for (int j = 0; j< N; j++) {
                a[i][j] = all.get(index++);
            }
        }
        LocalMinimumMatrix lm = new LocalMinimumMatrix();
        //lm.print(a, N);
        Square min = lm.forMatrix(a, N);
        System.out.println(min.r + " " + min.c + " " + min.val);
    }
}
