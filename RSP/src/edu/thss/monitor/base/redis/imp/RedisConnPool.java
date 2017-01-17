package edu.thss.monitor.base.redis.imp;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.redis.IRedisConnPool;

/**
 * Redis连接池
 * @author huyang
 */
public class RedisConnPool implements IRedisConnPool{

	/**
	 * 定义Redis数据库的IP地址
	 */
	private String ip; //166.111.80.207
	/**
	 * 定义连接池最大维持连接数
	 */
	private int maxActive; //200
	
	/**
	 * 定义连接池最大闲置连接数
	 */
	private int maxIdle; //50

	/**
	 * 定义连接池最大等待时间
	 */
	private int maxWait; //1000
	
	/**
	 * 定义是否从连接池中取出连接前进行检验
	 */
	private boolean testOnBorrow=false;
	
	/**
	 * 定义Jedis与JedisPool对象
	 */
	private static JedisPool pool;

	/**
	 * 设置IP地址
	 * @param ip - ip地址
	 * @return  
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 设置最大维持连接数
	 * @param maxActive - 最大维持连接数
	 * @return  
	 */
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	/**
	 * 设置最大连接数
	 * @param maxIdle - 最大连接数
	 * @return  
	 */
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * 设置最大等待时间
	 * @param maxWait - 最大等待时间
	 * @return  
	 */
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	/**
	 * 设置是否从连接池中取出连接前进行检验
	 * @param testOnBorrow - 是否进行检验
	 * @return  
	 */
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	/**
	 * 设置pool
	 * @param pool - 连接池名称
	 * @return  
	 */
	public static void setPool(JedisPool pool) {
		RedisConnPool.pool = pool;
	}
	
	/**
	 * 初始化Redis连接池
	 */
	public void init(){
		JedisPoolConfig config = new JedisPoolConfig();
	    config.setMaxActive(maxActive);
	    config.setMaxIdle(maxIdle);
	    config.setMaxWait(maxWait);
	    config.setTestOnBorrow(testOnBorrow);
	    pool = new JedisPool(config,ip);
	}
	
	/**
	 * 获得Redis连接
	 * @return 连接
	 */
	public synchronized Jedis getConn(){
		Jedis jedis = pool.getResource();
//		LogRecord.info(jedis.getDB()+"==="+jedis);
		return jedis;
	}
	
	/**
	 * 释放Redis连接
	 */
	public synchronized void releaseConn(Jedis jedis){
		pool.returnResource(jedis);
	}
	
	/**
	 * 销毁Redis连接池
	 */
	public synchronized void destoryPool(){
		pool.destroy();
	}
	
}
