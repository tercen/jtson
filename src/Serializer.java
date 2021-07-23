import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class Serializer {
	
	private ByteArrayOutputStream bos;
	private byte[] bytes;
	
	public Serializer(Object obj) {
		this.bos = new ByteArrayOutputStream();
		this.addString(Spec.TSON_SPEC_VERSION);
		try {
			this.addObject(obj);
		} catch (TsonError e) {
			e.printStackTrace();
		}
	}
	
	private void addObject(Object obj) throws TsonError {

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
			this.addBoolean((String) obj);
		} else if (obj instanceof List) {
			this.addList((String) obj);
		} else if (obj instanceof Map) {
			this.addMap((String) obj);
		} else {
          throw new TsonError("Unknown object type.");
		}
	}

	private void addNull() {
	}
	
	private void addType(int stringType) {
	}


	private void addString(String obj) {
        this.addType(Spec.STRING_TYPE);
        //this.bos.write(struct.pack("{0}s".format(len(obj)), obj.encode("utf-8")));
        this.addNull();
	}
	
	private void addInteger(Integer obj) {
	}
	
	private void addDouble(Double obj) {
	}
	
	private void addBoolean(String obj) {
	}
	
	private void addList(String obj) {
	}
	
	private void addMap(String obj) {
	}
	
	public byte[] getBytes() {
        return bytes;
	}
}
