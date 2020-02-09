package chapter1.part3;

public class Ex31<Item> {
    private class DoubleNode {
        Item item;
        DoubleNode left, right;
    }

    public DoubleNode insertBegin(DoubleNode node, Item item) {
        DoubleNode cur = node;
        DoubleNode next = null;
        while (cur != null) {
            next = cur;
            cur = cur.left;
        }
        DoubleNode first = new DoubleNode();
        first.item = item;
        first.right = next;
        if (next != null) {
            next.left = first;
        }
        return first;
    }

    public DoubleNode insertEnd(DoubleNode node, Item item) {
        DoubleNode cur = node;
        DoubleNode prev = null;
        while (cur != null) {
            prev = cur;
            cur = cur.right;
        }
        DoubleNode last = new DoubleNode();
        last.item = item;
        last.left = prev;
        if (prev != null) {
            prev.right = last;
        }
        return last;
    }

    public Item removeBegin(DoubleNode node) {
        DoubleNode cur = node;
        DoubleNode next = null;
        while (cur != null) {
            next = cur;
            cur = cur.left;
        }
        if (next == null) {
            return null;
        } else {
            Item item = next.item;
            if (next.right != null) {
                next.right.left = null;
                next.right = null;
            }
            return item;
        }
    }

    public Item removeEnd(DoubleNode node) {
        DoubleNode cur = node;
        DoubleNode prev = null;
        while (cur != null) {
            prev = cur;
            cur = cur.right;
        }
        if (prev == null) {
            return null;
        } else {
            Item item = prev.item;
            if (prev.left != null) {
                prev.left.right = null;
                prev.left = null;
            }
            return item;
        }
    }

    public DoubleNode insertBefore(DoubleNode node, Item item) {
        if (node == null) {
            return null;
        }
        DoubleNode prev = node.left;
        DoubleNode newNode = new DoubleNode();
        newNode.item = item;
        newNode.right = node;
        node.left = newNode;
        newNode.left = prev;
        if (prev != null) {
            prev.right = newNode;
        }
        return newNode;
    }

    public DoubleNode insertAfter(DoubleNode node, Item item) {
        if (node == null) {
            return null;
        }
        DoubleNode next = node.right;
        DoubleNode newNode = new DoubleNode();
        newNode.item = item;
        newNode.left = node;
        node.right = newNode;
        newNode.right = next;
        if (next != null) {
            next.left = newNode;
        }
        return newNode;
    }

    public Item remove(DoubleNode node) {
        if (node == null) {
            return null;
        }
        DoubleNode prev = node.left;
        DoubleNode next = node.right;
        node.left = null;
        node.right = null;
        if (prev != null) {
            prev.right = next;
        }
        if (next != null) {
            next.left = prev;
        }
        return node.item;
    }
}
