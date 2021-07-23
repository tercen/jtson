import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Conversion {

	public static int getIntFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
}
