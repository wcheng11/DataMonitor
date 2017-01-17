package edu.thss.monitor.pub.entity;

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

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 设备系列
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_DeviceSeries")
public class DeviceSeries implements java.io.Serializable {
	
	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public DeviceSeries() {
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
	 * 设备系列名称
	 */
	@Column(name = "plt_seriesName", columnDefinition = "NVARCHAR2(32)")
	private String seriesName;

	/**
	 * 设备系列命名规则
	 */
	@Column(name = "plt_seriesRule", columnDefinition = "NVARCHAR2(100)")
	private String seriesRule;

	/**
	 * 同步件配置列表
	 */
	@OneToMany(mappedBy = "deviceSeries")
	@LazyCollection(LazyCollectionOption.FALSE)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<SynConfig> synConfigList;

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesRule() {
		return seriesRule;
	}

	public void setSeriesRule(String seriesRule) {
		this.seriesRule = seriesRule;
	}

	public String getOid() {
		return oid;
	}

	public List<SynConfig> getSynConfigList() {
		return synConfigList;
	}

	public void setSynConfigList(List<SynConfig> synConfigList) {
		this.synConfigList = synConfigList;
	}

}
