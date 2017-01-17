package demo.framework.spring.service;

import demo.framework.spring.dao.ISpringDAO;

public class SpringService implements ISpringService {

	private ISpringDAO springDao;
	
	public String sayHello(String name) {
		return springDao.getResponse(name);
	}

	//set方式注入依赖
	public void setSpringDao(ISpringDAO springDao) {
		this.springDao = springDao;
	}
	
}
