package edu.thss.monitor.pub.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.entity.relate.TPara2Treat;
import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 模板参数
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_TemplatePara")
public class TemplatePara implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public TemplatePara() {
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
	 * 参数编号
	 */
	@Column(name = "plt_parameterID", columnDefinition = "NVARCHAR2(50)")
	private String parameterID;

	/**
	 * 参数名字
	 */
	@Column(name = "plt_parameterName", columnDefinition = "NVARCHAR2(32)")
	private String parameterName;

	/**
	 * 参数类型： 1=基础信息参数 2=工况参数 3=协议参数
	 */
	@Column(name = "plt_parameterType", columnDefinition = "NUMBER(2)")
	private Integer parameterType;
	
	/**
	 * 参数类型主码
	 */
	@Column(name = "plt_parameterTypeMC", columnDefinition = "NUMBER(2) DEFAULT 20")
	private Integer parameterTypeMC;

	/**
	 * 长度，单位为bit
	 */
	@Column(name = "plt_length", columnDefinition = "NUMBER(10)")
	private Integer length;
	
	/**
	 * 参数单位
	 */
	@Column(name = "plt_parameterUnit", columnDefinition = "NUMBER(2)")
	private Integer parameterUnit;
	
	/**
	 * 参数单位主码
	 */
	@Column(name = "plt_parameterUnitMC", columnDefinition = "NUMBER(2) DEFAULT 21")
	private Integer parameterUnitMC;

	/**
	 * 本模板参数在TPara2Treat中的关联记录的列表
	 */
	@OneToMany(mappedBy = "pk.templatePara")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("treatSequence")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<TPara2Treat> tPara2TreatList;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
	}

	public Integer getParameterTypeMC() {
		return parameterTypeMC;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getOid() {
		return oid;
	}

	public List<TPara2Treat> gettPara2TreatList() {
		return tPara2TreatList;
	}

	public void settPara2TreatList(List<TPara2Treat> tPara2TreatList) {
		this.tPara2TreatList = tPara2TreatList;
	}

	public String getParameterID() {
		return parameterID;
	}

	public void setParameterID(String parameterID) {
		this.parameterID = parameterID;
	}

	public Integer getParameterUnit() {
		return parameterUnit;
	}

	public void setParameterUnit(Integer parameterUnit) {
		this.parameterUnit = parameterUnit;
	}

	public Integer getParameterUnitMC() {
		return parameterUnitMC;
	}

}
