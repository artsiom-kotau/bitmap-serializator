package by.roodxx.api;

/**
 * @author: roodxx
 */
public class BitmapCoordinates {

    private long offset;

    private long length;

    public BitmapCoordinates() {
    }

    public BitmapCoordinates(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapCoordinates that = (BitmapCoordinates) o;

        if (length != that.length) return false;
        if (offset != that.offset) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (offset ^ (offset >>> 32));
        result = 31 * result + (int) (length ^ (length >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BitmapCoordinates{" +
                "offset=" + offset +
                ", length=" + length +
                '}';
    }
}
