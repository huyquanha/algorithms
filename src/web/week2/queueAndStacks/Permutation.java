import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            randomizedQueue.enqueue(next);
        }
        for (int i = 0; i < k; i++) {
            String random = randomizedQueue.dequeue();
            System.out.println(random);
        }
    }
}
