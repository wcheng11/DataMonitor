package ty.producer;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import ty.pub.RawDataPacket;
import ty.receiver.Receiver;

public class PacketProducer implements Runnable{

	Receiver receiver;

	int threadNums = 10;
	
	static LinkedBlockingQueue<RawDataPacket> caches = new LinkedBlockingQueue<RawDataPacket>(300000);
	int i = 0;
	Sender [] senders;
	int k = 0;
	public PacketProducer(String topic, Properties props, int threadNums){
		this.threadNums = threadNums;
		senders = new Sender[threadNums];
		for(int i = 0;i<threadNums;i++){
			senders[i] = new Sender(i, topic, props);
			senders[i].setName("Sender" + i);
			
		}	
		for(int i = 0;i<threadNums;i++){
			senders[i].start();
		}
	}
	
	public void setReceiver(Receiver re){
		receiver = re;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
			try {
				receive();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private boolean receive() throws InterruptedException {
		RawDataPacket packet = receiver.next();
		if(packet != null){
//			if(i++ % 1000 == 0 || (i>49000 && i%100==0))
//				System.out.println("num:" + i);
			caches.offer(packet);
			return true;
		}else{
			i++;
			System.out.println("接收到的空包数量:" + i);
		}
		return false;
	}
	
	protected void finalize(){
		for(int i = 0;i<threadNums;i++){
			senders[i].interrupt();
		}
	}
	
}
