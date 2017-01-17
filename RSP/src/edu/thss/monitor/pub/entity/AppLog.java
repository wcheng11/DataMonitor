package edu.thss.monitor.pub.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.base.logrecord.imp.Log;
import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 应用接口日志
 * 
 * @author zhuangxy 2012-12-24
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_RSP_AppLog")
public class AppLog extends Log implements java.io.Serializable {

	public AppLog(String userid,Integer operateType,String operateResult,
			String operateDetail,Date timeStamp){
		this.userid = userid;
		this.operateType = operateType;
		this.operateResult = operateResult;
		this.operateDetail = operateDetail;
		this.timeStamp = timeStamp;
		
		this.plt_id = IDGenerator.getPltID();
	}
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public AppLog() {
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
	 * 用户id
	 */
	@Column(name = "plt_userID", columnDefinition = "NVARCHAR2(32)")
	private String userid;

	/**
	 * 操作类型（包括订阅、预警、控制等）
	 */
	@Column(name = "plt_operateType", columnDefinition = "NUMBER(2)")
	private Integer operateType;

	/**
	 * 操作结果
	 */
	@Column(name = "plt_operateResult", columnDefinition = "NVARCHAR2(32)")
	private String operateResult;

	/**
	 * 详细信息
	 */
	@Column(name = "plt_operateDetail", columnDefinition = "NVARCHAR2(50)")
	private String operateDetail;

	/**
	 * 时间戳
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plt_timeStamp", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date timeStamp;

	/**
	 * 应用接口操作类型主码，default=2
	 */
	@Column(name = "plt_appOperateTypeMC", columnDefinition = "NUMBER(2) DEFAULT 2")
	private Integer appOperateTypeMC;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getOperateDetail() {
		return operateDetail;
	}

	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getAppOperateTypeMC() {
		return appOperateTypeMC;
	}

	public String getOid() {
		return oid;
	}

}
