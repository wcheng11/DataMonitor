package demo.framework.redisPerformanceTest;

import org.junit.Test;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

import redis.clients.jedis.Jedis;

import edu.thss.monitor.base.redis.imp.*;

public class RedisPerformanceTest {
	private RedisDAO redisDAO = new RedisDAO();
	private RedisConnPool redisConnPool = new RedisConnPool();

	Jedis jedis = new Jedis("192.168.10.35", 6379);

	// @Test
	public void TestConnection() {
		// Jedis jedis = new Jedis("192.168.10.35", 6379);
		jedis.set("li2", "lihubin2");
		System.out.println(jedis.get("li2"));
		jedis.quit();
	}

	 @Test
	public void TestDBSize() {
		Jedis jedis = new Jedis("192.168.10.35", 6379);
		try {
			for (int i = 0; i < 16; i++) {
				jedis.select(i);
				System.out.println(jedis.dbSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// 测试结果为系统中0~15号数据库在使用，10号数据库为空
	//@Test
	public void TestInit(){
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
	}

	// @Test
	public void TeseLength() {
		Long dateBegin = System.currentTimeMillis();
		String a = "a";
		String b = "abcde";
		String c = "abcdefghij";
		System.out.println(a.getBytes().length);
		System.out.println(b.getBytes().length);
		System.out.println(c.getBytes().length);
		Long dateEnd = System.currentTimeMillis();
		System.out.println(dateEnd - dateBegin);
	}// 测试结果为字符串字符个数即为字节数

	//@Test
	public void TestStringLengthOneByte() {
		Long dateBegin = System.currentTimeMillis();
		jedis.select(10);
		for (int i = 0; i < 10000; i++)
			jedis.append("" + i, "a");
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test1==="+(dateEnd - dateBegin));
		jedis.quit();
	}

	//@Test
	public void TestStringLengthOneByte2() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, "a");
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test2==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}
	

	// @Test
	public void TestStringLengthFiveByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, "abcde");
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test3==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthTenByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, "abcdefghij");
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test4==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthTwentyByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		String s=null;
		for(int i=0;i<20;i++)
			s=s+"a";
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, s);
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test5==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthFiftyByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		String s=null;
		for(int i=0;i<50;i++)
			s=s+"a";
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, s);
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test6==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthOneHundredByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		String s=null;
		for(int i=0;i<100;i++)
			s=s+"a";
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, s);
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test7==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthTwoHundredByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		String s=null;
		for(int i=0;i<200;i++)
			s=s+"a";
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, s);
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test8==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthFiveHundredByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);
		String s=null;
		for(int i=0;i<500;i++)
			s=s+"a";
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, s);
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test9==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}

	//@Test
	public void TestStringLengthAThousandByte() {
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);		
		String s=null;
		for(int i=0;i<1000;i++)
			s=s+"a";
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.addData(10, "" + i, s);
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test10==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}
	@Test
	public void TestGet(){
		redisConnPool.setIp("192.168.10.35");
		redisConnPool.setMaxActive(200);
		redisConnPool.setMaxIdle(50);
		redisConnPool.setMaxWait(1000);
		redisConnPool.init();
		redisDAO.setRedisConnPool(redisConnPool);		
		Long dateBegin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++)
			redisDAO.getStringData(10, "525999899");
		Long dateEnd = System.currentTimeMillis();
		System.out.println("Test11==="+(dateEnd - dateBegin));
		redisConnPool.destoryPool();
	}
}
