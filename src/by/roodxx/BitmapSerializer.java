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

    protected static byte[] getValueFromBitmap(byte[] data, long size, long offset) throws BitmapException {

        return new byte[1];
    }

    protected static void setValueToBitmap(byte[] data, long size, long offset, byte[] value) throws BitmapException {

        //calculate start byte in value
        int startByteInValue = value.length - (int)(size/BYTE_SIZE);
        if (size%BYTE_SIZE != 0) {
            startByteInValue--;
        }

        long counterOfSize = size;
        long counterOfOffset = offset;
        for(int byteIndex = startByteInValue; byteIndex < value.length;byteIndex++) {

            int targetBitAmount = (int)(counterOfSize%BYTE_SIZE);
            if (targetBitAmount == 0) {
                targetBitAmount = BYTE_SIZE;
            }
            counterOfSize-=targetBitAmount;

            while (targetBitAmount != 0) {

                //calculate index of processing byte in data
                int byteDataIndex  = (int)(counterOfOffset/BYTE_SIZE);

                //calculate how many bits available in current data byte
                int availableBitsInDataByte = (int)(BYTE_SIZE - counterOfOffset%BYTE_SIZE);


                byte temp = value[byteIndex];
                //calculate processed bits
                int processedBits = availableBitsInDataByte < targetBitAmount ? availableBitsInDataByte : targetBitAmount;
                //calculate shift  in current data byte
                int shiftValue = Math.abs(availableBitsInDataByte-targetBitAmount);

                if (availableBitsInDataByte > targetBitAmount) {
                    temp <<=shiftValue;
                } else {
                    temp>>=shiftValue;
                    temp&=getMask(BYTE_SIZE-availableBitsInDataByte);
                }
                data[byteDataIndex] |= temp;

                targetBitAmount-= processedBits;
                counterOfOffset+= processedBits;
            }
        }
    }

    protected static byte getMask(int shiftValue) {
        int mask = 0x00;
        switch (shiftValue) {
            case 0: {mask = 0xFF; break;}
            case 1: {mask = 0x7F; break;}
            case 2: {mask = 0x3F; break;}
            case 3: {mask = 0x1F; break;}
            case 4: {mask = 0xF; break;}
            case 5: {mask = 0x7; break;}
            case 6: {mask = 0x3; break;}
            case 7: {mask = 0x1; break;}
        }
        return (byte)mask;
    }

    protected void checkArguments(byte[] data, BitmapCoordinates coord, byte[] value) {

    }
}
