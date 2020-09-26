package web.week4;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private final int n;
    private int blankRow, blankCol;

    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(n + "\r\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(tiles[i][j]);
                if (j < n - 1) stringBuilder.append(" ");
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int correctI = (tiles[i][j] - 1) / n;
                    int correctJ = (tiles[i][j] - 1) % n;
                    manhattan += Math.abs(correctI - i) + Math.abs(correctJ - j);
                }
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.n != this.n) return false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        if (blankRow - 1 >= 0) {
            neighbors.enqueue(new Board(swap(blankRow, blankCol, blankRow - 1, blankCol)));
        }
        if (blankRow + 1 < n) {
            neighbors.enqueue(new Board(swap(blankRow, blankCol, blankRow + 1, blankCol)));
        }
        if (blankCol - 1 >= 0) {
            neighbors.enqueue(new Board(swap(blankRow, blankCol, blankRow, blankCol - 1)));
        }
        if (blankCol + 1 < n) {
            neighbors.enqueue(new Board(swap(blankRow, blankCol, blankRow, blankCol + 1)));
        }
        return neighbors;
    }

    public Board twin() {
        if (blankRow - 1 >= 0 && blankCol - 1 >= 0) {
            return new Board(swap(blankRow - 1, blankCol - 1, blankRow - 1, blankCol));
        }
        if (blankRow - 1 >= 0 && blankCol + 1 < n) {
            return new Board(swap(blankRow - 1, blankCol + 1, blankRow - 1, blankCol));
        }
        if (blankRow + 1 < n && blankCol + 1 < n) {
            return new Board(swap(blankRow + 1, blankCol + 1, blankRow + 1, blankCol));
        }
        if (blankRow + 1 < n && blankCol - 1 >= 0) {
            return new Board(swap(blankRow + 1, blankCol - 1, blankRow + 1, blankCol));
        }
        return null;
    }

    /**
     * swap the tile at (r1, c1) with the tile at (r2, c2)
     *
     * @param r1 the row number of tile1
     * @param c1 the col number of tile1
     * @param r2 the row number of tile2
     * @param c2 the col number of tile2
     * @return
     */
    private int[][] swap(int r1, int c1, int r2, int c2) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        int tmp = copy[r1][c1];
        copy[r1][c1] = copy[r2][c2];
        copy[r2][c2] = tmp;
        return copy;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = i * n + j + 1;
            }
        }
        tiles[n - 1][n - 1] = 0;
        // random shuffling
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int p = i + (int) (Math.random() * (n - i));
                int q = j + (int) (Math.random() * (n - j));
                int tmp = tiles[i][j];
                tiles[i][j] = tiles[p][q];
                tiles[p][q] = tmp;
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board);
        StdOut.println("Dimension: " + board.dimension());
        StdOut.println("Is Goal: " + board.isGoal());
        StdOut.println("Hamming: " + board.hamming());
        StdOut.println("Manhattan: " + board.manhattan());
        StdOut.println("Neighbors...");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
        StdOut.println("Twin: " + board.twin());
    }
}
