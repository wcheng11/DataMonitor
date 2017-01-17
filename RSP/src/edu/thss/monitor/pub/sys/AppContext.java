package edu.thss.monitor.pub.sys;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用程序上下文 在系统初始化时加载xml， 并缓存xml中bean的内容，为了以后能快速的得到一个bean
 * @author yangtao
 */
public class AppContext {

    private static ApplicationContext springContext;

    protected AppContext(){}
    
    static{
    	springContext =
			new ClassPathXmlApplicationContext(
					new String[]{"META-INF/beans-base.xml"}
			);
    }

    /**
     * 获得Spring上下文
     * @return
     */
    public static ApplicationContext getSpringContext() {
        return springContext;
    }

}