import by.roodxx.api.exception.BitmapException;
import by.roodxx.impl.BitmapSerializer;
import org.apache.commons.codec.binary.Hex;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Random;

/**
 * User: roodxx
 */
public class Runner {
    public static final int NUMBER_OF_TESTS = 10;
    public static void main(String[] args) {
        testByteConvertingForInt();
        testByteConvertingForLong();
        testByteConvertingForObject();
    }

    public static void testByteConvertingForInt() {
        Random rng = new Random();
        System.out.println("Test: Convert int to byte and vice versa");
        for(int test = 0; test < NUMBER_OF_TESTS; test++) {
            System.out.println("    Tets #: "+test);
            byte[] byteValue = new byte[BitmapSerializer.INT_SIZE_IN_BYTES];

            // convert to bytes
            int value = rng.nextInt();
            System.out.println("        Input Value: "+value);
            for (int i = 0; i < BitmapSerializer.INT_SIZE_IN_BYTES; i++) {
                byteValue[i] = (byte) (value >> (BitmapSerializer.INT_SIZE_IN_BITS - BitmapSerializer.BYTE_SIZE * (i + 1)));
            }
            System.out.println("        Hex result: " + Hex.encodeHexString(byteValue).toUpperCase());

            // convert from bytes
            int result = 0;
            if (byteValue.length != BitmapSerializer.INT_SIZE_IN_BYTES) {
                throw new BitmapException.
                        InvalidTypeSize("Size of target value is " + byteValue.length + ". Size of int is " + BitmapSerializer.INT_SIZE_IN_BYTES);
            }
            for (int i = 0; i < BitmapSerializer.INT_SIZE_IN_BYTES; i++) {
                int temp = (0xFF & byteValue[i]);
                temp <<= BitmapSerializer.INT_SIZE_IN_BITS - (i + 1) * BitmapSerializer.BYTE_SIZE;
                result |= temp;
            }

            System.out.println("        Output value: "+result);
            if (value != result) {
                throw new InvalidStateException("Input and Output are not equals!!!");
            }
        }
    }

    public static void testByteConvertingForLong() {
        Random rng = new Random();
        System.out.println("Test: Convert long to byte and vice versa");
        for(int test = 0; test < NUMBER_OF_TESTS; test++) {
            System.out.println("    Tets #: "+test);
            byte[] byteValue = new byte[BitmapSerializer.LONG_SIZE_IN_BYTES];
            // convert to bytes
            long value = rng.nextLong();
            System.out.println("        Input Value: "+value);
            for (int i = 0; i < BitmapSerializer.LONG_SIZE_IN_BYTES; i++) {
                byteValue[i] = (byte) (value >> (BitmapSerializer.LONG_SIZE_IN_BITS - BitmapSerializer.BYTE_SIZE * (i + 1)));
            }
            System.out.println("        Hex result: " + Hex.encodeHexString(byteValue).toUpperCase());

            // convert from bytes
            long result = 0;
            if (byteValue.length != BitmapSerializer.LONG_SIZE_IN_BYTES) {
                throw new BitmapException.
                        InvalidTypeSize("Size of target value is " + byteValue.length + ". Size of int is " + BitmapSerializer.LONG_SIZE_IN_BYTES);
            }
            for (int i = 0; i < BitmapSerializer.LONG_SIZE_IN_BYTES; i++) {
                long temp = (0xFF & byteValue[i]);
                temp <<= BitmapSerializer.LONG_SIZE_IN_BITS - (i + 1) * BitmapSerializer.BYTE_SIZE;
                result |= temp;
            }
            System.out.println("        Output value: "+result);
            if (value != result) {
                throw new InvalidStateException("Input and Output are not equals!!!");
            }
        }
    }

    public static void testByteConvertingForObject() {

    }
}
