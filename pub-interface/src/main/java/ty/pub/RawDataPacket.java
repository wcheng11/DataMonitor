package ty.pub;

import java.io.Serializable;

/**
 * 原始数据包
 * @author ZWX
 *
 */
public class RawDataPacket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -488894059909768006L;

	/**
	 * 时间戳
	 */
	private long timestamp;
	
	/**
	 * 数据来源
	 */
	private String packetSource;
	
	/**
	 * 数据来源ip地址
	 */
	private String ip;
	

	/**
	 * 未解析数据报文的数据
	 */
	private Object packetData;
	
	public RawDataPacket(){}
	
	public RawDataPacket(long timestamp, String ip,String packetSource, byte[] packetData) {
		super();
		this.timestamp = timestamp;
		this.ip = ip;
		this.packetSource = packetSource;
		this.packetData = packetData;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPacketSource() {
		return packetSource;
	}

	public void setPacketSource(String packetSource) {
		this.packetSource = packetSource;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Object getPacketData() {
		return packetData;
	}

	public void setPacketData(Object packetData) {
		this.packetData = packetData;
	}

}
