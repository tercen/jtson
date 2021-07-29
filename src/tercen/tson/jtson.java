package tercen.tson;

import java.io.IOException;

public class jtson {

	public static byte[] encodeTSON(Object object) throws TsonError, IOException {
		return new Serializer(object).getBytes();
	}

	public static Object decodeTSON(byte[] bytes) throws TsonError, IOException {
		return new DeSerializer(bytes).getObject();
	}

}
