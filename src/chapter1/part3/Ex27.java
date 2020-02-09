package chapter1.part3;

public class Ex27 {
    private class Node {
        int item;
        Node next;
    }

    public static int max(Node first) {
        if (first == null) {
            return 0;
        }
        int max = first.item;
        Node cur = first;
        while (cur.next != null) {
            cur = cur.next;
            if (cur.item > max) {
                max = cur.item;
            }
        }
        return max;
    }
}
