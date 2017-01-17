package edu.thss.monitor.pub.entity.relate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 生产型租户到使用型租户
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_Produ2Custo")
public class Produ2Custo implements java.io.Serializable {

	/**
	 * oid
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "edu.thss.monitor.pub.util.UUIDGenerator")
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
	 * 创建时间
	 */
	@Column(name = "plt_createTime", columnDefinition = "DATE DEFAULT SYSDATE ")
	private int createTime;

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

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public String getOid() {
		return oid;
	}

}
