package tercen.tson;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

	public static final String TEST_RESOURCES_DIR = "test/resources/";
	
	public static Map createInputMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("null", null);
		map.put("string", "tercen");
		map.put("integer", 10);
		map.put("double", 3.0);
		map.put("boolean", true);
		map.put("boolean_f", false);

		Map<String, Object> subMap = new LinkedHashMap<>();
		subMap.put("double", 42.0);
		map.put("map", subMap);

		List<Object> list = new ArrayList<>();
		list.add("Tercen Platform");
		list.add(10);
		list.add(3.0);
		list.add(false);
		map.put("list", list);
		return map;
	}
}
