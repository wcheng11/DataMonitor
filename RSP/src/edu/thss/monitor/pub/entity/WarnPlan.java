package edu.thss.monitor.pub.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.thss.monitor.pub.entity.relate.WPlan2TPara;
import edu.thss.monitor.pub.entity.service.PushPlan;
import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 预警方案
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_WarnPlan")
public class WarnPlan extends PushPlan implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public WarnPlan() {
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
	 * 创建者
	 */
	@Column(name = "plt_creator", columnDefinition = "RAW(32)")
	private String creator;

	/**
	 * 推送类型
	 */
	@Column(name = "plt_pushType", columnDefinition = "NUMBER(2)")
	private Integer pushType;

	/**
	 * 推送参数（结构化字符串）
	 */
	@Column(name = "plt_pushParam", columnDefinition = "NVARCHAR2(200)")
	private String pushParam;

	/**
	 * 推送类型主码
	 */
	@Column(name = "plt_pushTypeMC", columnDefinition = "NUMBER(2) DEFAULT 8")
	private Integer pushTypeMC;

	/**
	 * 创建时间
	 */
	@Column(name = "plt_createTime", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date createTime;
	
	/**
	 * 本预警方案在WPlan2TPara关联表中的记录
	 */
	@OneToMany(mappedBy = "pk.warnPlan")
	@LazyCollection(LazyCollectionOption.FALSE)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<WPlan2TPara> wPlan2TParaList;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public String getPushParam() {
		return pushParam;
	}

	public void setPushParam(String pushParam) {
		this.pushParam = pushParam;
	}

	public Integer getPushTypeMC() {
		return pushTypeMC;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOid() {
		return oid;
	}

	public List<WPlan2TPara> getwPlan2TParaList() {
		return wPlan2TParaList;
	}

	public void setwPlan2TParaList(List<WPlan2TPara> wPlan2TParaList) {
		this.wPlan2TParaList = wPlan2TParaList;
	}
	
}
