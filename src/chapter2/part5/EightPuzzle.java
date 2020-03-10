package chapter2.part5;

import chapter1.part3.Queue;
import chapter1.part3.Stack;
import chapter2.part4.MinPQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Ex2.5.32
 */
public class EightPuzzle {
    private static class Board implements Comparable<Board> {
        int[][] grid; //we assume the blank square has value 0, which makes sense because an uninitialized element has value 0
        int rows, cols, blankRow, blankCol;
        int movesMade, wrongCount;
        Board prev;

        public Board(int[][] grid, int movesMade, Board prev) {
            this.prev = prev;
            this.movesMade = movesMade;
            rows = grid.length;
            cols = grid[0].length;
            this.grid = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    this.grid[i][j] = grid[i][j];
                    if (this.grid[i][j] == 0) {
                        // this is the blank space. We want to record the position of it
                        blankRow = i;
                        blankCol = j;
                    }
                    else if (this.grid[i][j] != rows * i + j + 1) {
                        // if this.grid[i][j] is not in its correct position
                        wrongCount++;
                    }
                }
            }
        }

        public boolean isFinal() {
            return wrongCount == 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Board)) return false;
            Board that = (Board) obj;
            if ((this.rows != that.rows) || (this.cols != that.cols)) return false;
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    if (this.grid[i][j] != that.grid[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        public int compareTo(Board that) {
            return (this.movesMade + this.wrongCount) - (that.movesMade + that.wrongCount);
        }

        public List<Board> nextBoards() {
            List<Board> nextBoards = new ArrayList<>();
            if (blankRow == 0) {
                addBoard(nextBoards, 1, 0);
            } else if (blankRow == rows - 1){
                // move up only
                addBoard(nextBoards, -1, 0);
            } else {
                // able to move up and down
                addBoard(nextBoards, 1, 0);
                addBoard(nextBoards, -1, 0);
            }
            if (blankCol == 0) {
                //move right only
                addBoard(nextBoards, 0, 1);
            } else if (blankCol == cols - 1) {
                // move left only
                addBoard(nextBoards, 0, -1);
            } else {
                // able to move left and right
                addBoard(nextBoards, 0, 1);
                addBoard(nextBoards, 0, -1);
            }
            return nextBoards;
        }

        public void addBoard(List<Board> nextBoards, int di, int dj) {
            // instead of moving other pieces, we imagine moving the blank piece in space of another piece
            Board nextBoard = new Board(grid, movesMade + 1, this);
            int tmp = nextBoard.grid[blankRow + di][blankCol + dj];
            nextBoard.grid[blankRow + di][blankCol + dj] = 0;
            nextBoard.grid[blankRow][blankCol] = tmp;
            if (!nextBoard.equals(prev)) {
                if (tmp == rows * blankRow + blankCol + 1) {
                    nextBoard.wrongCount--;
                } else if (tmp == rows * (blankRow + di) + blankCol + dj + 1) {
                    // tmp being at the previous position is correct, and moving it away increase wrongCount
                    nextBoard.wrongCount++;
                }
                nextBoard.blankRow = blankRow + di;
                nextBoard.blankCol = blankCol + dj;
                nextBoards.add(nextBoard);
            }
        }

        public void show() {
            System.out.println("--------------------------------");
            System.out.println("Moves made: " + movesMade);
            System.out.println("Wrong count: " + wrongCount);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void solve(int[][] start) {
        MinPQ<Board> pq = new MinPQ<>();
        Board initial = new Board(start, 0, null);
        pq.insert(initial);
        initial.show();
        while (!pq.isEmpty()) {
            Board curBoard = pq.delMin();
            if (curBoard.wrongCount == 0) {
                System.out.println("Problem solved");
                System.out.println("Total moves made: " + curBoard.movesMade);
                System.out.println("List of moves (start to end): ");
                Stack<Board> path = new Stack<>();
                Board cur = curBoard;
                while (cur != null) {
                    path.push(cur);
                    cur = cur.prev;
                }
                while (!path.isEmpty()) {
                    Board b = path.pop();
                    b.show();
                }
                return;
            }
            List<Board> nextBoards = curBoard.nextBoards();
            for (Board b : nextBoards) {
                pq.insert(b);
            }
        }
    }

    private static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            int tmp = a[r];
            a[r] = a[i];
            a[i] = tmp;
        }
    }

    private static void shuffle(int[][] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            int[] tmp = a[r];
            a[r] = a[i];
            a[i] = tmp;
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[][] grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = i * N + j + 1;
            }
        }
        grid[N-1][N-1] = 0;
        for (int i = 0; i < N; i++) {
            // shuffle within each row
            shuffle(grid[i]);
        }
        // shuffle the rows
        shuffle(grid);
        solve(grid);
    }
}
