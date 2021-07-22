import org.junit.Assert;
import org.junit.Test;

public class SerializerDeSerializerTest {

	@Test
	public void test() {
		try {
			Person input = new Person("Tercen");
			byte[] result = jtson.encodeTSON(new Person("Tercen"));
			Object decodeOutput = jtson.decodeTSON(result);
			
			
			Assert.assertEquals(input.getClass(), decodeOutput.getClass());
			Person output = (Person) decodeOutput;
			Assert.assertEquals(input.getName(), output.getName());
			
			System.out.print(result);
		} catch (Exception e) {
			System.out.print("Cannot create empty list object");
		}
	}
}
