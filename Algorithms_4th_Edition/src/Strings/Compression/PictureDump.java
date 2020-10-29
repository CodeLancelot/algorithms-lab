package Strings.Compression;

import libraries.BinaryIn;
import libraries.Picture;

import java.awt.Color;
import java.net.URL;

// The {@code PictureDump} class provides a client for displaying the contents of a binary file as a black-and-white picture.
public class PictureDump {
    private PictureDump() {
    }

    public static void main(String[] args) {
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        try {
            URL txtURL = new URL("https://introcs.cs.princeton.edu/stdlib/abra.txt");
            BinaryIn binaryIn = new BinaryIn(txtURL);
            Picture picture = new Picture(width, height);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (!binaryIn.isEmpty()) {
                        if (binaryIn.readBoolean()) picture.set(col, row, Color.BLACK);
                        else picture.set(col, row, Color.WHITE);
                    } else {
                        picture.set(col, row, Color.RED);
                    }
                }
            }
            picture.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
