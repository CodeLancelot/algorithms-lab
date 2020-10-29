package Strings.Compression;

import libraries.BinaryIn;
import libraries.StdOut;

import java.net.URL;

//  The class provides a client for displaying the contents of a binary file in hexadecimal.
public class HexDump {
    private HexDump() {
    }

    public static void main(String[] args) {
        int itemsPerLine = 16;
        if (args.length == 1) itemsPerLine = Integer.parseInt(args[0]);

        try {
            URL txtURL = new URL("https://introcs.cs.princeton.edu/stdlib/abra.txt");
            BinaryIn binaryIn = new BinaryIn(txtURL);
            int count;
            for (count = 0; !binaryIn.isEmpty(); count++) {
                if (count != 0 && count % itemsPerLine == 0) StdOut.println();
                char c = binaryIn.readChar();
                StdOut.printf("%02x ", c & 0xff);
            }
            StdOut.println();
            StdOut.println(count * 8 + " bits");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
