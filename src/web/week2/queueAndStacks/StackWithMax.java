package web.week2;

import chapter1.part3.Stack;

public class StackWithMax {
    private Stack<Double> items;
    private Stack<Double> max;

    public StackWithMax() {
        items = new Stack<>();
        max = new Stack<>();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public void push(Double item) {
        items.push(item);
        if (max.isEmpty() || item > max.peek()) {
            max.push(item);
        }
    }

    public Double pop() {
        Double item = items.pop();
        if (item.equals(max.peek())) {
            max.pop();
        }
        return item;
    }

    public Double findMax() {
        return max.peek();
    }

    public static void main(String[] args) {

    }
}
