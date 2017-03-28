package edu.thss.monitor.base.logrecord.imp;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import edu.thss.monitor.base.logrecord.ILogRecord;

public class KafkaLogRecorder implements ILogRecord{

	Producer<String, String> producer;
	
	public KafkaLogRecorder() {
		// TODO Auto-generated constructor stub
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "192.168.15.214:9092");
		props.setProperty("acks", "all");
		props.setProperty("retries", "0");
		props.setProperty("batch.size", "16384");
		props.setProperty("linger.ms", "1");
		props.setProperty("buffer.memory", "33554432");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	//创建生产这对象
		producer = new KafkaProducer<String, String>(props);
	}
	
	@Override
	public boolean log(String logType, String log) {
		// TODO Auto-generated method stub
		producer.send(new ProducerRecord<String, String>(logType, String.valueOf(new Date().getTime()), log));
		producer.flush();
		return true;
	}

}
