package edu.thss.monitor.base.dataaccess.imp;

import static org.junit.Assert.*;

import java.sql.Connection;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.dataaccess.imp.ConnectPool.Connect;
import edu.thss.monitor.pub.exception.RSPException;

public class ConnectPoolTest {
	protected ApplicationContext context;	
	protected ConnectPool pool;
	private int initPoolSize, increment;
	
	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext(
						new String[]{"META-INF/beans-base.xml"}
				);
		pool = (ConnectPool)context.getBean("connectPool");
		initPoolSize = pool.getMin_size();
		increment = pool.getIncrement();
	}

	@Test
	public void testInit() throws RSPException {
		assertNotNull(pool);
		assertEquals(initPoolSize, pool.getPool().size());
		pool.clear();
		
//		System.out.println("class:"+pool.getDriver_class());
//		System.out.println("Increment:"+pool.getIncrement());
//		System.out.println("Max_size:"+pool.getMax_size());
//		System.out.println("Min_size:"+pool.getMin_size());
//		System.out.println("URL:"+pool.getUrl());
//		System.out.println("Username:"+pool.getUsername());
//		System.out.println("Password:"+pool.getPassword());
	}
	
	/**
	 * 测试连接池大小的递增，到达最大大小后清除再重新获取
	 * @throws RSPException
	 */
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
		pool.clear();
	}
	
	/**
	 * 测试连接池释放连接的操作
	 * @throws RSPException
	 */
	@Test
	public void testReleaseConnection() throws RSPException{
		Connection connection = null;
		
		assertEquals(initPoolSize, pool.getPool().size());
		for (int i = 0; i < initPoolSize; i++) {
			connection = pool.getCon();
		}
		
		assertEquals(initPoolSize, pool.getPool().size());
		assertNotNull(connection);
		
		Connect lastConnect = pool.getPool().get(initPoolSize-1);
		assertFalse(lastConnect.isState());
		pool.release(connection);
		assertTrue(lastConnect.isState());
		pool.clear();
	}
}
