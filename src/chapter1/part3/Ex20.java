package chapter1.part3;

public class Ex20 {
    private class Node {
        String item;
        Node next;
    }

    //return linked list after removal
    public static Node delete (int k, Node first) {
        if (first == null) {
            return null;
        }
        Node cur = first;
        Node prev = null;
        int index = 1;
        while (index < k && cur.next != null) {
            prev = cur;
            cur = cur.next;
            index++;
        }
        if (index==k) {
            if (prev==null) {
                first = null;
            }
            else {
                prev.next = cur.next;
            }
        }
        return first;
    }
}
