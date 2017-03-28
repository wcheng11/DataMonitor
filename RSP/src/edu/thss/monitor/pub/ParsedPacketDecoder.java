package edu.thss.monitor.pub;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import edu.thss.monitor.pub.entity.service.TransPacket;

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