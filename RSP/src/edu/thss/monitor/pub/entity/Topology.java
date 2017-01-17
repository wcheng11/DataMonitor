package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 拓扑
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_Topology")
public class Topology implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public Topology() {
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
	 * 拓扑名称
	 */
	@Column(name = "plt_topoName", columnDefinition = "NVARCHAR2(50)")
	private String topoName;

	/**
	 * 服务包id号
	 */
	@Column(name = "plt_servicePackageID", columnDefinition = "NVARCHAR2(32)")
	private String servicePackageID;

	/**
	 * 拓扑状态主码
	 */
	@Column(name = "plt_topoStatusMC", columnDefinition = "NUMBER(2) DEFAULT 13")
	private Integer topoStatusMC;

	/**
	 * 拓扑状态
	 */
	@Column(name = "plt_topoStatus", columnDefinition = "NUMBER(2)")
	private Integer topoStatus;

	public String getTopoName() {
		return topoName;
	}

	public void setTopoName(String topoName) {
		this.topoName = topoName;
	}

	public String getServicePackageID() {
		return servicePackageID;
	}

	public void setServicePackageID(String servicePackageID) {
		this.servicePackageID = servicePackageID;
	}

	public Integer getTopoStatusMC() {
		return topoStatusMC;
	}

	public Integer getTopoStatus() {
		return topoStatus;
	}

	public void setTopoStatus(Integer topoStatus) {
		this.topoStatus = topoStatus;
	}

	public String getOid() {
		return oid;
	}

}
