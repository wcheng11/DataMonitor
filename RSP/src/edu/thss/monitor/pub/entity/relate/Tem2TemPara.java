package edu.thss.monitor.pub.entity.relate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;

/**
 * 模板到模板参数
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_Tem2TemPara")
public class Tem2TemPara implements java.io.Serializable {

	/**
	 * 内嵌复合主键
	 */
	@EmbeddedId
	private Tem2TemParaPK pk;

	public Tem2TemParaPK getPk() {
		return pk;
	}

	/**
	 * 偏移量
	 */
	@Column(name = "plt_offset", columnDefinition = "NVARCHAR2(32)")
	private String offset;

	/**
	 * 模板参数的排列顺序
	 */
	@Column(name = "plt_tpOrder", columnDefinition = "NUMBER(4)")
	private Integer tpOrder;

	public Tem2TemPara() {
		this.pk = new Tem2TemParaPK();
	}
	
	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public Integer getTpOrder() {
		return tpOrder;
	}

	public void setTpOrder(Integer tpOrder) {
		this.tpOrder = tpOrder;
	}

	public Template getTemplate() {
		return this.pk.template;
	}

	public TemplatePara getTemplatePara() {
		return this.pk.templatePara;
	}
	
	
	public void setTemplate(Template template) {
		this.pk.template = template;
	}

	public void setTemplatePara(TemplatePara templatePara) {
		this.pk.templatePara = templatePara;
	}
	

}
