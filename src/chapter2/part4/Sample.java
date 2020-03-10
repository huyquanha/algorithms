//package chapter2.part4;
//
//public class Sample {
//    private double[] p, w;
//    private double T = 0;
//
//    public Sample(double[] p) {
//        int N = p.length;
//        this.p = new double[N + 1];
//        this.w = new double[N + 1];
//        for (int i = 1; i <= N; i++) {
//            this.p[i] = p[i - 1];
//            T += p[i - 1];
//            w[i] = p[i - 1];
//        }
//        for (int i = N / 2; i >= 1; i--) {
//            int j = i * 2;
//            w[i] += w[j];
//            if (j < N) {
//                w[i] += w[j + 1];
//            }
//        }
//    }
//
//    public int random() {
//        double r = Math.random() * T; //from 0 (inclusive) to T (exclusive)
//
//    }
//}
