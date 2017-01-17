package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 参数处理方案
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_TempTreClass")
public class TempTreClass implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public TempTreClass() {
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
	 * 处理方案名称
	 */
	@Column(name = "plt_treatName", columnDefinition = "NVARCHAR2(32)")
	private String treatName;

	/**
	 * 处理方案类名
	 */
	@Column(name = "plt_className", columnDefinition = "NVARCHAR2(100)")
	private String className;

	/**
	 * 格式字符串，用于向用户提示构造函数接受的参数的格式
	 */
	@Column(name = "plt_acceptedParameter", columnDefinition = "NVARCHAR2(100)")
	private String acceptedParameter;

	/**
	 * 参数处理类型主码
	 */
	@Column(name = "plt_tempTreatTypeMC", columnDefinition = "NUMBER(2) DEFAULT 5")
	private Integer tempTreatTypeMC;

	/**
	 * 处理类型
	 */
	@Column(name = "plt_treatType", columnDefinition = "NUMBER(2)")
	private Integer treatType;

	public String getTreatName() {
		return treatName;
	}

	public void setTreatName(String treatName) {
		this.treatName = treatName;
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

	public Integer getTempTreatTypeMC() {
		return tempTreatTypeMC;
	}

	public Integer getTreatType() {
		return treatType;
	}

	public void setTreatType(Integer treatType) {
		this.treatType = treatType;
	}

	public String getOid() {
		return oid;
	}

}
