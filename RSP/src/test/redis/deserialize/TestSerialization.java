package test.redis.deserialize;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.redis.IRedisDAO;
import edu.thss.monitor.base.resource.util.KryoUtil;
import edu.thss.monitor.pub.entity.SubPlan;
import edu.thss.monitor.pub.sys.AppContext;

public class TestSerialization {

	public static void main(String[] args){
		try{
		for (int i = 0; i < 20; i++) {
			Runnable runnable = new MyRunnable();
			Thread myThread = new Thread(runnable);
			myThread.start();
			System.out.println("Thread " + myThread.getId() + " 已开始......");
		}
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}

	
}
