package edu.thss.monitor.pub.entity.service;

import java.io.Serializable;
import java.util.Map;

public class TransPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1021437932787366779L;

	/**
	 * 时间戳
	 */
	private long timestamp;

	/**
	 * 工况数据的发送设备
	 */
	private String deviceId;
	
	
	/**
	 * 数据来源ip
	 */
	private String ip;
	
	/**
	 * 数据报文基础信息
	 * key = 模板参数（TemplatePara）的参数编号parameterID
	 * value = 模板参数对应的数值
	 */
	private Map<String, String> baseInfoMap;

	/**
	 * 工况数据信息
	 * key = 模板参数（TemplatePara）的参数编号parameterID
	 * value = 模板参数对应的数值
	 */
	private Map<String, String> workStatusMap;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map<String, String> getBaseInfoMap() {
		return baseInfoMap;
	}

	public void setBaseInfoMap(Map<String, String> baseInfoMap) {
		this.baseInfoMap = baseInfoMap;
	}

	public Map<String, String> getWorkStatusMap() {
		return workStatusMap;
	}

	public void setWorkStatusMap(Map<String, String> workStatusMap) {
		this.workStatusMap = workStatusMap;
	}
	
}
