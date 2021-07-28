package tercen.tson;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeSerializer {

	private Object object;
	private ByteArrayInputStream bis;

	public DeSerializer(byte[] bytes) throws TsonError, IOException {
		if (bytes == null) {
            throw new TsonError("Connection cannot be None.");
		}

        if (bytes.length == 0) {
        	throw new TsonError("Connection buffer is empty.");
        }
        this.bis = new ByteArrayInputStream(bytes);

        Object versionObject = readObject();
        if (!versionObject.getClass().getName().equals("java.lang.String")) {
        	throw new TsonError("wrong format -- expect version as str");
        }

        String version = (String) versionObject;
        if (!version.equals(Spec.TSON_SPEC_VERSION)) {
        	throw new TsonError(String.format(version, Spec.TSON_SPEC_VERSION));
        }

        this.object = readObject();
	}

	private int readType() throws IOException {
		return(this.bis.read());
	}

	private byte[] readNBytes(int n) {
		byte[] bytes = new byte[n];
		int offset = 0;
		while (n-offset > 0) {
			offset += this.bis.read(bytes, offset, n-offset);
		}

		return bytes;
	}

	private long readLength() throws TsonError, IOException {
		int len = Utils.getIntFromByteArray(this.readNBytes(4));

        if (len < 0)
        	throw new TsonError(String.format("Found invalid length %d", len));
        return len;
	}

    //Add object
    private Object readObject() throws IOException, TsonError {
        int type = readType();
        //System.out.println(String.format("Type: %d", type));
        if (type == Spec.NULL_TYPE) {
        	return(null);
        } else if (type == Spec.BOOL_TYPE) {
        	return(readBool());
        } else if (type == Spec.INTEGER_TYPE) {
        	return(readInteger());
        } else if (type == Spec.DOUBLE_TYPE) {
        	return(readDouble());
        } else if (type == Spec.STRING_TYPE) {
        	return(readString());
        } else if (type == Spec.LIST_TYPE) {
        	return(readList());
        } else if (type == Spec.MAP_TYPE) {
        	return(readMap());
        } else if (type == Spec.LIST_UINT8_TYPE) {
        	throw new TsonError("ReadObject: LIST_UINT8_TYPE not supported yet.");
        } else if (type == Spec.LIST_UINT16_TYPE) {
        	throw new TsonError("ReadObject: LIST_UINT16_TYPE not supported yet.");
        } else if (type == Spec.LIST_UINT32_TYPE) {
        	throw new TsonError("ReadObject: LIST_UINT32_TYPE not supported yet.");
        } else if (type == Spec.LIST_INT8_TYPE) {
        	return(readByteArray());
        } else if (type == Spec.LIST_INT16_TYPE) {
        	return(readShortArray());
        } else if (type == Spec.LIST_INT32_TYPE) {
        	return(readIntArray());
        } else if (type == Spec.LIST_INT64_TYPE) {
        	return(readLongArray());
        } else if (type == Spec.LIST_FLOAT32_TYPE) {
        	return(readFloatArray());
        } else if (type == Spec.LIST_FLOAT64_TYPE) {
        	return(readDoubleArray());
        } else if (type == Spec.LIST_STRING_TYPE) {
        	return(readCStringList());
        } else {
        	return(null);
        }
    }

    //Basic types (null, string, integer, double, bool)
    private String readString() throws IOException {
        StringBuffer result = new StringBuffer();
        int b = 0;
        while ((b = this.bis.read()) != -1) {
            if (b == 0) {
                break;
            }
            char ch = (char) b;
            result.append(ch);
        }
        return result.toString();
    }

    private int readInteger() throws IOException {
        return Utils.getIntFromByteArray(this. readNBytes(4));
    }

    private double readDouble() throws IOException {
    	return Utils.getDoubleFromByteArray(this. readNBytes(8));
    }

    private boolean readBool() throws IOException {
    	return Utils.getBooleanFromByteArray(this. readNBytes(1));
    }

    //Basic list
    private List<Object> readList() throws IOException, TsonError {
        long length = readLength();
        List<Object> result = new ArrayList<>();
        if (length > 0) {
        	for (int i = 0; i < length; i++) {
        		result.add(readObject());
        	}
        }
        return result;
    }

    //Basic map
    private Map<Object, Object> readMap() throws TsonError, IOException {
    	long length = readLength();
        Map<Object, Object> result = new LinkedHashMap<>();

        if (length > 0) {
        	for (int i = 0; i < length; i++) {
                Object key = readObject();
                if (!(key instanceof String)) {
                	System.out.println(key);
                    throw new TsonError("Key in map is not a string");
                }
                result.put(key, readObject());
            }
        }
        return result;
    }
    
    private byte[] readByteArray() throws TsonError, IOException {
        long length = readLength();
        return this.readNBytes((int) length);
    }
    
    private short[] readShortArray() throws TsonError, IOException {
        long length = readLength();
        return Utils.getShortArrayFromByteArray(this.readNBytes((int) length * 2));
    }
    
    private int[] readIntArray() throws TsonError, IOException {
        long length = readLength();
        return Utils.getIntArrayFromByteArray(this.readNBytes((int) length * 4));
    }
    
    private long[] readLongArray() throws TsonError, IOException {
        long length = readLength();
        return Utils.getLongArrayFromByteArray(this.readNBytes((int) length * 8));
    }
    
    private float[] readFloatArray() throws TsonError, IOException {
        long length = readLength();
        return Utils.getFloatArrayFromByteArray(this.readNBytes((int) length * 4));
    }
    
    private double[] readDoubleArray() throws TsonError, IOException {
        long length = readLength();
        return Utils.getDoubleArrayFromByteArray(this.readNBytes((int) length * 8));
    }
    
    private CStringList readCStringList() throws TsonError, IOException {
    	long length = readLength();
    	return new CStringList(this.readNBytes((int) length));
    }
	
	public Object getObject() {
        return object;
	}
}
