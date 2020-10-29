package Strings.Compression;

import libraries.BinaryIn;
import libraries.StdOut;

import java.net.URL;

public class BinaryDump {
    private BinaryDump() {
    }

    public static void main(String[] args) {
        int bitsPerLine = 16;
        if (args.length == 1) bitsPerLine = Integer.parseInt(args[0]);

        try {
            URL txtURL = new URL("https://introcs.cs.princeton.edu/stdlib/abra.txt");
            BinaryIn binaryIn = new BinaryIn(txtURL);

            int count;
            for (count = 0; !binaryIn.isEmpty(); count++) {
                if (count != 0 && count % bitsPerLine == 0) StdOut.println();
                if (binaryIn.readBoolean()) StdOut.print(1);
                else StdOut.print(0);
            }
            StdOut.println();
            StdOut.println(count + " bits");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}