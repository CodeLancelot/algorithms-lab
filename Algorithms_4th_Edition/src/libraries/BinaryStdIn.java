package libraries;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * <i>Binary standard input</i>. This class provides methods for reading
 * in bits from standard input.
 * <p>
 * All primitive types are assumed to be represented using their
 * standard Java representations, in big-endian (most significant
 * byte first) order.
 * <p>
 * The client should not intermix calls to {@code BinaryStdIn} with calls
 * to {@code StdIn} or {@code System.in};
 * otherwise unexpected behavior will result.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public final class BinaryStdIn {
    private static final int EOF = -1;      // end of file

    private static BufferedInputStream in;  // input stream
    private static int buffer;              // one character buffer
    private static int n;                   // number of bits left in buffer
    private static boolean isInitialized;   // has BinaryStdIn been called for first time?

    // don't instantiate
    private BinaryStdIn() {
    }

    // fill buffer
    private static void initialize() {
        in = new BufferedInputStream(System.in);
        buffer = 0;
        n = 0;
        fillBuffer();
        isInitialized = true;
    }

    private static void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            System.out.println("EOF");
            buffer = EOF;
            n = -1;
        }
    }

    /**
     * Close this input stream and release any associated system resources.
     */
    public static void close() {
        if (!isInitialized) initialize();
        try {
            in.close();
            isInitialized = false;
        } catch (IOException ioe) {
            throw new IllegalStateException("Could not close BinaryStdIn", ioe);
        }
    }

    public static boolean isEmpty() {
        if (!isInitialized) initialize();
        return buffer == EOF;
    }

    /**
     * Reads the next bit of data from standard input and return as a boolean.
     */
    public static boolean readBoolean() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) fillBuffer();
        return bit;
    }

    /**
     * Reads the next 8 bits from standard input and return as an 8-bit char.
     */
    public static char readChar() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        // special case when aligned byte
        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        // combine last n bits of current buffer with first 8-n bits of new buffer
        int x = buffer;
        x <<= (8 - n);
        int oldN = n;
        fillBuffer();
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        n = oldN;
        x |= (buffer >>> n);
        return (char) (x & 0xff);
        // the above code doesn't quite work for the last character if n = 8
        // because buffer will be -1, so there is a special case for aligned byte
    }

    /**
     * Reads the next <em>r</em> bits from standard input and return as an <em>r</em>-bit character.
     *
     * @throws NoSuchElementException   if there are fewer than {@code r} bits available on standard input
     * @throws IllegalArgumentException unless {@code 1 <= r <= 16}
     */
    public static char readChar(int r) {
        if (r < 1 || r > 16) throw new IllegalArgumentException("Illegal value of r = " + r);

        // optimize r = 8 case
        if (r == 8) return readChar();

        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    /**
     * Reads the remaining bytes of data from standard input and return as a string.
     */
    public static String readString() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }


    /**
     * Reads the next 16 bits from standard input and return as a 16-bit short.
     */
    public static short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * Reads the next 32 bits from standard input and return as a 32-bit int.
     */
    public static int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * Reads the next <em>r</em> bits from standard input and return as an <em>r</em>-bit int.
     */
    public static int readInt(int r) {
        if (r < 1 || r > 32) throw new IllegalArgumentException("Illegal value of r = " + r);

        // optimize r = 32 case
        if (r == 32) return readInt();

        int x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    /**
     * Reads the next 64 bits from standard input and return as a 64-bit long.
     */
    public static long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * Reads the next 64 bits from standard input and return as a 64-bit double.
     */
    public static double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * Reads the next 32 bits from standard input and return as a 32-bit float.
     */
    public static float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public static byte readByte() {
        char c = readChar();
        return (byte) (c & 0xff);
    }
}