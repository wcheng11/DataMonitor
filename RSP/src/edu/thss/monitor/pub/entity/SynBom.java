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

import edu.thss.monitor.pub.util.IDGenerator;

/**
 * 同步BOM
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_SynBom")
public class SynBom implements java.io.Serializable {

	/**
	 * 默认构造函数，自动生成plt_id的时间。若重写构造函数需包含以下代码。
	 */
	public SynBom() {
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
	 * 同步BOM项
	 */
	@OneToMany(mappedBy = "synBom")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SynBomItem> syncBomItemList;

	/**
	 * 设备oid:可对应上唯一设备
	 */
	@Column(name = "plt_deviceID", columnDefinition = "NVARCHAR2(32)")
	private String device;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getOid() {
		return oid;
	}

	public List<SynBomItem> getSyncBomItemList() {
		return syncBomItemList;
	}

	public void setSyncBomItemList(List<SynBomItem> syncBomItemList) {
		this.syncBomItemList = syncBomItemList;
	}
	

}
