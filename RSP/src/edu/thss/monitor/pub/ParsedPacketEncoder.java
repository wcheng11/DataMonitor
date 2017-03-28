package edu.thss.monitor.pub;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import edu.thss.monitor.pub.entity.service.TransPacket;

public class ParsedPacketEncoder implements Serializer<TransPacket>{

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serialize(String topic, TransPacket data) {
		// TODO Auto-generated method stub
		return BeanUtil.toByteArray(data);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
