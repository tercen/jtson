package tercen.tson;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Test;

public class SerializerDeSerializerTest {

	@Test
	public void test() {
		try {
			LinkedHashMap<String, Object> input = TestUtils.createInputMap();
			byte[] result = jtson.encodeTSON(input);
			Object decodeOutput = jtson.decodeTSON(result);

			Assert.assertEquals(input.getClass(), decodeOutput.getClass());
			LinkedHashMap<String, Object> output = (LinkedHashMap<String, Object>) decodeOutput;

			//basic type
			Assert.assertEquals(input.get("null"), output.get("null"));
			Assert.assertEquals(input.get("string"), output.get("string"));
			Assert.assertEquals(input.get("integer"), output.get("integer"));
			Assert.assertEquals(input.get("float"), output.get("float"));
			Assert.assertEquals(input.get("bool_t"), output.get("bool_t"));
			Assert.assertEquals(input.get("bool_f"), output.get("bool_f"));
			Assert.assertEquals(input.get("map"), output.get("map"));
			Assert.assertEquals(input.get("list"), output.get("list"));
			
			// special lists
			Assert.assertTrue(Arrays.equals((byte[]) input.get("int8"), (byte[]) output.get("int8")));
			Assert.assertTrue(Arrays.equals((short[]) input.get("int16"), (short[]) output.get("int16")));
			Assert.assertTrue(Arrays.equals((int[]) input.get("int32"), (int[]) output.get("int32")));
			Assert.assertTrue(Arrays.equals((long[]) input.get("int64"), (long[]) output.get("int64")));
			Assert.assertTrue(Arrays.equals((float[]) input.get("float32"), (float[]) output.get("float32")));
			Assert.assertTrue(Arrays.equals((double[]) input.get("float64"), (double[]) output.get("float64")));
			
			Assert.assertEquals(input.get("cstringlist"), output.get("cstringlist"));
		} catch (Exception e) {
			Assert.fail("SerializerDeSerializerTest error:" + e.getMessage());
		}
	}
}
