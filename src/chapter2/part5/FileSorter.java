package chapter2.part5;

import java.io.File;
import java.util.Comparator;

/**
 * Ex2.5.28
 */
public class FileSorter {
    private static final Comparator<File> NAME_ORDER = new FileNameOrder();

    private static class FileNameOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return f1.getName().compareTo(f2.getName());
        }
    }

    public static void printDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            sort(files);
            for (File f : files) {
                System.out.println(f.getName());
            }
        }
    }

    /**
     * this sort needs to be stable
     * @param files
     */
    private static void sort(File[] files) {
        int lo = 0, hi = files.length - 1;
        File[] aux = new File[files.length];
        sort(files, aux, lo, hi);
    }

    private static void sort(File[] files, File[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        sort(files, aux, lo, mid);
        sort(files, aux, mid + 1, hi);
        merge(files, aux, lo, mid, hi);
    }

    private static void merge(File[] files, File[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = files[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) files[k] = aux[j++];
            else if (j > hi) files[k] = aux[i++];
            else if (less(aux[j], aux[i])) files[k] = aux[j++];
            else files[k] = aux[i++];
        }
    }

    private static boolean less(File f1, File f2) {
        return NAME_ORDER.compare(f1, f2) < 0;
    }

    public static void main(String[] args) {
        String dir = args[0];
        File f = new File(dir);
        printDirectory(f);
    }
}
