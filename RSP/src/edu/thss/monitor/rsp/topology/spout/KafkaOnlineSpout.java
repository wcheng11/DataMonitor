package edu.thss.monitor.rsp.topology.spout;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.timerecord.TimeLogger;
import edu.thss.monitor.kafka.SerializableConsumer;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.Property;
import edu.thss.monitor.pub.RawPacketDecoder;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.receive.IReceivable;
import edu.thss.monitor.rsp.topology.ComponentId;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableSpout;

public class KafkaOnlineSpout extends ObservableSpout implements IReceivable {

	private static final long serialVersionUID = -4295899123976695527L;

	SpoutOutputCollector _collector;

	// port为接收器的服务端口
	private String topic;
	SerializableConsumer<String, RawDataPacket> consumer;
	
	int i = 0;
	
	public KafkaOnlineSpout(String topic) {
		super();
		this.topic = topic;
	}

	// 数据缓冲为空时休眠时间间隔
	// private Integer receiveInterval = 50; //单位毫秒

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 测试用

	@Override
	public void nextTuple() {
		while(true)
			receive();// 接收
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 初始化Spout
	 */
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		_collector = collector;
		AppContext.getSpringContext();
		LogRecord.info(LogConstant.LOG_FLAG_RECEIVE + "在线接收Spout初始化... ");
		if(consumer == null){
			Properties props = new Properties();
			props.setProperty("bootstrap.servers", Property.getProperty("spout.broker"));
			props.setProperty("group.id", Property.getProperty("spout.groupId"));
			props.setProperty("enable.auto.commit", Property.getProperty("spout.enable.auto.commit"));
			props.setProperty("auto.commit.interval.ms", Property.getProperty("spout.auto.commit.interval.ms"));
			props.setProperty("key.deserializer", Property.getProperty("spout.key.deserializer"));
			props.setProperty("value.deserializer", RawPacketDecoder.class.getName());
			consumer = new SerializableConsumer<>(props);
			consumer.subscribe(Arrays.asList(topic));
		}
		// 加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId() + "_" + context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 发送数据格式
		declarer.declare(new Fields("data", "times"));
	}

	/**
	 * 接收
	 */
	@Override
	public void receive() {

		/*********************************** 使用接收器 *********************************/
		// 调用服务的 getRawDataPacket2方法获取数据
		// STEP1：从接收器的数据队列中取出一条
		ConsumerRecords<String, RawDataPacket> records = consumer.poll(100);
		for (ConsumerRecord<String, RawDataPacket> record : records) {
			RawDataPacket packet = record.value();
			
			Date start = new Date();

			String times = "" + ComponentId.ONLINE_SPOUT + ":" + start.getTime() + "-";
			packet.setTimestamp(record.timestamp());
			_collector.emit(new Values(packet, times), record.partition() + "-" +record.offset());
			Date end = new Date();
			TimeLogger.recordTime("0", ComponentId.ONLINE_SPOUT, start.getTime(), end.getTime());
			i++;
	        if(i%100==0){
	        	super.count(i);
	        	i=0;
	        }
		}
	}
	
	@Override  
    public void ack(Object msgId) {  
//		TimeLogger.log((int)msgId + "");
    }  
  
    @Override  
    public void fail(Object msgId) {
    	TimeLogger.log((int)msgId + "");
    }  

}
