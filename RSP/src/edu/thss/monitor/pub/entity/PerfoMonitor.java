package edu.thss.monitor.pub.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 平台性能监测
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_MPM_PerfoMonitor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PerfoMonitor implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public PerfoMonitor() {
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
	 * 时间戳
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plt_timeStamp", columnDefinition = "DATE DEFAULT SYSDATE ")
	private Date timeStamp;

	/**
	 * storm 流量
	 */
	@Column(name = "plt_stormFlow", columnDefinition = "NUMBER(10)")
	private Long stormFlow;

	/**
	 * storm推送量
	 */
	@Column(name = "plt_stormDeliveryNum", columnDefinition = "NUMBER(10)")
	private Long stormDeliveryNum;

	/**
	 * storm包识别量
	 */
	@Column(name = "plt_stormPackIdentifyNum", columnDefinition = "NUMBER(10)")
	private Long stormPackIdentifyNum;

	/**
	 * Strom处理参数量
	 */
	@Column(name = "plt_stormProcessParaNum", columnDefinition = "NUMBER(10)")
	private Long stormProcessParaNum;

	/**
	 * Strom出错量
	 */
	@Column(name = "plt_stormErrorAmount", columnDefinition = "NUMBER(10)")
	private Long stormErrorAmount;

	/**
	 * Redis写速率
	 */
	@Column(name = "plt_redisWriteRate", columnDefinition = "NUMBER(10)")
	private Long redisWriteRate;

	/**
	 * Redis读速率
	 */
	@Column(name = "plt_redisReadRate", columnDefinition = "NUMBER(10)")
	private Long redisReadRate;

	/**
	 * 数据库写速率
	 */
	@Column(name = "plt_databaseReadRate", columnDefinition = "NUMBER(10)")
	private Long databaseReadRate;

	/**
	 * 数据库读速率
	 */
	@Column(name = "plt_databaseWriteRate", columnDefinition = "NUMBER(10)")
	private Long databaseWriteRate;

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getStormFlow() {
		return stormFlow;
	}

	public void setStormFlow(Long stormFlow) {
		this.stormFlow = stormFlow;
	}

	public Long getStormDeliveryNum() {
		return stormDeliveryNum;
	}

	public void setStormDeliveryNum(Long stormDeliveryNum) {
		this.stormDeliveryNum = stormDeliveryNum;
	}

	public Long getStormPackIdentifyNum() {
		return stormPackIdentifyNum;
	}

	public void setStormPackIdentifyNum(Long stormPackIdentifyNum) {
		this.stormPackIdentifyNum = stormPackIdentifyNum;
	}

	public Long getStormProcessParaNum() {
		return stormProcessParaNum;
	}

	public void setStormProcessParaNum(Long stormProcessParaNum) {
		this.stormProcessParaNum = stormProcessParaNum;
	}

	public Long getStormErrorAmount() {
		return stormErrorAmount;
	}

	public void setStormErrorAmount(Long stormErrorAmount) {
		this.stormErrorAmount = stormErrorAmount;
	}

	public Long getRedisWriteRate() {
		return redisWriteRate;
	}

	public void setRedisWriteRate(Long redisWriteRate) {
		this.redisWriteRate = redisWriteRate;
	}

	public Long getRedisReadRate() {
		return redisReadRate;
	}

	public void setRedisReadRate(Long redisReadRate) {
		this.redisReadRate = redisReadRate;
	}

	public Long getDatabaseReadRate() {
		return databaseReadRate;
	}

	public void setDatabaseReadRate(Long databaseReadRate) {
		this.databaseReadRate = databaseReadRate;
	}

	public Long getDatabaseWriteRate() {
		return databaseWriteRate;
	}

	public void setDatabaseWriteRate(Long databaseWriteRate) {
		this.databaseWriteRate = databaseWriteRate;
	}

	public String getOid() {
		return oid;
	}

	

}
