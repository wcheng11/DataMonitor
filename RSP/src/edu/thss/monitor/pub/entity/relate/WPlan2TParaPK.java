package edu.thss.monitor.pub.entity.relate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.WarnPlan;

@SuppressWarnings("serial")
@Embeddable
public class WPlan2TParaPK implements Serializable {

	public WPlan2TParaPK() {
	}

	/**
	 * 左类oid, 预警方案
	 */
	@ManyToOne
	@JoinColumn(name = "plt_leftOid")
	public WarnPlan warnPlan;

	/**
	 * 右类oid,工况模板参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_rightOid")
	public TemplatePara templatePara;

	@Override
	public int hashCode() {
		return (warnPlan.getOid() + templatePara.getOid()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof WPlan2TParaPK))
			return false;
		if (obj == null)
			return false;
		WPlan2TParaPK pk = (WPlan2TParaPK) obj;
		return pk.warnPlan.equals(this.warnPlan)
				&& pk.templatePara.equals(this.templatePara);
	}

}
