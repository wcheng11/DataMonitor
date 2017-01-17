package demo.framework.spring.model;

public class SpringBean {
	private int id;
	private String name;
	
	public SpringBean(){
		
	}
	
	public SpringBean(int id,String name){
		this.id=id;
		this.name=name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
}
