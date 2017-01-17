package demo.framework.spring.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import demo.framework.spring.model.SpringBean;

/**
 * 测试SpringBean的两种依赖注入方式：set注入和构造器注入
 * @author yangtao
 */
public class SpringBeanTest{
	private SpringBean springBean;
	
	protected ApplicationContext context = null;
	
	@Before
	public void init(){
		context = 
			new ClassPathXmlApplicationContext(
					new String[]{"demo/framework/spring/resource/beans-config.xml"}
			);
	}
	
	@After
	public void finalize() throws Exception{
		springBean = null;
	}
	
	@Test
	public void testSetIOC() throws Exception{
		springBean = (SpringBean)context.getBean("springSetBean");
		Assert.assertEquals(springBean.getId(), 11);
		Assert.assertEquals(springBean.getName(), "good");
	}
	
	@Test
	public void testConstrutIOC() throws Exception{
		springBean = (SpringBean)context.getBean("springConstrutBean");
		Assert.assertEquals(springBean.getId(), 22);
		Assert.assertEquals(springBean.getName(), "so good");
	}
}
