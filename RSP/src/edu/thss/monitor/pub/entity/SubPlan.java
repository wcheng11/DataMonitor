package edu.thss.monitor.pub.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import edu.thss.monitor.pub.entity.service.PushPlan;
import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 订阅方案
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_SubPlan")
public class SubPlan extends PushPlan implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public SubPlan() {
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
	 * 推送参数（如目的地址等,存储结构化字符串）
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
	 * 方案开始时间
	 */
	@Column(name = "plt_beginTime", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date beginTime;
	
	/**
	 * 方案结束时间
	 */
	@Column(name = "plt_endTime", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date endTime;
	
	/**
	 * 方案状态
	 */
	@Column(name = "plt_planState", columnDefinition = "NUMBER(1) DEFAULT 1")
	private Integer state;
	
	/**
	 * 订阅工况列表
	 */
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "PLT_TSM_R_SPlan2TPara", 
			joinColumns = { @JoinColumn(name = "plt_leftOid", referencedColumnName = "plt_oid") },
			inverseJoinColumns = { @JoinColumn(name = "plt_rightOid", referencedColumnName = "plt_oid") })
	private List<TemplatePara> paramaterList;

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

	public List<TemplatePara> getParamaterList() {
		return paramaterList;
	}

	public void setParamaterList(List<TemplatePara> paramaterList) {
		this.paramaterList = paramaterList;
	}

	public String getOid() {
		return oid;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
