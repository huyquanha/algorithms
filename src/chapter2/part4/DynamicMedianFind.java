package chapter2.part4;

/**
 * Ex2.4.30
 * @param <Item>
 */
public class DynamicMedianFind<Item extends Comparable<Item>> {
    //Imagining maxPQ having its root sitting on the left, next to the root of minPQ
    //either the root of maxPQ or minPQ is the median, because the one in MaxPQ is larger than all of its other element,
    //and the root of minPQ is smaller than all of its other element. We just need to make sure root(maxPQ) <= root(minPQ)
    //and the size of maxPQ and minPQ are equal or within 1 from each other.
    private MaxPQ<Item> maxPQ;
    private MinPQ<Item> minPQ;

    public void insert(Item item) {
        if (maxPQ.size() <= minPQ.size()) {
            maxPQ.insert(item);
        } else {
            minPQ.insert(item);
        }
        while (!maxPQ.isEmpty() && !minPQ.isEmpty() && less(minPQ.min(), maxPQ.max())) {
            Item rootMax = maxPQ.delMax();
            Item rootMin = minPQ.delMin();
            maxPQ.insert(rootMin);
            minPQ.insert(rootMax);
        }
    }

    public Item findMedian() {
        if (maxPQ.size() <= minPQ.size()) {
            return minPQ.min();
        } else {
            return maxPQ.max();
        }
    }

    public Item delMedian() {
        Item median;
        if (maxPQ.size() <= minPQ.size()) {
            median = minPQ.delMin();
        } else {
            median = maxPQ.delMax();
        }
        while (!maxPQ.isEmpty() && !minPQ.isEmpty() && less(minPQ.min(), maxPQ.max())) {
            Item rootMax = maxPQ.delMax();
            Item rootMin = minPQ.delMin();
            maxPQ.insert(rootMin);
            minPQ.insert(rootMax);
        }
        return median;
    }

    private boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
}
