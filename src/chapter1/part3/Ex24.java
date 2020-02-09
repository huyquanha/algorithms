package chapter1.part3;

public class Ex24 {
    private class Node {
        String item;
        Node next;
    }

    public static Node removeAfter(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        node.next = node.next.next;
        return node;
    }
}
