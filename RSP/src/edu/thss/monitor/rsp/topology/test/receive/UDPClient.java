package edu.thss.monitor.rsp.topology.test.receive;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.thss.monitor.base.logrecord.imp.LogRecord;

public class UDPClient {
	private static final int PORT = 5021;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
	private byte sendDataByte[];
	private String sendStr ="test";
	
	private InetAddress addressName;
	
	public UDPClient() {
		Init();

	}

	public void Init() {
		try {
			// 指定端口号，避免与其他应用程序发生冲突  
			dataSocket = new DatagramSocket(PORT + 1);
			sendDataByte = new byte[1024];
			addressName = InetAddress.getByName("192.168.10.253");
//			addressName = InetAddress.getByName("localhost");// 得到目标机器的地址实例(本机实例)
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void send(){
		try {
//			sendStr = scpG.getData().toString();
			sendDataByte = sendStr.getBytes();
//			System.out.println(TypeTransUtil.getBytesString(sendDataByte));
			
			dataPacket = new DatagramPacket(sendDataByte, sendDataByte.length,
					addressName, PORT);
			dataSocket.send(dataPacket);
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
		LogRecord.info("test................");
		UDPClient client = new UDPClient();
		long timeTotal = 0;
		//测试发送速率：每秒发送10W左右的数据包（每个包260字节）
		//测试次数，每次平均发送时间如下：1W——93ms;10W——989ms;100W——10423ms
		int testNum = 1;
		for(int testNo=0;testNo<testNum;testNo++){
			long start1 = System.currentTimeMillis();
			for(int i=0;i<100;i++){
				//DO Something	sleep 10不会丢包，sleep 5 时候10W丢了700包
				client.send();
//				if(i!=0&&i%10==0){
//					try {
//						Thread.sleep(1);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			}
			long end1 = System.currentTimeMillis();   
			timeTotal += (end1 - start1);
			System.out.println("用时：" + (end1 - start1)+"毫秒");
		}
		System.out.println("总用时：" + timeTotal+"毫秒");
		System.out.println("平均用时：" + (timeTotal/testNum)+"毫秒");
	}
	
}
