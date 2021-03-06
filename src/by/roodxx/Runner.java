package by.roodxx;

import org.apache.commons.codec.binary.Hex;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Random;

/**
 * User: roodxx
 */
public class Runner {
    public static final int NUMBER_OF_TESTS = 10;
    public static void main(String[] args) {
        testBitsSetting();
        /*testByteConvertingForInt();
        testByteConvertingForLong();
        testByteConvertingForObject();  */
    }

    public static void testBitsSetting() {
        //Random rng = new Random();
        byte data[] = new byte[5];
        long size = 15;
        long offset = 9;
        int inputValue = 27554;

        byte inputValueArray[] = BitmapSerializer.convertIntToBytes(inputValue);
        BitmapSerializer.setValueToBitmap(data,size,offset,inputValueArray);
        BitmapSerializer.setValueToBitmap(data,8,0,BitmapSerializer.convertIntToBytes(177));
        byte[] outputValueArray = BitmapSerializer.getValueFromBitmap(data,size,offset);
        if (outputValueArray.length < 4) {
            byte[] temp = outputValueArray;
            outputValueArray = new byte[4];
            for(int i = temp.length-1, j=3; i>= 0; i--,j--) {
                outputValueArray[j]=temp[i];
            }
        }

        int outputValue = BitmapSerializer.convertBytesToInt(outputValueArray);
        if (inputValue != outputValue) {
            throw new InvalidStateException("Input and Output are not equals!!!");
        }
    }
    public static void testByteConvertingForInt() {
        Random rng = new Random();
        System.out.println("Test: Convert int to byte and vice versa");
        for(int test = 0; test < NUMBER_OF_TESTS; test++) {
            System.out.println("    Tets #: "+test);

            int value = rng.nextInt();
            System.out.println("        Input Value: "+value);

            // convert to bytes
            byte[] byteValue = BitmapSerializer.convertIntToBytes(value);
            System.out.println("        Hex result: " + Hex.encodeHexString(byteValue).toUpperCase());

            // convert from bytes
            int result = BitmapSerializer.convertBytesToInt(byteValue);
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

            long value = rng.nextLong();
            System.out.println("        Input Value: "+value);

            // convert to bytes
            byte[] byteValue = BitmapSerializer.convertLongToBytes(value);
            System.out.println("        Hex result: " + Hex.encodeHexString(byteValue).toUpperCase());

            // convert from bytes
            long result = BitmapSerializer.convertBytesToLong(byteValue);
            System.out.println("        Output value: "+result);

            if (value != result) {
                throw new InvalidStateException("Input and Output are not equals!!!");
            }
        }
    }

    public static void testByteConvertingForObject() {

    }
}
