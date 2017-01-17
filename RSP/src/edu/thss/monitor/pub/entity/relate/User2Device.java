package edu.thss.monitor.pub.entity.relate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_User2Device")
public class User2Device implements java.io.Serializable {

	/**
	 * oid
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "plt_oid", columnDefinition = "RAW(32)")
	private String oid;

	/**
	 * 左类
	 */
	@Column(name = "plt_leftClass", columnDefinition = "NVARCHAR2(50)")
	private String leftClass;

	/**
	 * 左类oid
	 */
	@Column(name = "plt_leftOid", columnDefinition = "RAW(32)")
	private String leftOid;

	/**
	 * 右类
	 */
	@Column(name = "plt_rightClass", columnDefinition = "NVARCHAR2(50)")
	private String rightClass;

	/**
	 * 右类oid
	 */
	@Column(name = "plt_rightOid", columnDefinition = "RAW(32)")
	private String rightOid;

	/**
	 * 验证状态
	 */
	@Column(name = "plt_authorityStatus", columnDefinition = "NUMBER(2)")
	private int authorityStatus;

	/**
	 * 验证状态主码
	 */
	@Column(name = "plt_authorityStatusMC", columnDefinition = "NUMBER(2) DEFAULT 9")
	private int authorityStatusMC;

	/**
	 * 创建者
	 */
	@Column(name = "plt_creator", columnDefinition = "RAW(32)")
	private String creator;

	public String getLeftClass() {
		return leftClass;
	}

	public void setLeftClass(String leftClass) {
		this.leftClass = leftClass;
	}

	public String getLeftOid() {
		return leftOid;
	}

	public void setLeftOid(String leftOid) {
		this.leftOid = leftOid;
	}

	public String getRightClass() {
		return rightClass;
	}

	public void setRightClass(String rightClass) {
		this.rightClass = rightClass;
	}

	public String getRightOid() {
		return rightOid;
	}

	public void setRightOid(String rightOid) {
		this.rightOid = rightOid;
	}

	public int getAuthorityStatus() {
		return authorityStatus;
	}

	public void setAuthorityStatus(int authorityStatus) {
		this.authorityStatus = authorityStatus;
	}

	public int getAuthorityStatusMC() {
		return authorityStatusMC;
	}

	public void setAuthorityStatusMC(int authorityStatusMC) {
		this.authorityStatusMC = authorityStatusMC;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOid() {
		return oid;
	}

}
