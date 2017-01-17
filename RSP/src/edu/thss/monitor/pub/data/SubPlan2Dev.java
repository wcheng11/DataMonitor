package edu.thss.monitor.pub.data;

import javax.persistence.*;
@Entity
@Table(name="PLT_TSM_R_SubPlan2Dev")
public class SubPlan2Dev implements java.io.Serializable{
	
	@Id
	@Column(name = "plt_leftOid", columnDefinition = "RAW(32)")
	public String leftOid;

	@Id
	@Column(name = "plt_rightOid", columnDefinition = "RAW(32)")
	public String rightOid;

	@Column(name = "plt_oid", columnDefinition = "RAW(32)")
	private String oid;
	
	@Column(name = "plt_leftClass", columnDefinition = "NVARCHAR2(50)")
	public String leftClass;
	
	@Column(name = "plt_rightClass", columnDefinition = "NVARCHAR2(50)")
	public String rightClass;

	public String getLeftOid() {
		return leftOid;
	}

	public void setLeftOid(String leftOid) {
		this.leftOid = leftOid;
	}

	public String getRightOid() {
		return rightOid;
	}

	public void setRightOid(String rightOid) {
		this.rightOid = rightOid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getLeftClass() {
		return leftClass;
	}

	public void setLeftClass(String leftClass) {
		this.leftClass = leftClass;
	}

	public String getRightClass() {
		return rightClass;
	}

	public void setRightClass(String rightClass) {
		this.rightClass = rightClass;
	}
}

