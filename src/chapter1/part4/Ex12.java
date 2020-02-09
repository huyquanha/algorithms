package chapter1.part4;

public class Ex12 {
    public static void printCommon(int[] a, int[] b, int N) {
        int i = 0, j = 0;
        while (i < N && j < N) {
            if (a[i] == b[j]) {
                System.out.print(a[i] + " ");
                i++;
                j++;
            } else if (a[i] > b[j]) {
                j++;
            } else { //a[i] < b[j]
                i++;
            }
        }
        System.out.println();
    }
}
