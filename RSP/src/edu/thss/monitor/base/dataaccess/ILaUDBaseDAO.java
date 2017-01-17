package edu.thss.monitor.base.dataaccess;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import edu.thss.monitor.pub.exception.RSPException;

/**
 * LaUD基本DAO
 * @author yangtao
 */
public interface ILaUDBaseDAO {

	/**
	 * 取得连接
	 * @return Connection
	 * @throws RSPException 
	 */
	public Connection getConnection() throws RSPException;
	
	/**
	 * 释放连接
	 */
	public void releaseConnection(Connection connection);
	
	/**
	 * 执行命令
	 * @param lasql
	 * @return boolean
	 * @throws RSPException 
	 */
	public boolean execute(String lasql) throws RSPException;
	
	/**
	 * 批量执行命令
	 * @param lasqlLst - LaSQL语句List
	 * @return boolean
	 * @throws RSPException 
	 */
	boolean batchExecute(List<String> lasqlLst) throws RSPException;
	
	/**
	 * 查询数据
	 * @param rowKey	- rowKey
	 * @param startColumn - 起始列名
	 * @param endColumn - 终止列名
	 * @return
	 * @throws RSPException
	 */
	public Map<String,String> findData(String keyspace,String rowKey, String startColumn , String endColumn) throws RSPException;
}
