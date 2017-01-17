package demo.framework.spring.dao;

public class SpringDAOTwo implements ISpringDAO{

	@Override
	public String getResponse(String name) {
		return name+" is a bad person";
	}

}
