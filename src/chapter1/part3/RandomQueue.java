package chapter1.part3;

//Exercise 1.3.35
import java.util.Iterator;

class Card {
    String type; //to simplify, we called them heart, spade, club, diamond
    int value; //J is 11, Q is 12, K is 13, A is 1

    public Card(String type, int value) {
        this.type = type;
        this.value = value;
    }
}

public class RandomQueue<Item> implements Iterable<Item> {
    private Item[] q = (Item[]) new Object[1];
    private int N = 0;

    public boolean isEmpty() {
        return N ==0;
    }

    public void enqueue(Item item) {
        if (N == q.length) {
            resize(2*q.length);
        }
        q[N++] = item;
    }

    // random dequeue
    public Item dequeue() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        int r = (int) (Math.random() * N);
        Item tmp = q[r];
        q[r] = q[N-1];
        q[N-1] = tmp;
        Item item = q[--N];
        q[N] = null;
        if (N > 0 && N == q.length/4) {
            resize(q.length/2);
        }
        return item;
    }

    public Item sample() {
        int r = (int) (Math.random() * N);
        Item item = q[r];
        return item;
    }

    private void resize(int newSize) {
        assert newSize >= N;
        Item[] tmp = (Item[]) new Object[newSize];
        for (int i=0; i< N; i++) {
            tmp[i] = q[i];
        }
        q = tmp;
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    //Exercise 1.3.36
    private class RandomArrayIterator implements Iterator<Item> {
        private int cur = 0;

        public RandomArrayIterator() {
            for (int i=0; i< N; i++) {
                int r = (int)(Math.random()* (N-i)) + i;
                Item tmp = q[r];
                q[r] = q[i];
                q[i] = tmp;
            }
        }

        public boolean hasNext() {
            return cur < N;
        }

        public Item next() {
            Item item = q[cur];
            cur++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomQueue<Card> deck = new RandomQueue<>();
        String[] types = {"heart", "diamond", "spade", "club"};
        for (int i=0; i< types.length; i++) {
            for (int j=1; j<= 13; j++) {
                deck.enqueue(new Card(types[i], j));
            }
        }
        for (Card c : deck) {
            System.out.println(c.type + " "+ c.value);
        }
        System.out.println("---------------");

        int numHands = 4;
        RandomQueue<Card>[] hands = (RandomQueue<Card>[]) new RandomQueue[numHands];
        int activeHand = 0;
        while (!deck.isEmpty()) {
            if (hands[activeHand] == null) {
                hands[activeHand] = new RandomQueue<>();
            }
            hands[activeHand].enqueue(deck.dequeue());
            activeHand = (activeHand +1) % numHands;
        }
        for (int i=0; i< numHands; i++) {
            System.out.println("Hand "+ i+ ": ");
            for (Card c : hands[i]) {
                System.out.println(c.type + " "+ c.value);
            }
            System.out.println("------------------");
        }
    }
}
