package chapter1.part3;

import java.io.File;

public class Ex43 {
    public static void main(String[] args) {
        String folderName = args[0];
        Queue<String> q = new Queue<>();
        File folder = new File(folderName);
        addFiles(q, folder, "");
        for(String item: q) {
            System.out.println(item);
        }
    }

    public static void addFiles(Queue<String> q, File f, String indent) {
        if (f.isFile()) {
            q.enqueue(indent + f.getName());
        }
        else if (f.isDirectory()) {
            q.enqueue(indent + f.getName());
            for (File child : f.listFiles()) {
                addFiles(q, child, indent + " ");
            }
        }
    }
}
