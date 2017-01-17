package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_MPM_Code")
public class Code implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public Code() {
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
	 * 主码值
	 */
	@Column(name = "plt_majorCode", columnDefinition = "NUMBER(5)")
	private Integer majorCode;

	/**
	 * 子码值
	 */
	@Column(name = "plt_subcode", columnDefinition = "NUMBER(2)")
	private Integer subcode;

	/**
	 * 代码含义
	 */
	@Column(name = "plt_name", columnDefinition = "NVARCHAR2(50)")
	private String name;

	public Integer getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(Integer majorCode) {
		this.majorCode = majorCode;
	}

	public Integer getSubcode() {
		return subcode;
	}

	public void setSubcode(Integer subcode) {
		this.subcode = subcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOid() {
		return oid;
	}

}
