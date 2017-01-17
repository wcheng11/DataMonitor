package edu.thss.monitor.pub.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.util.IDGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_SynBomItem")
public class SynBomItem implements Serializable {

	public SynBomItem() {
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
	 * 同步件项名
	 */
	@ManyToOne
	@JoinColumn(name = "plt_synItem")
	@NotFound(action=NotFoundAction.IGNORE)
	private TemplatePara synItem;

	/**
	 * 值
	 */
	@Column(name = "plt_synValue", columnDefinition = "NVARCHAR2(50)")
	private String synValue;

	/**
	 * 值的类型 1=唯一标识 2=公有标志 3=其它标识
	 */
	@Column(name = "plt_synType", columnDefinition = "NUMBER(2)")
	private Integer valueType;

	/**
	 * 其他标注（可用于标定是否一致）
	 */
	@Transient
	private Integer flag;

	/**
	 * 所属同步Bom 
	 */
	@ManyToOne
	@JoinColumn(name = "plt_synBom")
	@NotFound(action=NotFoundAction.IGNORE)
	private SynBom synBom;

	public TemplatePara getSynItem() {
		return synItem;
	}

	public void setSynItem(TemplatePara synItem) {
		this.synItem = synItem;
	}

	public String getSynValue() {
		return synValue;
	}

	public void setSynValue(String synValue) {
		this.synValue = synValue;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public Boolean isUniqueKey() {
		if(this.valueType == 1)
			return true;
		else 
			return false;
	}

	public Boolean isCommonKey() {
		if(this.valueType == 2)
			return true;
		else 
			return false;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public SynBom getSynBom() {
		return synBom;
	}

	public void setSynBom(SynBom synBom) {
		this.synBom = synBom;
	}

	public String getOid() {
		return oid;
	}

}
