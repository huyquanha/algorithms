package chapter1.part3;

public class Ex30 {
    private class Node {
        String item;
        Node next;
    }

    public static Node iterativeReverse(Node first) {
        Node prev = null;
        Node cur = first;
        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    //Ex31
    public static Node recursiveReverse(Node first) {
        if (first == null) {
            return null;
        }
        if (first.next == null) {
            return first;
        }
        Node next = first.next;
        first.next = null;
        Node reverse = recursiveReverse(next);
        next.next = first;
        return reverse;
    }

    public Node createLinkedList() {
        Node a = new Node();
        a.item = "a";
        Node b = new Node();
        b.item = "b";
        a.next = b;
        Node c = new Node();
        c.item = "c";
        b.next = c;
        Node d = new Node();
        d.item = "d";
        c.next = d;
        return a;
    }

    public static void main(String[] args) {
        Ex30 ex = new Ex30();
        Node first = ex.createLinkedList();
        Node newFirst = recursiveReverse(first);
        Node cur = newFirst;
        while (cur != null) {
            System.out.println(cur.item);
            cur = cur.next;
        }
    }
}
