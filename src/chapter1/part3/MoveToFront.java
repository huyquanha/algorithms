package chapter1.part3;

import edu.princeton.cs.algs4.StdIn;

public class MoveToFront {
    private class Node {
        char c;
        Node next;
    }

    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public void add(char c) {
        if (isEmpty()) {
            first = new Node();
            first.c = c;
        }
        else {
            Node cur = first;
            Node prev = null;
            while (cur != null) {
                if (cur.c != c) {
                    prev = cur;
                    cur = cur.next;
                }
                else {
                    if (prev != null) {
                        prev.next = cur.next;
                    }
                    else {
                        first = first.next;
                    }
                    break;
                }
            }
            Node oldFirst = first;
            first = new Node();
            first.c = c;
            first.next = oldFirst;
        }
    }

    public void print() {
        if (isEmpty()) {
            return;
        }
        Node cur = first;
        while (cur != null) {
            System.out.print(cur.c + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MoveToFront mtf = new MoveToFront();
        while (!StdIn.isEmpty()) {
            char c = StdIn.readChar();
            mtf.add(c);
            mtf.print();
        }
    }
}
