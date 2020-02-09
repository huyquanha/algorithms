package chapter1.part1;

public class Exercise35 {
    private static int SIDES = 6;

    public static double[] desiredDist() {
        double[] dist = new double[2*SIDES+1];
        for (int i = 1; i <= SIDES; i++)
            for (int j = 1; j <= SIDES; j++)
                dist[i+j] += 1.0;
        for (int k = 2; k <= 2*SIDES; k++)
            dist[k] /= 36.0;
        return dist;
    }

    private static void print(double[] a) {
        for (int i=0; i< a.length; i++) {
            System.out.print(a[i]+ " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] desiredDist = desiredDist();
        print(desiredDist);

        double[] realDist = new double[2*SIDES+1];
        double[] baskets = new double[2*SIDES + 1];
        for (int i=0; i< N ;i++) {
            //for N dice throws
            int dice1 = (int) (Math.random()*6) + 1;
            int dice2 = (int) (Math.random()*6) + 1;
            baskets[dice1+dice2] += 1.0;
        }
        for (int k=2; k<= 2*SIDES; k++) {
            realDist[k] = baskets[k]/N;
        }
        print(realDist);
    }
}
