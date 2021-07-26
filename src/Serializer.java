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
			//this.bos.write("\1".getBytes()); //start of header
			this.addString(Spec.TSON_SPEC_VERSION);
			this.addObject(obj);
		} catch (TsonError e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void addType(int type) throws UnsupportedEncodingException {
		bos.write(Conversion.getByteFromInt(type));
	}

	private void addLength(int length) throws IOException {
		bos.write(Conversion.getBytesFromInt(length));
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
		} else if (obj instanceof List) {
			this.addList((List) obj);
		} else if (obj instanceof Map) {
			this.addMap((Map) obj);
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
		bos.write(Conversion.getBytesFromInt(obj));
	}

	private void addDouble(Double obj) throws IOException {
		this.addType(Spec.DOUBLE_TYPE);
		//this.bos.write(String.valueOf("<d" + obj).getBytes());
		this.bos.write(String.valueOf(obj).getBytes());
	}

	private void addBoolean(Boolean obj) throws IOException {
		this.addType(Spec.BOOL_TYPE);
		//this.bos.write(String.valueOf("<B" + obj).getBytes());
		this.bos.write(String.valueOf(obj).getBytes());
	}

	private void addList(List obj) throws IOException, TsonError {
		this.addType(Spec.LIST_TYPE);
        this.addLength(obj.size());

        for (Object item : obj) {
        	this.addObject(item);
        }
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
