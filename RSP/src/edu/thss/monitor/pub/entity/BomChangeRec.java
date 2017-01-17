package edu.thss.monitor.pub.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 同步变更记录
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_RSP_BomChangeRec")
public class BomChangeRec implements java.io.Serializable {

	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public BomChangeRec() {
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
	 * 变更前数值
	 */
	@Column(name = "plt_beforeValue", columnDefinition = "NVARCHAR2(32)")
	private String beforeValue;

	/**
	 * 变更后数值
	 */
	@Column(name = "plt_afterValue", columnDefinition = "NVARCHAR2(32)")
	private String afterValue;

	/**
	 * 时间戳
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plt_timeStamp", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date timeStamp;

	/**
	 * 设备oid
	 */
	@Column(name = "plt_deviceID", columnDefinition = "RAW(32)")
	private String device;

	/**
	 * 变更同步件
	 */
	@ManyToOne
	@JoinColumn(name = "plt_changeComponent")
	@NotFound(action=NotFoundAction.IGNORE)
	private TemplatePara changedComponent;

	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	public String getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public TemplatePara getChangedComponent() {
		return changedComponent;
	}

	public void setChangedComponent(TemplatePara changedComponent) {
		this.changedComponent = changedComponent;
	}

	public String getOid() {
		return oid;
	}

}
