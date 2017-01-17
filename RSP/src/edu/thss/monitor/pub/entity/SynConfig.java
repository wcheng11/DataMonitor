package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.util.IDGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_SynConfig")
public class SynConfig implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public SynConfig() {
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
	 * 同步件类型 1=唯一标识 2=公有标志 3=其它标识
	 */
	@Column(name = "plt_syncComponentType", columnDefinition = "NUMBER(2)")
	private Integer syncComponentType;

	/**
	 * 同步件类型主码
	 */
	@Column(name = "plt_syncCompTypeMC", columnDefinition = "NUMBER(2) DEFAULT 7")
	private Integer syncCompTypeMC;

	/**
	 * 设备系列编号
	 */
	@ManyToOne
	@JoinColumn(name = "plt_deviceSeriesID")
	@NotFound(action=NotFoundAction.IGNORE)
	private DeviceSeries deviceSeries;

	/**
	 * 同步件ID。即，模板参数表主键ID。
	 */
	@ManyToOne
	@JoinColumn(name = "plt_synComponentID")
	@NotFound(action=NotFoundAction.IGNORE)
	private TemplatePara synComponent;

	public Integer getSyncComponentType() {
		return syncComponentType;
	}

	public void setSyncComponentType(Integer syncComponentType) {
		this.syncComponentType = syncComponentType;
	}

	public Integer getSyncCompTypeMC() {
		return syncCompTypeMC;
	}

	public DeviceSeries getDeviceSeries() {
		return deviceSeries;
	}

	public void setDeviceSeries(DeviceSeries deviceSeries) {
		this.deviceSeries = deviceSeries;
	}

	public TemplatePara getSynComponent() {
		return synComponent;
	}

	public void setSynComponent(TemplatePara synComponent) {
		this.synComponent = synComponent;
	}

	public String getOid() {
		return oid;
	}

}
