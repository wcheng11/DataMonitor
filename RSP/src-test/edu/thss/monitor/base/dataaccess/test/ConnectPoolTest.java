package edu.thss.monitor.base.dataaccess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.pub.exception.RSPException;

public class ConnectPoolTest {
	protected static ApplicationContext context = 
			new ClassPathXmlApplicationContext(
					new String[]{"META-INF/beans-base.xml"}
			);
	
	protected static ConnectPool pool;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		pool = (ConnectPool)context.getBean("connectPool");
	}

	@Test
	public void testInit() {
		int initPoolSize = 10;
		assertNotNull(pool);
		assertEquals(initPoolSize, pool.getPool().size());
		
//		System.out.println("class:"+pool.getDriver_class());
//		System.out.println("Increment:"+pool.getIncrement());
//		System.out.println("Max_size:"+pool.getMax_size());
//		System.out.println("Min_size:"+pool.getMin_size());
//		System.out.println("URL:"+pool.getUrl());
//		System.out.println("Username:"+pool.getUsername());
//		System.out.println("Password:"+pool.getPassword());
	}
	
	@Test
	public void testGetConnection() throws RSPException {
		int testConnectionCount = 100;
		for (int i = 0; i < testConnectionCount; i++) {
			Connection conn = pool.getCon();
			if (conn == null && pool.getPool().size() == pool.getMax_size()) {
				pool.clear();
				conn = pool.getCon();
			}
			assertNotNull(conn);
		}
	}
}
