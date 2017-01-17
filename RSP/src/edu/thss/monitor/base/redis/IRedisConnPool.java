package edu.thss.monitor.base.redis;

import redis.clients.jedis.Jedis;
/**
 * Redis连接池
 * @author huyang
 */
public interface IRedisConnPool {

	/**
	 * 初始化Redis连接池
	 */
	public void init();
	
	/**
	 * 获得Redis连接
	 * @return 连接
	 */
	public Jedis getConn();
	
	/**
	 * 释放Redis连接
	 */
	public void releaseConn(Jedis jedis);
	
	/**
	 * 销毁Redis连接池
	 */
	public void destoryPool();
	
}
