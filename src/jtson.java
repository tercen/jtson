
public class jtson {
	
	public static byte[] encodeTSON(Object object) {
	    return new Serializer(object).getBytes();
	}	

	public static Object decodeTSON(byte[] bytes) {
	    return new DeSerializer(bytes).getObject();
	}

}
