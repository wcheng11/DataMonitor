package demo.framework.spring.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import demo.framework.spring.service.ISpringService;

/**
 * 测试注入不同依赖类的两个SpringService实例
 * @author yangtao
 */
public class SpringServiceTest{
	private ISpringService springDao;
	private String name = "yourname";
	
	protected ApplicationContext context = null;
	
	@Before
	public void init(){
		context = 
			new ClassPathXmlApplicationContext(
					new String[]{"demo/framework/spring/resource/beans-config.xml"}
			);
	}
	
	@After
	public void tearDown() throws Exception{
		springDao = null;
	}
	
	@Test
	public void testOne() throws Exception{
		springDao = (ISpringService)context.getBean("springServiceOne");
		String ret = springDao.sayHello(name);
		Assert.assertEquals(ret, name+" is a good person");
	}
	
	@Test
	public void testTwo() throws Exception{
		springDao = (ISpringService)context.getBean("springServiceTwo");
		String ret = springDao.sayHello(name);
		Assert.assertEquals(ret, name+" is a bad person");
	}
	
	
}
