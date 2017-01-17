package edu.thss.monitor.pub.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.entity.relate.Tem2TemPara;
import edu.thss.monitor.pub.entity.relate.Temp2Treat;
import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 模板
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_Template")
public class Template implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public Template() {
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
	 * 模板编号
	 */
	@Column(name = "plt_templateID", columnDefinition = "NVARCHAR2(32)")
	private String templateID;
	
	/**
	 * 模板名称
	 */
	@Column(name = "plt_templateName", columnDefinition = "NVARCHAR2(50)")
	private String templateName;

	/**
	 * 模板解析类
	 */
	@ManyToOne
	@JoinColumn(name = "plt_parseClassName")
	@NotFound(action=NotFoundAction.IGNORE)
	private TempTreClass parseClassName;

	/**
	 * 解析参数
	 */
	@Column(name = "plt_parseParameter", columnDefinition = "NVARCHAR2(100)")
	private String parseParameter;

	/**
	 * 报文的固定长度，若非固定取-1
	 */
	@Column(name = "plt_fixedLength", columnDefinition = "NUMBER(4) DEFAULT -1")
	private Integer fixedLength;

	/**
	 * 模板长度对应的模板参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_lengthParameterID")
	@NotFound(action=NotFoundAction.IGNORE)
	private TemplatePara lengthParameter;
	
	/**
	 * 公有标识参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_commonKey")
	@NotFound(action=NotFoundAction.IGNORE)
	private TemplatePara commonKey;
	
	/**
	 * 唯一标识参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_uniqueKey")
	@NotFound(action=NotFoundAction.IGNORE)
	private TemplatePara uniqueKey;

	/**
	 * 本模板在Tem2TemPara中的关联记录列表,按照tpOder升序排列
	 */
	@OneToMany(mappedBy = "pk.template")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("tpOrder")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Tem2TemPara> tem2TemParaList;
	
	/**
	 * 本模板在Temp2Treat中的关联记录列表,按照treatSequence升序排列
	 */
	@OneToMany(mappedBy = "pk.template")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("treatSequence")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Temp2Treat> temp2TreatList;
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public TempTreClass getParseClassName() {
		return parseClassName;
	}

	public void setParseClassName(TempTreClass parseClassName) {
		this.parseClassName = parseClassName;
	}

	public String getParseParameter() {
		return parseParameter;
	}

	public void setParseParameter(String parseParameter) {
		this.parseParameter = parseParameter;
	}

	public Integer getFixedLength() {
		return fixedLength;
	}

	public void setFixedLength(Integer fixedLength) {
		this.fixedLength = fixedLength;
	}

	public TemplatePara getLengthParameter() {
		return lengthParameter;
	}

	public void setLengthParameter(TemplatePara lengthParameter) {
		this.lengthParameter = lengthParameter;
	}

	public String getOid() {
		return oid;
	}

	public List<Tem2TemPara> getTem2TemParaList() {
		return tem2TemParaList;
	}

	public void setTem2TemParaList(List<Tem2TemPara> tem2TemParaList) {
		this.tem2TemParaList = tem2TemParaList;
	}

	public List<Temp2Treat> getTemp2TreatList() {
		return temp2TreatList;
	}

	public void setTemp2TreatList(List<Temp2Treat> temp2TreatList) {
		this.temp2TreatList = temp2TreatList;
	}

	public String getTemplateID() {
		return templateID;
	}

	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public TemplatePara getCommonKey() {
		return commonKey;
	}

	public void setCommonKey(TemplatePara commonKey) {
		this.commonKey = commonKey;
	}

	public TemplatePara getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(TemplatePara uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	
}
