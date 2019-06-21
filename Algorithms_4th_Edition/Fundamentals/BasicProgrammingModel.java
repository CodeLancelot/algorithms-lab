package Fundamentals;
import libraries.*;
public class BasicProgrammingModel {
    public static void main(String[] args) {
        boolean isEqualed = true;
        for (int i = 0; i < args.length; i++) {
            if (i > 0 && !args[i].equals(args[i-1])) {
                StdOut.println(i);
                isEqualed = false;
            }
        }

        if (isEqualed) {
            StdOut.println("equal");
        }
        else {
            StdOut.println("not equal");
        }
    }
}