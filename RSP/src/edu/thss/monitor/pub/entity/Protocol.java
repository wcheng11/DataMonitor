package edu.thss.monitor.pub.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.entity.relate.Pro2Temp;
import edu.thss.monitor.pub.util.IDGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_Protocol")
public class Protocol implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public Protocol() {
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
	 * 协议名称
	 */
	@Column(name = "plt_protocolName", columnDefinition = "NVARCHAR2(32)")
	private String protocalName;

	/**
	 * 协议识别方式,举例： file: c://documents//123.txt port:1235
	 */
	@Column(name = "plt_packetSource", columnDefinition = "NVARCHAR2(100)")
	private String packetSource;

	/**
	 * 协议模板识别方式. 定位到协议识别方式类
	 */
	@ManyToOne
	@JoinColumn(name = "plt_templateRecogClass")
	@NotFound(action=NotFoundAction.IGNORE)
	private ProTreatClass templateRecogClass;
	
	/**
	 * 解析方式
	 */
	@Column(name = "plt_parseMethod", columnDefinition = "NUMBER(2)")
	private Integer parseMethod;
	
	/**
	 * 模板识别算法参数
	 */
    @Column(name="plt_tempRegParameter",columnDefinition="NVARCHAR2(50)")
    private String tempRegParameter;
    
	/**
	 * 解析方式主码
	 */
	@Column(name = "plt_parseMethodMC", columnDefinition = "NUMBER(2) DEFAULT 19")
	private Integer parseMethodMC;
	
	/**
	 * 本协议在Pro2Temp关联表中的记录
	 */
	@OneToMany(mappedBy = "pk.protocol")
	@LazyCollection(LazyCollectionOption.FALSE)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Pro2Temp> pro2TempList;
	

	public String getProtocalName() {
		return protocalName;
	}

	public void setProtocalName(String protocalName) {
		this.protocalName = protocalName;
	}

	public String getPacketSource() {
		return packetSource;
	}

	public void setPacketSource(String packetSource) {
		this.packetSource = packetSource;
	}

	public String getOid() {
		return oid;
	}
	
	public Integer getParseMethod() {
		return parseMethod;
	}

	public void setParseMethod(Integer parseMethod) {
		this.parseMethod = parseMethod;
	}

	public Integer getParseMethodMC() {
		return parseMethodMC;
	}

	public ProTreatClass getTemplateRecogClass() {
		return templateRecogClass;
	}

	public void setTemplateRecogClass(ProTreatClass templateRecogClass) {
		this.templateRecogClass = templateRecogClass;
	}

	public List<Pro2Temp> getPro2TempList() {
		return pro2TempList;
	}

	public void setPro2TempList(List<Pro2Temp> pro2TempList) {
		this.pro2TempList = pro2TempList;
	}

	public String getTempRegParameter() {
		return tempRegParameter;
	}

	public void setTempRegParameter(String tempRegParameter) {
		this.tempRegParameter = tempRegParameter;
	}

	
}
