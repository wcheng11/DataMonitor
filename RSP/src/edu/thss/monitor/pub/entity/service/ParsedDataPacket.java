package edu.thss.monitor.pub.entity.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.TemplatePara;

/**
 * 报文解析后的数据封装对象
 * 
 * @author zhuangxy 2012-12-30
 */
@SuppressWarnings("serial")
public class ParsedDataPacket implements Serializable {

	public ParsedDataPacket() {
		
		this.baseInfoMap = new LinkedHashMap<String, String>();
		this.workStatusMap = new LinkedHashMap<String, String>();
	}

	/**
	 * 时间戳
	 */
	private long timestamp;

	/**
	 * 工况数据的发送设备
	 */
	private Device device;
	
	/**
	 * 协议模板的公有标识，用于进行设备识别
	 */
	private TemplatePara commonKey;
	
	/**
	 * 协议模板的唯一标识，用于进行设备识别
	 */
	private TemplatePara uniqueKey;
	
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

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
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

	public TemplatePara getCommonKey() {
		return commonKey;
	}

	public void setCommonKey(TemplatePara commonKey) {
		this.commonKey = commonKey;
	}

	public TemplatePara getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(TemplatePara uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public TransPacket getTransPacket(){
		TransPacket packet = new TransPacket();
		packet.setDeviceId(device.getDeviceID());
		packet.setIp(ip);
		packet.setBaseInfoMap(baseInfoMap);
		packet.setTimestamp(timestamp);
		packet.setWorkStatusMap(workStatusMap);
		return packet;
	}
	
}
