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
        	throw new TsonError("TSON version mismatch, found: %s, expected : %s".format(version, Spec.TSON_SPEC_VERSION));
        }
                
        this.object = readObject();
	}
	
	private int readType() throws IOException {
		return(this.bis.read());
	}
	
	byte[] readNBytes(int n) {
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
        List<Object> result = new ArrayList<Object>(); 
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
        Map<Object, Object> result = new LinkedHashMap<Object, Object>();

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
	
	public Object getObject() {
        return object;
	}
}
