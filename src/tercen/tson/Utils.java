package tercen.tson;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Utils {

	// DeSerialize methods

	public static int getIntFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

	public static double getDoubleFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

	public static Boolean getBooleanFromByteArray(byte[] bytes) {
		int result = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).get();
		return result == 1 ? true:false;
    }

	// Serialize methods

	public static byte getByteFromInt(int number) {
	    return (byte) number;
    }

	public static byte[] getBytesFromInt(int number) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(number);
	    return byteBuffer.array();
    }

	public static byte[] getBytesFromDouble(double number) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putDouble(number);
	    return byteBuffer.array();
    }

	public static byte getByteFromBoolean(boolean input) {
		return (byte) (input ? 1:0);
    }

}
