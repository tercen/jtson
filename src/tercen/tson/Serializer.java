package tercen.tson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Serializer {

	private ByteArrayOutputStream bos;

	public Serializer(Object obj) {
		this.bos = new ByteArrayOutputStream();
		try {
			this.addString(Spec.TSON_SPEC_VERSION);
			this.addObject(obj);
		} catch (TsonError e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void addType(int type) throws UnsupportedEncodingException {
		this.bos.write(Utils.getByteFromInt(type));
	}

	private void addLength(int length) throws IOException {
		this.bos.write(Utils.getBytesFromInt(length));
	}

	private void addObject(Object obj) throws TsonError, IOException {
		// Basic types
		if (obj == null) {
			this.addNull();
		} else if (obj instanceof String) {
			this.addString((String) obj);
		} else if (obj instanceof Integer) {
			this.addInteger((Integer) obj);
		} else if (obj instanceof Double) {
			this.addDouble((Double) obj);
		} else if (obj instanceof Boolean) {
			this.addBoolean((Boolean) obj);
		} else if (obj instanceof CStringList) {
			this.addStringList((CStringList) obj);
		} else if (obj instanceof List) {
			this.addList((List) obj);
		} else if (obj instanceof Map) {
			this.addMap((Map) obj);
		} else if (obj.getClass().isArray()) {
			this.addArray(obj);
		} else {
			throw new TsonError("Unknown object type.");
		}
	}
	
	// Basic types (null, string, integer, double, bool)
	private void addNull() throws UnsupportedEncodingException {
		 this.addType(Spec.NULL_TYPE);
	}

	private void addString(String obj) throws IOException {
		this.addType(Spec.STRING_TYPE);
		this.bos.write(obj.getBytes(StandardCharsets.UTF_8));
		this.addNull();
	}

	private void addInteger(Integer obj) throws IOException {
		this.addType(Spec.INTEGER_TYPE);
		this.bos.write(Utils.getBytesFromInt(obj));
	}

	private void addDouble(Double obj) throws IOException {
		this.addType(Spec.DOUBLE_TYPE);
		this.bos.write(Utils.getBytesFromDouble(obj));
	}

	private void addBoolean(Boolean obj) throws IOException {
		this.addType(Spec.BOOL_TYPE);
		this.bos.write(Utils.getByteFromBoolean(obj));
	}

	private void addList(List<Object> list) throws IOException, TsonError {
		this.addType(Spec.LIST_TYPE);
        this.addLength(list.size());

        for (Object item : list) {
        	this.addObject(item);
        }
	}
	
	private void addArray(Object obj) throws IOException, TsonError {
		if (obj instanceof byte[]) {
			byte[] input = (byte[]) obj;
			this.addIntArrayProperties(Spec.LIST_INT8_TYPE, input.length);
			this.bos.write(input);
		} else if (obj instanceof short[]) {
			short[] input = (short[]) obj;
			this.addIntArrayProperties(Spec.LIST_INT16_TYPE, input.length);
			this.bos.write(Utils.getBytesFromShortArray(input));
		} else if (obj instanceof int[]) {
			int[] input = (int[]) obj;
			this.addIntArrayProperties(Spec.LIST_INT32_TYPE, input.length);
			this.bos.write(Utils.getBytesFromIntArray(input));
		} else if (obj instanceof long[]) {
			long[] input = (long[]) obj;
			this.addIntArrayProperties(Spec.LIST_INT64_TYPE, input.length);
			this.bos.write(Utils.getBytesFromLongArray(input));
		} else if (obj instanceof float[]) {
			float[] input = (float[]) obj;
			this.addIntArrayProperties(Spec.LIST_FLOAT32_TYPE, input.length);
			this.bos.write(Utils.getBytesFromFloatArray(input));
		} else if (obj instanceof double[]) {
			double[] input = (double[]) obj;
			this.addIntArrayProperties(Spec.LIST_FLOAT64_TYPE, input.length);
			this.bos.write(Utils.getBytesFromDoubleArray(input));
		} 
	}
	
	private void addIntArrayProperties(int type, int length) throws IOException { 
        this.addType(type);
        this.addLength(length);
	}

	private void addStringList(CStringList obj) throws IOException {
        this.addType(Spec.LIST_STRING_TYPE);
        this.addLength(obj.lengthInBytes());
        this.bos.write(obj.toBytes());
	}

	private void addMap(Map obj) throws IOException, TsonError {
		this.addType(Spec.MAP_TYPE);
		this.addLength(obj.size());

		for (Object key : obj.keySet()) {
            if (!(key instanceof String)) {
                throw new TsonError("Map key must be a String.");
            }
            this.addObject(key);
            this.addObject(obj.get(key));
        }
	}

	public byte[] getBytes() {
		return this.bos.toByteArray();
	}
}
