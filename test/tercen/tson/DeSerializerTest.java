package tercen.tson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import tercen.tson.TsonError;
import tercen.tson.jtson;

public class DeSerializerTest {

	@Test
	public void testInputOutput() {
		File file = new File("test/test_data.tson");
		try {
			byte[] fileContent = Files.readAllBytes(file.toPath());
			Object object = jtson.decodeTSON(fileContent);
			System.out.println(object);

			InputStream testDataJson = new FileInputStream("test/test_data.json");
			Map<String,Object> result = new ObjectMapper().readValue(testDataJson, LinkedHashMap.class);
			Assert.assertEquals(result, object);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TsonError e) {
			e.printStackTrace();
		}
	}

}