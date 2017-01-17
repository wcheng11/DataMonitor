package edu.thss.monitor.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 拓扑节点
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_TopoComp")
public class TopoComp implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public TopoComp() {
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
	 * 节点名称
	 */
	@Column(name = "plt_compName", columnDefinition = "NVARCHAR2(50)")
	private String compName;

	/**
	 * 类名
	 */
	@Column(name = "plt_className", columnDefinition = "NVARCHAR2(100)")
	private String className;

	/**
	 * 拓扑节点类型主码，默认值为14
	 */
	@Column(name = "plt_compTypeMC", columnDefinition = "NUMBER(2) DEFAULT 14")
	private Integer compTypeMC;

	/**
	 * 节点类型
	 */
	@Column(name = "plt_compType", columnDefinition = "NUMBER(2)")
	private Integer compType;

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getCompTypeMC() {
		return compTypeMC;
	}

	public Integer getCompType() {
		return compType;
	}

	public void setCompType(Integer compType) {
		this.compType = compType;
	}

	public String getOid() {
		return oid;
	}

}
