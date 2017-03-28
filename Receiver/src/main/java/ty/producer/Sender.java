package ty.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import ty.pub.BeanUtil;
import ty.pub.RawDataPacket;

public class Sender extends Thread{
	
	private KafkaProducer<String, byte[]> producer;

	String topic;
	
	int index;
	
	boolean isSet = false;
	
	RawDataPacket packet;
	
	public Sender(int index, String topic, Properties props){
		this.index = index;
		this.topic = topic;
		producer = new KafkaProducer<String, byte[]>(props);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			RawDataPacket packet = PacketProducer.caches.poll();			
			if(packet != null){
				producer.send(new ProducerRecord<String, byte[]>(topic, BeanUtil.getByteForRawPacket(packet)));			
			}else{
				producer.flush();
			}
		}			
	}
	
	protected void finalize(){
		producer.flush();
	}

}
