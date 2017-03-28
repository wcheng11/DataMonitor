package ty.pub;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
/**
 * 解析后数据包解码器
 * @author ZWX
 *
 */
public class ParsedPacketDecoder implements Deserializer<TransPacket>{

	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransPacket deserialize(String arg0, byte[] bytes) {
		// TODO Auto-generated method stub
		return BeanUtil.toObject(bytes, TransPacket.class);
	}

}