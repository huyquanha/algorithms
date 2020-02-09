package chapter1.part3;

public class Ex21 {
    private class Node {
        String item;
        Node next;
    }

    public static boolean find(Node first, String key) {
        Node cur = first;
        boolean found = false;
        while (cur != null && !found) {
            if (cur.item.equals(key)) {
                found = true;
            }
            else {
                cur = cur.next;
            }
        }
        return found;
    }
}
