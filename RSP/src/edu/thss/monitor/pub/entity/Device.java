package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 设备表
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_Device")
public class Device implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public Device() {
		this.plt_id = IDGenerator.getPltID();
	}

	/**
	 * MRO平台特殊需要的属性，对用户不可见
	 */
	@SuppressWarnings("unused")
	@Column(name = "plt_id", columnDefinition = "NVARCHAR2(50)", nullable = false)
	private String plt_id;

	/**
	 * oid
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "edu.thss.monitor.pub.util.UUIDGenerator")
	@Column(name = "plt_oid", columnDefinition = "RAW(32)")
	private String oid;

	/**
	 * 设备编号
	 */
	@Column(name = "plt_deviceNO", columnDefinition = "NVARCHAR2(50)")
	private String deviceID;

	/**
	 * 设备系列编号
	 */
	@ManyToOne
	@JoinColumn(name = "plt_deviceSeriesID")
	@LazyCollection(LazyCollectionOption.FALSE)
	@NotFound(action=NotFoundAction.IGNORE)
	private DeviceSeries deviceSeries;

	public String getOid() {
		return oid;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public DeviceSeries getDeviceSeries() {
		return deviceSeries;
	}

	public void setDeviceSeries(DeviceSeries deviceSeries) {
		this.deviceSeries = deviceSeries;
	}

}
