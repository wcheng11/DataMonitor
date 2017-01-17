package edu.thss.monitor.pub.entity.service;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class RawDataPacket implements Serializable {
	
	/**
	 * 时间戳
	 */
	private Date timestamp;
	
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
	
	public RawDataPacket(Date timestamp, String ip,String packetSource, Object packetData) {
		super();
		this.timestamp = timestamp;
		this.ip = ip;
		this.packetSource = packetSource;
		this.packetData = packetData;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
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
