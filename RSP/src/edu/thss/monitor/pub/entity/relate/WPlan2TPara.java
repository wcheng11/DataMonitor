package edu.thss.monitor.pub.entity.relate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.WarnAlgClass;
import edu.thss.monitor.pub.entity.WarnPlan;

/**
 * 预警方案到模板参数
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_WPlan2TPara")
public class WPlan2TPara implements java.io.Serializable {

	/**
	 * 内嵌复合主键
	 */
	@EmbeddedId
	private WPlan2TParaPK pk;

	/**
	 * 预警参数
	 */
	@Column(name = "plt_warnParameter", columnDefinition = "NVARCHAR2(100)")
	private String warnParameter;

	/**
	 * 预警算法类
	 */
	@ManyToOne
	@JoinColumn(name = "plt_warnAlgorithmClass")
	private WarnAlgClass warnAlgorithmClass;

	public String getWarnParameter() {
		return warnParameter;
	}

	public void setWarnParameter(String warnParameter) {
		this.warnParameter = warnParameter;
	}

	public WarnAlgClass getWarnAlgorithmClass() {
		return warnAlgorithmClass;
	}

	public void setWarnAlgorithmClass(WarnAlgClass warnAlgorithmClass) {
		this.warnAlgorithmClass = warnAlgorithmClass;
	}

	public WarnPlan getWarnPlan() {
		return this.pk.warnPlan;
	}

	public TemplatePara getTemplatePara() {
		return this.pk.templatePara;
	}

}
