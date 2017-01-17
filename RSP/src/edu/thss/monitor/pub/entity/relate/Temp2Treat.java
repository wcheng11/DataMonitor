package edu.thss.monitor.pub.entity.relate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.thss.monitor.pub.entity.TempTreClass;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;

/**
 * 模板到模板处理方案
 * @author zhuangxy
 * 2013-1-15
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_Temp2Treat")
public class Temp2Treat implements Serializable{

	/**
	 * 内嵌复合主键
	 */
	@EmbeddedId
	private Temp2TreatPK pk;
	
	/**
	 * 处理方案相关模板参数
	 */
	@ManyToOne
	@JoinColumn(name = "plt_templatePara")
	private TemplatePara templatePara;
	
	/**
	 * 处理参数（结构化字符串）
	 */
	@Column(name = "plt_treatParameter", columnDefinition = "NVARCHAR2(100)")
	private String treatParameter;

	/**
	 * 处理顺序
	 */
	@Column(name = "plt_treatSequence", columnDefinition = "NUMBER(2)")
	private int treatSequence;
	
	public Temp2Treat() {
		this.pk = new Temp2TreatPK();
	}

	public Template getTemplate() {
		return pk.template;
	}

	public TempTreClass getTemplateTreat() {
		return pk.templateTreat;
	}

	public String getTreatParameter() {
		return treatParameter;
	}

	public void setTreatParameter(String treatParameter) {
		this.treatParameter = treatParameter;
	}

	public int getTreatSequence() {
		return treatSequence;
	}

	public void setTreatSequence(int treatSequence) {
		this.treatSequence = treatSequence;
	}

	public TemplatePara getTemplatePara() {
		return templatePara;
	}

	public void setTemplatePara(TemplatePara templatePara) {
		this.templatePara = templatePara;
	}

	public void setTemplate(Template template) {
		this.pk.template = template;
	}

	public void setTemplateTreat(TempTreClass templateTreat) {
		this.pk.templateTreat = templateTreat;
	}
	
}
