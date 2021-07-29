package tercen.tson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class TestUtils {

	public static final String TEST_RESOURCES_DIR = "test/resources/";

	public static LinkedHashMap<String, Object> createInputMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("null", null);
		map.put("string", "hello");
		map.put("integer", 42);
		map.put("double", 42.0);
		map.put("bool_t", true);
		map.put("bool_f", false);

		// map
		LinkedHashMap<String, Object> subMap = new LinkedHashMap<String, Object>();
		subMap.put("string", "42");
		map.put("map", subMap);

		// normal list
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(42);
		list.add("42");
		list.add(subMap);
		list.add(false);
		map.put("list", list);

		// empty list
		ArrayList<Object> emptyList = new ArrayList<Object>();
		map.put("list", emptyList);

		// typed lists
		map.put("int8", new byte[] { -42, 42 });
		map.put("int16", new short[] { 42, 42 });
		map.put("int32", new int[] { 42, 42 });
		map.put("int64", new long[] { 42, 42 });
		map.put("float32", new float[] { 42.0f, 42.0f });
		map.put("float64", new double[] { 42.0, 42.0 });
		map.put("cstringlist", new CStringList(new ArrayList<>(Arrays.asList("42.0", "42"))));
		return map;
	}

	public static LinkedHashMap<String, Object> createUnsupportedInputMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("uint8", new char[] { 1, 1 });
		return map;
	}

}
