package edu.thss.monitor.base.timerecord.imp;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import edu.thss.monitor.base.timerecord.ITimeRecorder;
import edu.thss.monitor.pub.Property;

public class TimeRecorder implements ITimeRecorder{

	int i = 100;
	
	String topic = "time-Log";
	
	String times_topic = "times";
	
	String log_topic = "data-log";
	
	Producer<String, String> producer;
	
	public TimeRecorder() {
		// TODO Auto-generated constructor stub
		Properties props = new Properties();
//		try {
//			
//			File file = new File(this.getClass().getResource("/META-INF/time-log.properties").getPath());
//			System.out.println("--FILE_PATH--" + file.getPath());
//			FileInputStream in = new FileInputStream(file);
//			props.load(in);
//			in.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		props.setProperty("bootstrap.servers", Property.getProperty("log.broker"));
		props.setProperty("acks", Property.getProperty("log.acks"));
		props.setProperty("retries", Property.getProperty("log.retries"));
		props.setProperty("batch.size", Property.getProperty("log.batch"));
		props.setProperty("linger.ms", Property.getProperty("log.linger.ms"));
		props.setProperty("buffer.memory", Property.getProperty("log.buffer"));
		props.setProperty("key.serializer", Property.getProperty("log.keySerial"));
		props.setProperty("value.serializer", Property.getProperty("log.valueSerial"));
    	//创建生产这对象
		producer = new KafkaProducer<String, String>(props);
	}
	
	@Override
	public void logTime(String id, String process, long start, long end) {
		// TODO Auto-generated method stub
//		System.out.println(i+":"+id+"-"+process+"-"+start+"-"+end);
		producer.send(new ProducerRecord<String, String>(topic, "" + new Date().getTime(),id+"-"+process+"-"+start+"-"+end));
		producer.flush();
	}

	@Override
	public void flushTimes(String id, String times) {
		// TODO Auto-generated method stub 
		producer.send(new ProducerRecord<String, String>(times_topic, "" + new Date().getTime(),times.substring(0, times.length()-1)));
		producer.flush();
	}

	@Override
	public void log(String log) {
		// TODO Auto-generated method stub
		producer.send(new ProducerRecord<String, String>(log_topic, "" + new Date().getTime(),log));
		producer.flush();
	}

	
	
}
