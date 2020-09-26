//package chapter3.part3;
//
//public class TwoThreeST<Key extends Comparable<Key>, Value> {
//    private class Node {
//        int n;
//        Node left, right;
//
//        public Node(int n) {
//            this.n = n;
//        }
//    }
//
//    private class TwoNode extends Node {
//        Key k;
//        Value v;
//
//        public TwoNode(int n, Key k, Value v) {
//            super(n);
//            this.k = k;
//            this.v = v;
//        }
//
//        public ThreeNode to3Node(Key k1, Value v1) {
//            if (k1.compareTo(k) < 0) {
//                return new ThreeNode(n + 1, )
//            }
//        }
//    }
//
//    private class ThreeNode extends Node {
//        Key ks, kl;
//        Value vs, vl;
//        Node mid;
//
//        public ThreeNode(int n, Key ks, Value vs, Key kl, Value vl) {
//            super(n);
//            this.ks = ks;
//            this.vs = vs;
//            this.kl = kl;
//            this.vl = vl;
//        }
//    }
//}
