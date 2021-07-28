import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import tercen.tson.jtson;

public class SerializerDeSerializerTest {

	@Test
	public void test() {
		try {
			Map input = TestUtils.createInputMap();
			byte[] result = jtson.encodeTSON(input);
			Object decodeOutput = jtson.decodeTSON(result);
			
			Assert.assertEquals(input.getClass(), decodeOutput.getClass());
			Map output = (Map) decodeOutput;
			Assert.assertEquals(input, output);
			
			System.out.print(result);
		} catch (Exception e) {
			System.out.print("SerializerDeSerializerTest error:" + e.getMessage());
		}
	}
}
