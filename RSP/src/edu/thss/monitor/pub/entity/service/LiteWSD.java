package edu.thss.monitor.pub.entity.service;

/**
 * 精简版工况数据
 */
public class LiteWSD {

	/**
	 * 工况：模板参数的parameterID
	 */
	private String workStatus;

	/**
	 * 工况值
	 */
	private String dataValue;

	/**
	 * 时间戳
	 */
	private long timestamp;

	/**
	 * 流水号
	 */
	private String serialNo;

	public LiteWSD(){}
	
	public LiteWSD(String workStatus, String dataValue,long timestamp, String serialNo) {
		this.workStatus = workStatus;
		this.dataValue = dataValue;
		this.timestamp = timestamp;
		this.serialNo = serialNo;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
