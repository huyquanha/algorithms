package chapter1.part3;

public class Ex19 {
    private class Node {
        String item;
        Node next;
    }

    //return the linked list after removal
    public static Node removeLast(Node first) {
        if (first == null) {
            return null;
        }
        if (first.next == null) {
            first = null;
            return first;
        }
        Node cur = first;
        while (cur.next.next != null) {
            cur = cur.next;
        }
        cur.next = null;
        return first;
    }
}
