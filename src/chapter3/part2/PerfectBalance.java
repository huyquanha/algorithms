package chapter3.part2;

import chapter2.part3.Quick;

/**
 * Ex3.2.25
 * @param <Key>
 */
public class PerfectBalance<Key extends Comparable<Key>> {
    // assume keys are unique
    public BST<Key, Integer> constructBST(Key[] keys) {
        Quick.sort(keys);
        int n = keys.length;
        BST<Key, Integer> bst = new BST<>();
        insert(bst, keys, 0, n - 1);
        return bst;
    }

    private void insert(BST<Key, Integer> bst, Key[] keys,int lo, int hi) {
        if (lo > hi) return;
        int mid = lo + (hi - lo)/2;
        bst.put(keys[mid], mid);
        insert(bst, keys, lo, mid - 1);
        insert(bst, keys, mid + 1, hi);
    }

    public static void main(String[] args) {
        String input = "FABCDGH";
        String[] letters = new String[input.length()];
        for (int i = 0; i < input.length(); i++) {
            letters[i] = input.substring(i, i + 1);
        }
        PerfectBalance<String> pb = new PerfectBalance<>();
        BST<String, Integer> bst = pb.constructBST(letters);
        for (String key: bst.keys()) {
            System.out.print(key + " ");
        }
        System.out.println();
    }
}
