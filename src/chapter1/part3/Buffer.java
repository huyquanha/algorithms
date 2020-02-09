package chapter1.part3;

import java.util.NoSuchElementException;

//Ex 1.3.44
public class Buffer {
    //Imagine the cursor is the border between top of mainStack and top of subStack
    //mainStack...top | top...subStack
    private Stack<Character> mainStack;
    private Stack<Character> subStack;
    private int N;

    public Buffer() {
        mainStack = new Stack<>();
        subStack = new Stack<>();
        N=0;
    }

    public void insert(char c) {
        mainStack.push(c);
        N++;
    }

    public char delete() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException();
        }
        char c = mainStack.pop();
        N--;
        return c;
    }

    public void left(int k) {
        if (k<0 || k> mainStack.size()) {
            throw new UnsupportedOperationException();
        }
        int step =0;
        while (step < k) {
            subStack.push(mainStack.pop());
            step++;
        }
    }

    public void right(int k) {
        if (k<0 || k > subStack.size()) {
            throw new UnsupportedOperationException();
        }
        int step =0;
        while (step < k) {
            mainStack.push(subStack.pop());
            step++;
        }
    }

    public int size() {
        return N;
    }
}
