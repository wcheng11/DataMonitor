package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 生产型租户信息
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_ProducerInfo")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProducerInfo implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public ProducerInfo() {
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
	 * 企业名称
	 */
	@Column(name = "plt_producerName", columnDefinition = "NVARCHAR2(50)")
	private String producerName;

	/**
	 * 地址
	 */
	@Column(name = "plt_producerAddress", columnDefinition = "NVARCHAR2(100)")
	private String producerAddress;

	/**
	 * 企业注册登记号
	 */
	@Column(name = "plt_registrationNumber", columnDefinition = "NVARCHAR2(20)")
	private String registrationNumber;

	/**
	 * 邮箱
	 */
	@Column(name = "plt_email", columnDefinition = "NVARCHAR2(100)")
	private String email;

	/**
	 * 企业类型主码
	 */
	@Column(name = "plt_producerTypeMC", columnDefinition = "NUMBER(2) DEFAULT 10")
	private Integer producerTypeMC;

	/**
	 * 企业类型
	 */
	@Column(name = "plt_producerType", columnDefinition = "NUMBER(2)")
	private Integer producerType;

	/**
	 * 工况数据表名
	 */
	@Column(name = "plt_cfName", columnDefinition = "NVARCHAR2(50)")
	private String cfName;

	/**
	 * 电话
	 */
	@Column(name = "plt_telephone", columnDefinition = "NVARCHAR2(50)")
	private String telephone;

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getProducerAddress() {
		return producerAddress;
	}

	public void setProducerAddress(String producerAddress) {
		this.producerAddress = producerAddress;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getProducerTypeMC() {
		return producerTypeMC;
	}

	public Integer getProducerType() {
		return producerType;
	}

	public void setProducerType(Integer producerType) {
		this.producerType = producerType;
	}

	public String getCfName() {
		return cfName;
	}

	public void setCfName(String cfName) {
		this.cfName = cfName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOid() {
		return oid;
	}

}
