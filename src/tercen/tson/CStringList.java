package tercen.tson;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CStringList extends ArrayList<String> {
	
	private byte[] bytes = null;
	private int[] starts = null;
	
	public CStringList(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public CStringList(List<String> list) {
		int lengthInBytes = list.stream().mapToInt(str -> {
		      if (str == null) throw new RuntimeException("Null values are not allowed.");
		      return str.getBytes(StandardCharsets.UTF_8).length + 1;
		    }).sum();
		ByteBuffer buffer = ByteBuffer.allocate(lengthInBytes).order(ByteOrder.LITTLE_ENDIAN);
	    list.forEach(str -> {
	      byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
	      buffer.put(bytes);
	      buffer.put(Utils.getByteFromInt(0));
	    });
	    this.bytes = buffer.array();
	}
	
	private int[] buildStarts() {
		int length = 0;
	    for (int i = 0; i < this.bytes.length; i++) {
	      if (this.bytes[i] == 0) 
	    	  length++;
	    }
	    this.starts = new int[length + 1];
	    this.starts[0] = 0;
	    int offset = 0;

	    for (int i = 0; i < length; i++) {
	      int start = offset;
	      while (this.bytes[offset] != 0) 
	    	  offset++;
	      offset += 1;
	      this.starts[i + 1] = this.starts[i] + (offset - start);
	    }
	    return this.starts;
	}
	
	private int[] getStarts() {
		return (this.starts == null) ? this.buildStarts() : this.starts;
	}
		
	@Override
	public int size() {
		return getStarts().length - 1;
	}
	
	@Override
	public String get(int i) {
		int start = getStarts()[i];
	    int end = getStarts()[i + 1];
	    return new String(Arrays.copyOfRange(this.bytes, start, end), StandardCharsets.UTF_8);
	}

	public byte[] toBytes() {
		return this.bytes;
	}
	
	public int lengthInBytes() {
		return this.bytes.length;
	}
}
