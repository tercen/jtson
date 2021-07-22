import org.apache.commons.lang3.SerializationUtils;

public class DeSerializer {
	
	private Object object;
	
	public DeSerializer(byte[] bytes) {
		object = SerializationUtils.deserialize(bytes);
	}
	
	public Object getObject() {
        return object;
	}

}
