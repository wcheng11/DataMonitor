package test.redis.deserialize;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.redis.IRedisDAO;
import edu.thss.monitor.pub.entity.SubPlan;

public class MyRunnable implements Runnable{
	protected static ApplicationContext context = 
			new ClassPathXmlApplicationContext(
					new String[]{"test/redis/deserialize/beans-test.xml"}
			);
	
	@Override
	public void run() {
		IRedisDAO redisDAO = (IRedisDAO)context.getBean("redisDAO");
		for(int i=0;i<10000;i++){
			SubPlan sp = (SubPlan)redisDAO.getObjectData(5, "26EFD2E13C710829013C7108407400B0", SubPlan.class);
			System.out.println(sp.getOid());
		}
	}
}
