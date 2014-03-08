package by.roodxx.api.exception;

/**
 * @author: roodxx
 */
public class BitmapException extends RuntimeException {

    public BitmapException() {
    }

    public BitmapException(String message) {
        super(message);
    }

    public BitmapException(String message, Throwable cause) {
        super(message, cause);
    }

    public BitmapException(Throwable cause) {
        super(cause);
    }

    public static class InvalidTypeSize extends BitmapException {
        public InvalidTypeSize(String message) {
            super(message);
        }

        public InvalidTypeSize(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
