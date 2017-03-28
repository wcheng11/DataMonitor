package edu.thss.monitor.kafka;

import java.io.Serializable;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class SerializableConsumer<K, V> extends KafkaConsumer<K, V> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerializableConsumer(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

}
