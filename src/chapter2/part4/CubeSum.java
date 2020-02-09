package chapter2.part4;

public class CubeSum {
    private static class Node implements Comparable<Node> {
        int i,j;
        long sum;

        public int compareTo(Node other) {
            if (this.sum < other.sum) {
                return -1;
            } else if (this.sum > other.sum) {
                return 1;
            } else {
                return 0;
            }
        }

        public String toString() {
            return "(" + sum + ", " + i + ", " + j + ")";
        }
    }

    public static void printSum(int N) {
        Node[] arr = new Node[N + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Node();
            arr[i].i = i;
            arr[i].j = 0;
            arr[i].sum = (long) (Math.pow(i, 3));
        }
        MinPQ<Node> pq = new MinPQ<>(arr);
        while (!pq.isEmpty()) {
            Node min = pq.delMin();
            System.out.print(min + " ");
            if (min.j < N) {
                Node next = new Node();
                next.i = min.i;
                next.j = min.j + 1;
                next.sum = (long) (Math.pow(next.i, 3) + Math.pow(next.j, 3));
                pq.insert(next);
            }
        }
        System.out.println();
    }

    public static void findEqualSum(int N) {
        Node[] arr = new Node[N + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Node();
            arr[i].i = i;
            arr[i].j = 0;
            arr[i].sum = (long) (Math.pow(i, 3));
        }
        MinPQ<Node> pq = new MinPQ<>(arr);
        Node prevMin = null;
        while (!pq.isEmpty()) {
            Node min = pq.delMin();
            if (prevMin != null) {
                if (min.sum == prevMin.sum && distinct(min.i, min.j, prevMin.i, prevMin.j)) {
                    System.out.println(prevMin + " " + min);
                }
            }
            prevMin = min;
            if (min.j < N) {
                Node next = new Node();
                next.i = min.i;
                next.j = min.j + 1;
                next.sum = (long) (Math.pow(next.i, 3) + Math.pow(next.j, 3));
                pq.insert(next);
            }
        }
    }

    private static boolean distinct(int a, int b, int c, int d) {
        if (a == b || a == c || a == d || b == c || b == d || c == d) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        findEqualSum(N);
    }
}
