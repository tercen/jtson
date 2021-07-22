import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public class Serializer {
	
	private byte[] bytes;
	
	public Serializer(Object obj) {
		bytes = SerializationUtils.serialize((Serializable) obj);
	}
	
	public byte[] getBytes() {
        return bytes;
	}

}
