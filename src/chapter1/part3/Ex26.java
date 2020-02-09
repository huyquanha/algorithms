package chapter1.part3;

public class Ex26 {
    private class Node {
        String item;
        Node next;
    }

    public static Node remove(Node first, String key) {
        Node cur = first;
        Node prev = null;
        while (cur != null) {
            if (cur.item.equals(key)) {
                if (prev == null) {
                    first = first.next; //remove the old first
                }
                else {
                    prev.next = cur.next;
                }
            }
            else {
                prev = cur;
            }
            cur = cur.next;
        }
        return first;
    }
}
