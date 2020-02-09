package chapter1.part3;

import java.util.Iterator;

//Exercise 1.3.37
//potential problem: M >> N, then this code is very inefficient.
public class Josephus {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);

        Queue<Integer> q = new Queue<>();
        for (int i = 0; i < N; i++) {
            q.enqueue(i);
        }
        Queue<Integer> tmp = new Queue<>();
        int index = 0;
        while (!q.isEmpty()) {
            Iterator<Integer> itr = q.iterator();
            while (itr.hasNext()) {
                Integer next = itr.next();
                index++;
                if (index != M) {
                    tmp.enqueue(next);
                } else {
                    System.out.println(next);
                    index = 0;
                }
            }
            q = tmp;
            tmp = new Queue<>();
        }
    }
}
