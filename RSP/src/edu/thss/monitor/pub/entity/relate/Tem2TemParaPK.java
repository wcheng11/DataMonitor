package edu.thss.monitor.pub.entity.relate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;

@SuppressWarnings("serial")
@Embeddable
public class Tem2TemParaPK implements Serializable{

	public Tem2TemParaPK() {
	}

	/**
	 * 左类oid, 模板
	 */
	@ManyToOne
	@JoinColumn(name = "plt_leftOid")
	public Template template;

	/**
	 * 右类oid,模板参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_rightOid")
	public TemplatePara templatePara;

	@Override
	public int hashCode() {
		return (template.getOid() + templatePara.getOid()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Tem2TemParaPK))
			return false;
		if (obj == null)
			return false;
		Tem2TemParaPK pk = (Tem2TemParaPK) obj;
		return pk.template.equals(this.template)
				&& pk.templatePara.equals(this.templatePara);
	}

}
