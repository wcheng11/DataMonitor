package ty.receiver.imp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Date;

import ty.pub.RawDataPacket;

public class UDPServer {

	/**
	 * 端口号
	 */
	private int PORT = 5050;
	int i = 0;
	/**
	 * 每间隔LOG_INTERms写入一次统计日志
	 */
	private static int LOG_INTER = 10000;
	private long ltime = 0;

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
	
	
	byte[] receiveByte = new byte[MAX_PACKET_SIZE];
	
	
	
	public UDPServer(int port) {
		PORT = port;
		ltime = System.currentTimeMillis();
		try {
			dataSocket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
	}

	public RawDataPacket listen(){
		try {			
			int packetLen = 0; // 数据报文长度

			while (true) {
				if (i%1000==0) {
					System.out.println("获得的rawDataPacket数量为: " + i);
					ltime = System.currentTimeMillis();
				}
				// 无数据，则循环
				dataSocket.receive(dataPacket);
				packetLen = dataPacket.getLength();
				// 接收数据
				if (packetLen > 0) {
					i++;					
					// 指定接收到数据的长度,可使接收数据正常显示,开始时很容易忽略这一点
					// 从数据报文中取出数据内容
					byte[] data = new byte[packetLen];
//					ByteBuffer dataByteBuffer = ByteBuffer.wrap(data);
					System.arraycopy(receiveByte, 0, data, 0, packetLen);
					return new RawDataPacket(new Date().getTime(),dataPacket.getAddress().getHostAddress(), "port:"+PORT, data);	
				}
			}
		} catch (Exception e) {
//			System.out.println("获取数据报文过程发生异常!" + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		} finally {
			
		}
		return null;
	}
	
	protected void finalize(){
		dataSocket.close();
	}
}
