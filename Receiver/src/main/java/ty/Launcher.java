package ty;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.management.modelmbean.RequiredModelMBean;

import ty.producer.PacketProducer;
import ty.pub.RawPacketEncoder;
import ty.receiver.ReceiverFactory;

public class Launcher {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("receiver.props");
		props.load(in);
		in.close();
		launchOnlineReceiver(props);
	}
	
	public static void launchOnlineReceiver(Properties props){
		int port = Integer.parseInt(props.getProperty("port"));
		String source = props.getProperty("source");
//		props.setProperty("bootstrap.servers", "192.168.15.219:9092");
//		props.setProperty("acks", "0");
		props.setProperty("retries", "0");
		props.setProperty("batch.size", "16384");
		props.setProperty("linger.ms", "1");
		props.setProperty("buffer.memory", "33554432");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//		props.setProperty("producer.type", "async");
		PacketProducer producer = new PacketProducer(source, props, Integer.parseInt(props.getProperty("thread_num")));
		producer.setReceiver(ReceiverFactory.getOnlineReicever(port));
		Thread pThread = new Thread(producer);
		pThread.setName("OnlineReceiver");
		pThread.start();
	}

}
