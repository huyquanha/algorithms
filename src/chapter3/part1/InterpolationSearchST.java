package chapter3.part1;

public class InterpolationSearchST<Value> extends
        BinarySearchST<Double, Value> {
    public InterpolationSearchST() {
        super();
    }

    @Override
    public int rank(Double key) {
        checkKey(key);
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (int) ((key - keys[lo]) / (keys[hi] - keys[lo]) * (hi - lo));
            if (key == keys[mid]) {
                return mid;
            } else if (key > keys[mid]) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
}
