package ty.pub;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

public class RawPacketEncoder implements Serializer<RawDataPacket>{


	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void configure(Map<String, ?> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	public byte[] serialize(String topic, RawDataPacket t) {
		// TODO Auto-generated method stub
		return BeanUtil.toByteArray(t);
	}

}
