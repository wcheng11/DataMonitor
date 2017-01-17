package edu.thss.monitor.pub.entity.relate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.Template;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_Pro2Temp")
public class Pro2Temp implements java.io.Serializable {

	/**
	 * 内嵌复合主键
	 */
	@EmbeddedId
	private Pro2TempPK pk;

	public Pro2TempPK getPk() {
		return pk;
	}

	/**
	 * 设备状态
	 */
	@Column(name = "plt_deviceStatus", columnDefinition = "NUMBER(2)")
	private Integer deviceStatus;

	/**
	 * 设备状态主码
	 */
	@Column(name = "plt_deviceStatusMC", columnDefinition = "NUMBER(2) DEFAULT 3")
	private Integer deviceStatusMC;

	public Protocol getProtocol() {
		return this.pk.protocol;
	}

	public Template getTemplate() {
		return this.pk.template;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Integer getDeviceStatusMC() {
		return deviceStatusMC;
	}

	public void setDeviceStatusMC(Integer deviceStatusMC) {
		this.deviceStatusMC = deviceStatusMC;
	}
}


