package Fundamentals;

import libraries.*;

import java.io.File;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        printFileList(args[0], 0);
    }

    static void printFileList(String filePath, int indent) {
        File folder = new File(filePath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                for (int i = indent; i > 0; i--) {
                    StdOut.print("  ");
                }
                if (file.isFile()) {
                    StdOut.println(file.getName());
                } else if (file.isDirectory()) {
                    StdOut.println(file.getName());
                    printFileList(file.getPath(), indent + 1);
                }
            }
        }
    }

}