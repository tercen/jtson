package tercen.tson;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

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
	
	public static short[] getShortArrayFromByteArray(byte[] bytes) {
		ShortBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
		short[] array = new short[buffer.remaining()];
		buffer.get(array);
		return array;
    }
	
	public static int[] getIntArrayFromByteArray(byte[] bytes) {
		IntBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
		int[] array = new int[buffer.remaining()];
		buffer.get(array);
		return array;
    }
	
	public static long[] getLongArrayFromByteArray(byte[] bytes) {
		LongBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
		long[] array = new long[buffer.remaining()];
		buffer.get(array);
		return array;
    }
	
	public static float[] getFloatArrayFromByteArray(byte[] bytes) {
		FloatBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
		float[] array = new float[buffer.remaining()];
		buffer.get(array);
		return array;
    }
	
	public static double[] getDoubleArrayFromByteArray(byte[] bytes) {
		DoubleBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asDoubleBuffer();
		double[] array = new double[buffer.remaining()];
		buffer.get(array);
		return array;
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
	
	public static byte[] getBytesFromShortArray(short[] input) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 2); 
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer buffer = byteBuffer.asShortBuffer();
        buffer.put(input);
        return byteBuffer.array();
	}
	
	public static byte[] getBytesFromIntArray(int[] input) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 4); 
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        IntBuffer buffer = byteBuffer.asIntBuffer();
        buffer.put(input);
        return byteBuffer.array();
	}
	
	public static byte[] getBytesFromLongArray(long[] input) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 8); 
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        LongBuffer buffer = byteBuffer.asLongBuffer();
        buffer.put(input);
        return byteBuffer.array();
	}
	
	public static byte[] getBytesFromFloatArray(float[] input) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 4); 
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.put(input);
        return byteBuffer.array();
	}
	
	public static byte[] getBytesFromDoubleArray(double[] input) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 8); 
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
        buffer.put(input);
        return byteBuffer.array();
	}
}
