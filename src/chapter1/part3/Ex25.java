package chapter1.part3;

public class Ex25 {
    private class Node {
        String item;
        Node next;
    }

    public static Node insertAfter (Node before, Node after) {
        if (before == null || after == null) {
            return before;
        }
        after.next = before.next;
        before.next = after;
        return before;
    }
}
