package edu.thss.monitor.pub.entity.service;

import java.io.Serializable;
import java.util.Date;

import edu.thss.monitor.pub.entity.Device;

/**
 * 单条工况数据的封装对象
 * 
 * @author zhuangxy 2012-12-30
 */
@SuppressWarnings("serial")
public class WorkStatusData implements Serializable {

	public WorkStatusData() {
	}

	/**
	 * 所属设备
	 */
	private Device device;

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
	private Date timestamp;

	/**
	 * 流水号
	 */
	private String serialNo;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
