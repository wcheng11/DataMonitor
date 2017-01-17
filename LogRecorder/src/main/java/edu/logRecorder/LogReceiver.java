package edu.logRecorder;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class LogReceiver implements Runnable{
	
	KafkaConsumer<String, String> consumer;

	ILogRecorder recorder;
	
	public LogReceiver(Properties props){
		consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList("time-Log"));
	}
	
	public void setRecorder(ILogRecorder recorder){
		this.recorder = recorder;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records){
				recorder.log(record.value());
			}	
		}
	}

}
