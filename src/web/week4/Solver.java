package web.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static class Node implements Comparable<Node> {
        Board board;
        int moves, dist;
        Node prev;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.dist = board.manhattan();
        }

        public int compareTo(Node that) {
            return (this.moves + this.dist) - (that.moves + that.dist);
        }
    }

    private final Node finalState;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        Board twin = initial.twin();
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();
        minPQ.insert(new Node(initial, 0, null));
        twinPQ.insert(new Node(twin, 0, null));
        Node result = null;
        while (!minPQ.isEmpty() && !twinPQ.isEmpty()) {
            result = aStar(minPQ);
            if (result != null) {
                break;
            }
            result = aStar(twinPQ);
            if (result != null) {
                result = null;
                break;
            }
        }
        this.finalState = result;
    }

    private Node aStar(MinPQ<Node> minPQ) {
        Node cur = minPQ.delMin();
        Board board = cur.board;
        int moves = cur.moves;
        if (board.isGoal()) {
            return cur;
        }
        for (Board neighbor : board.neighbors()) {
            if (cur.prev == null || !neighbor.equals(cur.prev.board)) {
                minPQ.insert(new Node(neighbor, moves + 1, cur));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return this.finalState != null;
    }

    public int moves() {
        if (!isSolvable()) return -1;
        return this.finalState.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<>();
        Node cur = this.finalState;
        while (cur != null) {
            solution.push(cur.board);
            cur = cur.prev;
        }
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
