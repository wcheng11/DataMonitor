package demo.framework.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity 
@Table(name = "TB_TEST") 
public class TestEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="id")
	private String id;

	@Column(name="name")
	private String name;

	@Column(name="value",length=5)
	private String value;
	
	@Transient
	private String logicAttr; //非持久化属性用@Transient标记

	public String getLogicAttr() {
		return logicAttr;
	}

	public void setLogicAttr(String logicAttr) {
		this.logicAttr = logicAttr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer("TestEntity(");
		sb.append("id=").append(id).append(",")
		.append("name=").append(name).append(",")
		.append("value=").append(value).append(")");
		return sb.toString();
	}
}
