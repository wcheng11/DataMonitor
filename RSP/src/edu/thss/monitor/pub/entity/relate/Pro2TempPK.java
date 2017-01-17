package edu.thss.monitor.pub.entity.relate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.Template;

@SuppressWarnings("serial")
@Embeddable
public class Pro2TempPK implements Serializable {

	public Pro2TempPK() {
	}

	/**
	 * 协议
	 */
	@ManyToOne
	@JoinColumn(name = "plt_leftOid")
	public Protocol protocol;

	/**
	 * 模板
	 */
	@ManyToOne
	@JoinColumn(name = "plt_rightOid")
	public Template template;

	@Override
	public int hashCode() {
		return (protocol.getOid() + template.getOid()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Pro2TempPK))
			return false;
		if (obj == null)
			return false;
		Pro2TempPK pk = (Pro2TempPK) obj;
		return pk.protocol.equals(this.protocol)
				&& pk.template.equals(this.template);
	}

}
