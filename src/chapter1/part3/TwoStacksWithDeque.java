package chapter1.part3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoStacksWithDeque<Item> implements Iterable<Item> {
    private Deque<Item> dq;
    private int leftN, rightN;

    public boolean isLeftEmpty() {
        return leftN == 0;
    }

    public boolean isRightEmpty() {
        return rightN == 0;
    }

    public int leftSize() {
        return leftN;
    }

    public int rightSize() {
        return rightN;
    }

    public void pushToLeft(Item item) {
        dq.pushLeft(item);
        leftN++;
    }

    public void pushToRight(Item item) {
        dq.pushRight(item);
        rightN++;
    }

    public Item popFromLeft() {
        if (isLeftEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = dq.popLeft();
        leftN--;
        return item;
    }

    public Item popFromRight() {
        if (isRightEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = dq.popRight();
        rightN--;
        return item;
    }

    public Iterator<Item> iterator() {
        return dq.iterator();
    }
}
