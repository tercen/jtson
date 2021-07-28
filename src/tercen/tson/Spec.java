package tercen.tson;

public final class Spec {

	public static final String TSON_SPEC_VERSION = "1.1.0";

	public static final int TYPE_LENGTH_IN_BYTES = 1;
	public static final int NULL_TERMINATED_LENGTH_IN_BYTES = 1;
	public static final int ELEMENT_LENGTH_IN_BYTES = 4;

	public static final int NULL_TYPE = 0;
	public static final int STRING_TYPE = 1;
	public static final int INTEGER_TYPE = 2;
	public static final int DOUBLE_TYPE = 3;
	public static final int BOOL_TYPE = 4;

	public static final int LIST_TYPE = 10;
	public static final int MAP_TYPE = 11;

	public static final int LIST_UINT8_TYPE = 100;
	public static final int LIST_UINT16_TYPE = 101;
	public static final int LIST_UINT32_TYPE = 102;

	public static final int LIST_INT8_TYPE = 103;
	public static final int LIST_INT16_TYPE = 104;
	public static final int LIST_INT32_TYPE = 105;
	public static final int LIST_INT64_TYPE = 106;

	public static final int LIST_FLOAT32_TYPE = 110;
	public static final int LIST_FLOAT64_TYPE = 111;

	public static final int LIST_STRING_TYPE = 112;
}
