package chapter2.part5;

import java.io.File;
import java.util.*;

/**
 * Ex2.5.29
 */
public class LS {
    private static final Comparator<File> SIZE_ASC = new SizeAscOrder();
    private static final Comparator<File> SIZE_DESC = new SizeDescOrder();
    private static final Comparator<File> NAME_ASC = new NameAscOrder();
    private static final Comparator<File> NAME_DESC = new NameDescOrder();
    private static final Comparator<File> DATE_ASC = new DateAscOrder();
    private static final Comparator<File> DATE_DESC = new DateDescOrder();

    private static class SizeAscOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return Long.compare(f1.length(), f2.length());
        }

        @Override
        public String toString() {
            return "SIZE_ASC";
        }
    }

    private static class SizeDescOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return Long.compare(f2.length(), f1.length());
        }

        @Override
        public String toString() {
            return "SIZE_DESC";
        }
    }

    private static class NameAscOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return f1.getName().compareTo(f2.getName());
        }

        @Override
        public String toString() {
            return "NAME_ASC";
        }
    }

    private static class NameDescOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return f2.getName().compareTo(f1.getName());
        }

        @Override
        public String toString() {
            return "NAME_DESC";
        }
    }

    private static class DateAscOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return Long.compare(f1.lastModified(), f2.lastModified());
        }

        @Override
        public String toString() {
            return "DATE_ASC";
        }
    }

    private static class DateDescOrder implements Comparator<File> {
        public int compare(File f1, File f2) {
            return Long.compare(f2.lastModified(), f1.lastModified());
        }

        @Override
        public String toString() {
            return "DATE_DESC";
        }
    }

    public static void main(String[] args) {
        String dir = args[0];
        /**
         * -s: sort by size
         * -n: sort by name
         * -t: sort by last modified
         * asc: ascending (default)
         * desc: descending
         */
        Comparator<File>[] comparators = new Comparator[3];
        comparators[0] = SIZE_ASC;
        comparators[1] = NAME_ASC;
        comparators[2] = DATE_ASC;
        if (args.length > 1) {
            Map<String, String> sortMap = new LinkedHashMap<>();
            Set<String> allowedSortBy = new HashSet<>();
            Set<String> allowedSortOrder = new HashSet<>();
            allowedSortBy.addAll(Arrays.asList("-s", "-n", "-t"));
            allowedSortOrder.addAll(Arrays.asList("asc", "desc"));
            int idx = 1;
            while (idx < args.length) {
                if (allowedSortBy.contains(args[idx].toLowerCase())) {
                    // this is a sort by
                    String sortBy = args[idx].toLowerCase();
                    String sortOrder = "asc";
                    if (idx + 1 < args.length && allowedSortOrder.contains(args[idx + 1].toLowerCase())) {
                        // the next one is a sort order
                        sortOrder = args[idx + 1].toLowerCase();
                        idx++;
                    }
                    if (!sortMap.containsKey(sortBy)) {
                        sortMap.put(sortBy, sortOrder);
                    } else {
                        System.out.println("Usage: <dir> [-s/-n/-t] [asc/desc] [-s/-n/-t] [asc/desc] [-s/-n/-t] [asc/desc]");
                        return;
                    }
                    idx++;
                } else {
                    System.out.println("Usage: <dir> [-s/-n/-t] [asc/desc] [-s/-n/-t] [asc/desc] [-s/-n/-t] [asc/desc]");
                    return;
                }
            }
            if (!sortMap.containsKey("-s")) {
                sortMap.put("-s", "asc");
            }
            if (!sortMap.containsKey("-n")) {
                sortMap.put("-n", "asc");
            }
            if (!sortMap.containsKey("-t")) {
                sortMap.put("-t", "asc");
            }
            int i = 0;
            for (String key : sortMap.keySet()) {
                switch(key) {
                    case "-s":
                        comparators[i++] = "asc".equals(sortMap.get(key)) ? SIZE_ASC : SIZE_DESC;
                        break;
                    case "-n":
                        comparators[i++] = "asc".equals(sortMap.get(key)) ? NAME_ASC : NAME_DESC;
                        break;
                    case "-t":
                        comparators[i++] = "asc".equals(sortMap.get(key)) ? DATE_ASC : DATE_DESC;
                        break;
                }
            }
        }
        //sort using the provided comparators. The sort should be stable
        File directory = new File(dir);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (Comparator c: comparators) {
                System.out.println(c);
            }
            sort(files, comparators);
            for (File f : files) {
                System.out.println(f.length() + " " + f.getName() + " " + f.lastModified());
            }
        }
    }

    private static void sort(File[] files, Comparator<File>[] comparators) {
        int N = files.length;
        File[] aux = new File[N];
        sort(files, aux, 0, N - 1, comparators);
    }

    private static void sort(File[] files, File[] aux, int lo, int hi, Comparator<File>[] comparators) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        sort(files, aux, lo, mid, comparators);
        sort(files, aux, mid + 1, hi, comparators);
        merge(files, aux, lo, mid, hi, comparators);
    }

    private static void merge(File[] files, File[] aux, int lo, int mid, int hi, Comparator<File>[] comparators) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = files[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) files[k] = aux[j++];
            else if (j > hi) files[k] = aux[i++];
            else if (less(aux[j], aux[i], comparators)) files[k] = aux[j++];
            else files[k] = aux[i++];
        }
    }

    private static boolean less(File f1, File f2, Comparator<File>[] comparators) {
        int M = comparators.length;
        for (int i = 0; i < M; i++) {
            Comparator<File> c = comparators[i];
            int cmp = c.compare(f1, f2);
            if (cmp < 0) {
                return true;
            } else if (cmp > 0) {
                return false;
            }
        }
        //if we get here, that means f1 and f2 are equal
        return false;
    }
}
