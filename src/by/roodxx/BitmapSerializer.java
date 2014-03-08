package by.roodxx;

import by.roodxx.api.BitmapCoordinates;
import by.roodxx.api.deserializator.ITypeParser;
import by.roodxx.api.exception.BitmapException;
import by.roodxx.api.serializator.ITypeSerializer;

/**
 * @author: roodxx
 */
public class BitmapSerializer {

    public static final int BYTE_SIZE = 8;
    public static final int INT_SIZE_IN_BYTES = 4;
    public static final int INT_SIZE_IN_BITS = INT_SIZE_IN_BYTES * BYTE_SIZE;
    public static final int LONG_SIZE_IN_BYTES = 8;
    public static final int LONG_SIZE_IN_BITS = LONG_SIZE_IN_BYTES * BYTE_SIZE;

    private BitmapSerializer() {
    }

    public static void setInt(byte[] data, BitmapCoordinates coordinates, int value) throws BitmapException {
        byte[] byteValue = convertIntToBytes(value);
        setValueToBitmap(data, coordinates.getLength(), coordinates.getOffset(), byteValue);
    }

    protected static byte[] convertIntToBytes(int value) {
        byte[] byteValue = new byte[INT_SIZE_IN_BYTES];
        for (int i = 0; i < INT_SIZE_IN_BYTES; i++) {
            byteValue[i] = (byte) (value >> (INT_SIZE_IN_BITS - BYTE_SIZE * (i + 1)));
        }
        return byteValue;
    }

    public static int getInt(byte[] data, BitmapCoordinates coordinates) throws BitmapException {

        byte[] byteValue = getValueFromBitmap(data, coordinates.getLength(), coordinates.getOffset());
        return convertBytesToInt(byteValue);
    }

    protected static int convertBytesToInt(byte[] byteValue) {
        int result = 0;
        if (byteValue != null) {
            if (byteValue.length != INT_SIZE_IN_BYTES) {
                throw new BitmapException.
                        InvalidTypeSize("Size of target value is " + byteValue.length + ". Size of int is " + INT_SIZE_IN_BYTES);
            }
            for (int i = 0; i < INT_SIZE_IN_BYTES; i++) {
                long temp = (0xFF & byteValue[i]);
                temp <<= INT_SIZE_IN_BITS - (i + 1) * BYTE_SIZE;
                result |= temp;
            }
        }
        return result;
    }

    public static void setLong(byte[] data, BitmapCoordinates coordinates, long value) throws BitmapException {
        byte[] byteValue = convertLongToBytes(value);
        setValueToBitmap(data, coordinates.getLength(), coordinates.getOffset(), byteValue);
    }

    protected static byte[] convertLongToBytes(long value) {
        byte[] byteValue = new byte[LONG_SIZE_IN_BYTES];
        for (int i = 0; i < LONG_SIZE_IN_BYTES; i++) {
            byteValue[i] = (byte) (value >> (LONG_SIZE_IN_BITS - BYTE_SIZE * (i + 1)));
        }
        return byteValue;
    }

    public long getLong(byte[] data, BitmapCoordinates coordinates) throws BitmapException {
        byte[] byteValue = getValueFromBitmap(data, coordinates.getLength(), coordinates.getOffset());
        return convertBytesToLong(byteValue);
    }

    protected static long convertBytesToLong(byte[] byteValue) {
        long result = 0;
        if (byteValue != null) {
            if (byteValue.length != LONG_SIZE_IN_BYTES) {
                throw new BitmapException.
                        InvalidTypeSize("Size of target value is " + byteValue.length + ". Size of long is " + LONG_SIZE_IN_BYTES);
            }
            for (int i = 0; i < LONG_SIZE_IN_BYTES; i++) {
                long temp = (0xFF & byteValue[i]);
                temp <<= LONG_SIZE_IN_BITS - (i + 1) * BYTE_SIZE;
                result |= temp;
            }
        }
        return result;
    }

    public static <Type> void setValue(byte[] data, BitmapCoordinates coordinates, Type value, ITypeSerializer<Type> serializer)
            throws BitmapException {
        try {
            byte[] byteValue = serializer.serialize(value);
            setValueToBitmap(data, coordinates.getLength(), coordinates.getOffset(), byteValue);
        } catch (Exception exc) {
            throw new BitmapException(exc);
        }
    }

    public static <Type> Type getValue(byte[] data, BitmapCoordinates coordinates, ITypeParser<Type> parser)
            throws BitmapException {
        try {
            byte[] byteValue = getValueFromBitmap(data, coordinates.getLength(), coordinates.getOffset());
            return parser.parse(byteValue);
        } catch (Exception exc) {
            throw new BitmapException(exc);
        }
    }

    protected static byte[] getValueFromBitmap(byte[] data, int size, int offset) throws BitmapException {
        return new byte[size];
    }

    protected static void setValueToBitmap(byte[] data, int size, int offset, byte[] value) throws BitmapException {

    }
}
