import java.util.ArrayList;

import org.junit.Test;

public class SerializerTest {

	@Test
	public void test_empty_list() {
		try {
			byte[] result = jtson.encodeTSON(new ArrayList());
			System.out.print(result);
		} catch (Exception e) {
			System.out.print("Cannot create empty list object");
		}
	}

}
