import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Conversion {

	public static int getIntFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
	
	public static byte[] getBytesFromInt(int number) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(4); 
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(number); 
	    return byteBuffer.array();
    }
	
	public static byte getByteFromInt(int number) {
	    return (byte) number;
    }
	
}
