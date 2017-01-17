package edu.thss.monitor.rsp.topology.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.logging.LogFactory;

public class TestSendData {

//	private static final String IP = "localhost";
//	private static final String IP = "192.168.10.22";
	private static final String IP = "166.111.80.211";
	private static final int PORT = 5050;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
//	private byte sendDataByte[];
//	private String sendStr ="test";
	
	private byte[] sendDataByte = { 
			0x30, 0x30, 0x4, 0, 1, 0x4b, 0x19, 0x5a, 0x1f, 0x41, 0x35, 0x1a, 
			0x38, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8, 
			0x1e, 0x17, 0x1f, 0x1c, 9, 10, 11, 12, 13, 14, 15, 16,
			0x14, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8, 
			0x3e, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//			0x01, 0x17, 0x1f, 0x1c, 9, 10, 11, 12, 13, 14, 15, 16,
//			0x31, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//			0x16, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//			0x1a, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//			0x1b, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//			(byte)0x80, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//			0x11, 0x60, 0x1f, 0x1c, 1, 1, 0, 0, 2, 0, 0, 0,
//			0x12, 0x60, 0x1f, 0x1c, 2, 1, 0, 0, 1, 0, 0, 0,
			0x17, 0x17, 0x1f, 0x1c, 1, 1, 0, 0, 1, 1, 0, 0,
//			0x1f, 0x60, 0x1f, 0x1c, 0x55, 0, 0, 0, 0, 0, 0, 0,
//			0x0a, 0x60, 0x1f, 0x1c, 0x53, 0x00, 0, 1, 0x21, 0x05, 0x32, 0x49, 
//			0x0b, 0x60, 0x1f, 0x1c, 0x20, 0x13, 0x10, 0x52, 0x1f, 0x1c, 0x01, 0, 
//			0x0c, 0x60, 0x1f, 0x1c, 0, 0, 0, 0, 0, 0, (byte)0xc6, 0x01,
			(byte) 0xce, 0x2 };
	
	private InetAddress addressName;
	
	public TestSendData() {
		Init();
	}

	public void Init() {
		try {
			// 指定端口号，避免与其他应用程序发生冲突  
			dataSocket = new DatagramSocket(PORT + 1);
			addressName = InetAddress.getByName(IP);
//			sendDataByte = new byte[1024];
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
//			sendDataByte = sendStr.getBytes();
//			System.out.println(TypeTransUtil.getBytesString(sendDataByte));
			dataPacket = new DatagramPacket(sendDataByte, sendDataByte.length,
					addressName, PORT);
			dataSocket.send(dataPacket);
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
		LogFactory.getLog(TestSendData.class).info("test................");
		TestSendData client = new TestSendData();
		long timeTotal = 0;
		//测试发送速率：每秒发送10W左右的数据包（每个包260字节）
		//测试次数，每次平均发送时间如下：1W——93ms;10W——989ms;100W——10423ms
		int testNum = 1;
		for(int testNo=0;testNo<testNum;testNo++){
			long start1 = System.currentTimeMillis();
			for(int i=0;i<3000000;i++){
				//DO Something	sleep 10不会丢包，sleep 5 时候10W丢了700包
				client.send();
				if(i!=0&&i%200==0){  //200Sleep5时每秒1.5W,100Sleep5时每秒7K左右,无Sleep时最高4.5W
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			long end1 = System.currentTimeMillis();   
			timeTotal += (end1 - start1);
			System.out.println("用时：" + (end1 - start1)+"毫秒");
		}
		System.out.println("总用时：" + timeTotal+"毫秒");
		System.out.println("平均用时：" + (timeTotal/testNum)+"毫秒");
	}
	
}
