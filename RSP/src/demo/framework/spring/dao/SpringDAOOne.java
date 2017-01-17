package demo.framework.spring.dao;

public class SpringDAOOne implements ISpringDAO{

	@Override
	public String getResponse(String name) {
		return name+" is a good person";
	}

}
