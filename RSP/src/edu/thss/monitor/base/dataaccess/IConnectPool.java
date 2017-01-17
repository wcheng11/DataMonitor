package edu.thss.monitor.base.dataaccess;

import java.sql.Connection;

import edu.thss.monitor.pub.exception.RSPException;

/**
 * LaUD数据库连接池
 * @author lihubin
 */
public interface IConnectPool {
	
	/**
	 * 创建连接
	 * @return Connection
	 * @throws RSPException 
	 */
	public Connection create() throws RSPException;
	
	/**
	 * 往连接池中加入指定数量的连接
	 * @param num 需要创建的连接数量
	 * @throws RSPException 
	 */
	public void add(int num);
	
	/**
	 * 初始化数据库连接池
	 * @throws RSPException 
	 */
	public void init();
	
	/**
	 * 获取连接
	 * @return Connection 数据库连接
	 * @throws RSPException 
	 */
	public Connection getCon() throws RSPException;
	
	/**
	 * 释放连接
	 * @param con 数据库连接
	 */
	public void release(Connection con);
	
	/**
	 * 清空连接池
	 * @throws RSPException 
	 */
	public void clear() throws RSPException;
}
