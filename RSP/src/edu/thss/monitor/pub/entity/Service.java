package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 状态监测平台提供的服务的信息
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_MPM_Service")
public class Service implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public Service() {
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
	 * 服务类型
	 */
	@Column(name = "plt_serviceType", columnDefinition = "NUMBER(2)")
	private Integer serviceType;

	/**
	 * 服务名称
	 */
	@Column(name = "plt_serviceName", columnDefinition = "NVARCHAR2(50)")
	private String serviceName;

	/**
	 * 服务类型主码
	 */
	@Column(name = "plt_serviceTypeMC", columnDefinition = "NUMBER(2) DEFAULT 17")
	private Integer serviceTypeMC;

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceTypeMC() {
		return serviceTypeMC;
	}

	public String getOid() {
		return oid;
	}

}
