package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        String line = "a b c d e f g h i j k";
        LinkedList<String> list = new LinkedList<>();

        String[] arr = line.split("\\s+");
        for (String str : arr) {
            list.insertHead(str);
        }
        list.print();
        for (String str : args) {
            list.delete(Integer.parseInt(str));
            list.print();
        }
    }
}