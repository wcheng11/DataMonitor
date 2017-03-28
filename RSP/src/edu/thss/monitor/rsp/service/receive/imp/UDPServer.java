package edu.thss.monitor.rsp.service.receive.imp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * UDP接收服务器
 * @author yangtao
 */
public class UDPServer implements Runnable{
	
	/**
	 * 端口号
	 */
//	private static int PORT = 5000;
	private int PORT = 5000;
	
	/**
	 * 队列大小
	 */
	private static int QUEUE_SIZE = 10000000;
	
	/**
	 * 接收数据包的最大字节数
	 */
	private static int MAX_PACKET_SIZE = 1024;
	
	/**
	 * Socket
	 */
	private DatagramSocket dataSocket;
	
	/**
	 * 报文对象
	 */
	private DatagramPacket dataPacket;
	
//	public static LinkedBlockingQueue<byte[]> queue = new LinkedBlockingQueue<byte[]>(QUEUE_SIZE);
	
	/**
	 * 接收数据队列(ArrayBlockingQueue是线程安全队列)
	 */
	public static ArrayBlockingQueue<RawDataPacket> rawDataQueue = new ArrayBlockingQueue<RawDataPacket>(QUEUE_SIZE);
	
	public UDPServer(int port) {
		PORT = port;
	}

	public void listen() throws RSPException {
		try {
			dataSocket = new DatagramSocket(PORT);
			byte[] receiveByte = new byte[MAX_PACKET_SIZE];
			dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
			int packetLen = 0; //数据报文长度
			while (packetLen == 0) {
				// 无数据，则循环
				dataSocket.receive(dataPacket);
				packetLen = dataPacket.getLength();
				// 接收数据
				if (packetLen > 0) {
					// 指定接收到数据的长度,可使接收数据正常显示,开始时很容易忽略这一点
					//从数据报文中取出数据内容
					byte[] data = new byte[packetLen];
					System.arraycopy(receiveByte,0,data,0,packetLen);
//					RawDataPacket rawDataPacket = new RawDataPacket(new Date(new Date().getTime() - 8*3600000),dataPacket.getAddress().getHostAddress(),"port:"+PORT,data);
//					rawDataQueue.offer(rawDataPacket);
					packetLen = 0;// 循环接收
				}
			}
		} catch (Exception e) {
			throw new RSPException("获取数据报文过程发生异常!",e);
		}finally{
			dataSocket.close();
		}
	}

	public static void main(String args[]) {
		UDPServer us = new UDPServer(5000);
		us.run();
	}

	@Override
	public void run() {
		//开始监听，发生异常退出时
		while(true){
			try {
				listen();
			} catch (RSPException e) {
				//出问题后不处理，记录日志并重新启动监听
			}
		}
//		logger.info("UDP接收服务器已关闭!");
	}
	
}