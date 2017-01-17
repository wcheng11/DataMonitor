package demo.framework.storm_spring.service;

public class ResponseService implements IResponseService {

	@Override
	public String response(String name) {
		return "hello,"+name;
	}

}
