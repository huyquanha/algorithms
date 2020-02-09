package chapter1.part4;

import chapter1.part3.Stack;
import chapter1.part3.Steque;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Ex 1.4.30
public class DequeWithStackSteque<Item> implements Iterable<Item> {
    private Stack<Item> s = new Stack<>();
    private Steque<Item> st = new Steque<>();
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void pushLeft(Item item) {
        s.push(item);
        N++;
    }

    public Item popLeft() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (!s.isEmpty()) {
            Item item = s.pop();
            N--;
            return item;
        } else {
            //all N items are on steque
            reBalance(true);

            //now pop the item out of stack
            Item item = s.pop();
            N--;
            return item;
        }
    }

    public void pushRight(Item item) {
        st.push(item);
        N++;
    }

    public Item popRight() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (!st.isEmpty()) {
            Item item = st.pop();
            N--;
            return item;
        } else {
            // all N items on the stack
            // first pop all items from the stack and push to steque
            while (!s.isEmpty()) {
                st.push(s.pop());
            }
            //now the left is empty and the right has elements => do the exact same with popLeft
            reBalance(false);

            Item item = st.pop();
            N--;
            return item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        while (!s.isEmpty()) {
            st.enqueue(s.pop());
        }
        return st.iterator();
    }

    private void reBalance(boolean left) {
        int idx = 0;
        //if we reBalance for popLeft (left is true), we want the stack (left) to have at least 1 element after the rebalance
        //therefore, the threshold is smaller if left is true
        int threshold = left ? N/2 : (N/2 + 1);
        while (idx < N) {
            Item item = st.pop();
            if (idx < threshold) {
                // keep the upper half of items on the steque in the same order
                st.enqueue(item);
            } else {
                //and push the lower half to stack
                s.push(item);
            }
            idx++;
        }
    }
}
