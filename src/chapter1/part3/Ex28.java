package chapter1.part3;

public class Ex28 {
    private class Node {
        int item;
        Node next;
    }

    public static int recursMax(Node first) {
        if (first == null) {
            return 0;
        }
        int maxOfRest = recursMax(first.next);
        if (maxOfRest > first.item) {
            return maxOfRest;
        }
        else {
            return first.item;
        }
    }

}
