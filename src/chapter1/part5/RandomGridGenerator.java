package chapter1.part5;

import chapter1.part3.RandomBag;
import edu.princeton.cs.algs4.StdRandom;

public class RandomGridGenerator {
    private static class Connection {
        int p;
        int q;

        public Connection(int p, int q) {
            this.p = p;
            this.q = q;
        }
    }

    public static Connection[] generate(int N) {
        RandomBag<Connection> bag = new RandomBag<>();
        for (int i = 0; i < N; i++) {
            for (int j = i +1; j < N; j++) {
                boolean reverse = StdRandom.uniform() < 0.5 ? false : true;
                Connection con = reverse ? new Connection(j, i) : new Connection(i, j);
                bag.add(con);
            }
        }
        Connection[] connections = new Connection[bag.size()];
        int idx = 0;
        for (Connection con : bag) {
            connections[idx++] = con;
        }
        return connections;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Connection[] cons = generate(N);
        for (Connection con : cons) {
            System.out.println(con.p + " " + con.q);
        }
    }
}
