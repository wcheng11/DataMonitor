package edu.thss.monitor.pub.entity.service;
/**
 * 动态实体，用于座位资源管理加载数据时联表查询的结果对象
 * @author yangtao
 */
public class DynamicEntity {

	private String attr1;
	
	private String attr2;

	private String attr3;

	private String attr4;

	private String attr5;

	public DynamicEntity(String attr1, String attr2) {
		this.attr1 = attr1;
		this.attr2 = attr2;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getAttr5() {
		return attr5;
	}

	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}
	
	
}
