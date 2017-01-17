package edu.thss.monitor.pub.entity.relate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.thss.monitor.pub.entity.ParaTreatClass;
import edu.thss.monitor.pub.entity.TemplatePara;

/**
 * 模板参数到参数处理方案
 * 
 * @author zhuangxy 2012-12-25
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLT_TSM_R_TPara2Treat")
public class TPara2Treat implements java.io.Serializable {

	/**
	 * 内嵌复合主键
	 */
	@EmbeddedId
	private TPara2TreatPK pk;

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
	
	public TPara2Treat() {
		this.pk = new TPara2TreatPK();
	}

	public TemplatePara getTemplatePara() {
		return this.pk.templatePara;
	}

	public ParaTreatClass getParaTreatClass() {
		return this.pk.paraTreatClass;
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

	public void setTemplatePara(TemplatePara templatePara) {
		this.pk.templatePara = templatePara;
	}

	public void setParaTreatClass(ParaTreatClass paraTreatClass) {
		this.pk.paraTreatClass = paraTreatClass;
	}
}
