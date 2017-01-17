package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 预警算法类
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_WarnAlgClass")
public class WarnAlgClass implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public WarnAlgClass() {
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
	 * 预警算法名
	 */
	@Column(name = "plt_algorithmName", columnDefinition = "NVARCHAR2(32)")
	private String algorithmName;

	/**
	 * 预警算法类名
	 */
	@Column(name = "plt_className", columnDefinition = "NVARCHAR2(100)")
	private String className;

	/**
	 * 接受参数
	 */
	@Column(name = "plt_acceptedParameter", columnDefinition = "NVARCHAR2(100)")
	private String acceptedParameter;

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAcceptedParameter() {
		return acceptedParameter;
	}

	public void setAcceptedParameter(String acceptedParameter) {
		this.acceptedParameter = acceptedParameter;
	}

	public String getOid() {
		return oid;
	}

}
