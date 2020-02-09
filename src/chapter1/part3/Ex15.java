package chapter1.part3;

import edu.princeton.cs.algs4.StdIn;

public class Ex15 {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        Queue<String> q = new Queue<>();
        int index = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("q")) {
                break;
            }
            if (index < k) {
                q.enqueue(s);
            }
            else {
                q.dequeue(); //this is not needed anymore
                q.enqueue(s);
            }
            index++;
        }
        System.out.println(q.dequeue());
    }
}
