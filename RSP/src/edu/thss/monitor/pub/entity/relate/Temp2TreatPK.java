package edu.thss.monitor.pub.entity.relate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.thss.monitor.pub.entity.TempTreClass;
import edu.thss.monitor.pub.entity.Template;

@SuppressWarnings("serial")
@Embeddable
public class Temp2TreatPK implements Serializable{

	public Temp2TreatPK() {
	}

	/**
	 * 左类oid, 模板
	 */
	@ManyToOne
	@JoinColumn(name = "plt_leftOid")
	public Template template;

	/**
	 * 右类oid,模板处理方案
	 */
	@ManyToOne
	@JoinColumn(name = "plt_rightOid")
	public TempTreClass templateTreat;

	@Override
	public int hashCode() {
		return (template.getOid() + templateTreat.getOid()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Temp2TreatPK))
			return false;
		if (obj == null)
			return false;
		Temp2TreatPK pk = (Temp2TreatPK) obj;
		return pk.template.equals(this.template)
				&& pk.templateTreat.equals(this.templateTreat);
	}
}
