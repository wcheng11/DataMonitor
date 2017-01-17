package edu.thss.monitor.pub.entity.relate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.thss.monitor.pub.entity.ParaTreatClass;
import edu.thss.monitor.pub.entity.TemplatePara;

@SuppressWarnings("serial")
@Embeddable
public class TPara2TreatPK implements Serializable{
	
	public TPara2TreatPK() {
	}
	
	/**
	 * 模板参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_leftOid")
	public TemplatePara templatePara;

	/**
	 * 模板参数处理方案
	 */
	@ManyToOne
	@JoinColumn(name = "plt_rightOid")
	public ParaTreatClass paraTreatClass;

	@Override
	public int hashCode() {
		return (templatePara.getOid() + paraTreatClass.getOid()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof TPara2TreatPK))
			return false;
		if (obj == null)
			return false;
		TPara2TreatPK pk = (TPara2TreatPK) obj;
		return pk.templatePara.equals(this.templatePara)
				&& pk.paraTreatClass.equals(this.paraTreatClass);
	}

}
