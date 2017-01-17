package edu.thss.monitor.pub.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_RSP_RawData")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RawData implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public RawData() {
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
	 * 报文来源
	 */
	@Column(name = "plt_dataSource", columnDefinition = "NVARCHAR2(50)")
	private String dataSource;

	/**
	 * 报文内容
	 */
	@Column(name = "plt_dataContent", columnDefinition = "NVARCHAR2(120)")
	private String dataContent;

	/**
	 * 接收时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plt_receiveTime", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date receiveTime;

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDataContent() {
		return dataContent;
	}

	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getOid() {
		return oid;
	}

}
