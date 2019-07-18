package Fundamentals;

import libraries.*;

public class DataAbstraction {
    public static void main(String[] args) {
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            String[] strs = line.split(" ");
            if (checkCircularRotation(strs[0], strs[1])) {
                StdOut.println("Are Circular Rotation");
            } else {
                StdOut.println("Not Circular Rotation");
            }
        }
    }

    private static boolean checkCircularRotation(String s, String t) {
        if (s.length() == t.length()) {
            return cleverSkill(s, t);
        }
        return false;
    }

    private static boolean bruteForce(String s, String t) {
        for (int i = 0, len = s.length(); i < len; i++) {
            if (s.equals(t)) {
                return true;
            } else {
                s = s.substring(1, len) + s.substring(0, 1);
            }
        }
        return false;
    }

    private static boolean cleverSkill(String s, String t) {
        String fullStr = s + s;
        if (fullStr.contains(t)) {
            return true;
        }
        return false;
    }
}