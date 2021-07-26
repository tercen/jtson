import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SerializerDeSerializerTest {

	@Test
	public void test() {
		try {
			Map input = TestUtils.createInputMap();
			byte[] result = jtson.encodeTSON(input);
			Object decodeOutput = jtson.decodeTSON(result);
			
			
			Assert.assertEquals(input.getClass(), decodeOutput.getClass());
			Map output = (Map) decodeOutput;
			Assert.assertEquals(input.get("tercen"), output.get("tercen"));
			
			System.out.print(result);
		} catch (Exception e) {
			System.out.print("Cannot create empty list object");
		}
	}
}
