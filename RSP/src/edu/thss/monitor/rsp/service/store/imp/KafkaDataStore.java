package edu.thss.monitor.rsp.service.store.imp;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import edu.thss.monitor.pub.ParsedPacketEncoder;
import edu.thss.monitor.pub.Property;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.TransPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;
import edu.thss.monitor.rsp.service.store.IDataStore;

public class KafkaDataStore implements IDataStore{

	private KafkaProducer<String, TransPacket> producer;
	
	private String topic = Property.getProperty("store.topic");
	
	public KafkaDataStore() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", Property.getProperty("store.broker"));
		props.setProperty("acks", Property.getProperty("store.acks"));
		props.setProperty("retries", Property.getProperty("store.retries"));
		props.setProperty("batch.size", Property.getProperty("store.batch"));
		props.setProperty("linger.ms", Property.getProperty("store.linger.ms"));
		props.setProperty("buffer.memory", Property.getProperty("store.buffer"));
		props.setProperty("key.serializer", Property.getProperty("store.keySerial"));
		props.setProperty("value.serializer", ParsedPacketEncoder.class.getName());
		producer = new KafkaProducer<>(props);
	}
	
	@Override
	public void setCompanyDataDAO(ICompanyDataDAO companyDataDAO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData(ParsedDataPacket parsedDataPacket) throws RSPException {
		// TODO Auto-generated method stub
		TransPacket data = parsedDataPacket.getTransPacket();
		producer.send(new ProducerRecord<String, TransPacket>(topic, data), new Callback(){
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				if(exception != null){
					producer.send(new ProducerRecord<String, TransPacket>(topic, data));
				}
			}
		});
	}

	@Override
	public void setConfig(String param) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
