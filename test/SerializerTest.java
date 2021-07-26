import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Test
	public void testInputOutput() {
		File file = new File("test/test_data.json");
		try {
			InputStream testDataJson = new FileInputStream(file);
			Map<String,Object> object = new ObjectMapper().readValue(testDataJson, LinkedHashMap.class);
			byte[] result = jtson.encodeTSON(object);
			byte[] output = Files.readAllBytes(new File("test/test_data.tson").toPath());
			
			Assert.assertTrue(result.length == output.length);
			Assert.assertTrue(Arrays.equals(result, output));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
