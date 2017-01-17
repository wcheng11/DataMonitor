package edu.thss.monitor.pub.entity.service;

import java.util.List;


/**
 * 给应用层的传输数据对象
 * @author yangtao
 */
public class PushData {

	/**
	 * 订阅方案ID
	 */
	private String subscribeId;

	/**
	 * 设备ID
	 */
	private String deviceId;

	/**
	 * 精简工况数据(无设备归属)列表
	 */
	private List<LiteWSD> dataLst;

	public PushData(){}
	
	public PushData(String subscribeId, String deviceId, List<LiteWSD> dataLst) {
		this.subscribeId = subscribeId;
		this.deviceId = deviceId;
		this.dataLst = dataLst;
	}

	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}

	public List<LiteWSD> getDataLst() {
		return dataLst;
	}

	public void setDataLst(List<LiteWSD> dataLst) {
		this.dataLst = dataLst;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
