package edu.thss.monitor.pub;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.protobuf.InvalidProtocolBufferException;

import edu.thss.monitor.pub.entity.service.RawDataPacket;
import ty.pub.ProtoPacket.RawPacket;

public class RawPacketDecoder implements Deserializer<RawDataPacket>{

	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RawDataPacket deserialize(String arg0, byte[] bytes) {
		// TODO Auto-generated method stub
		RawDataPacket packet = null;
		try {
			RawPacket p = RawPacket.parseFrom(bytes);
			packet = new RawDataPacket(p.getTimestamp(), p.getIp(), p.getPacketSource(), p.getPacketData().toByteArray());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return packet;
	}

}
