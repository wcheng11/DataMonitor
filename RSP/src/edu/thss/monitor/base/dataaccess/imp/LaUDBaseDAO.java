package edu.thss.monitor.base.dataaccess.imp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTransientConnectionException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.TimedOutException;

import edu.thss.monitor.base.dataaccess.ILaUDBaseDAO;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * LaUD基本DAO实现
 * @author yangtao
 */
public class LaUDBaseDAO implements ILaUDBaseDAO{

	protected ConnectPool connectPool;
	
	public void setConnectPool(ConnectPool connectPool) {
		this.connectPool = connectPool;
		System.out.println("--SET laud Connection!--");
	}

	@Override
	public Connection getConnection() throws RSPException {
		return connectPool.getCon();
	}

	@Override
	public void releaseConnection(Connection connection) {
		connectPool.release(connection);
	}


	@Override
	public boolean execute(String lasql) throws RSPException {
		Connection connection = getConnection();
		Statement statement = null;
		boolean rst = false;
		try {
			statement= connection.createStatement();
			statement.execute(lasql);
			rst = true;
			statement.close();
		}catch (SQLException e) {
			throw new RSPException("LaUD数据库插入数据发生异常！ ",e);
		}finally{
			releaseConnection(connection);
		}
		return rst;
	}

	@Override
	public boolean batchExecute(List<String> lasqlLst) throws RSPException {
		Connection connection = getConnection();
		Statement statement = null;
		boolean rst = false;
		int tryInsertNum = 5;
		
		StringBuffer sb = new StringBuffer();
		sb.append("BEGIN BATCH ");
		for(String lasql:lasqlLst){
			sb.append(lasql + ";");
		}
		sb.append(" APPLY BATCH");
		
		while (rst == false && tryInsertNum > 0) {
			tryInsertNum--;
			try {
				statement= connection.createStatement();
				statement.execute(sb.toString());
				rst = true;
			} catch (SQLException e) {
				if(tryInsertNum > 0 && (e.getCause() instanceof TimedOutException)){
					LogRecord.debug("批量执行lasql语句时，服务器无法响应，将在5秒后进行 第" + (5-tryInsertNum) + "次重试");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						LogRecord.info(e1.getMessage());
					}
				}else if (tryInsertNum > 0 && (e.getCause() instanceof SQLTransientConnectionException)) {
					LogRecord.debug("批量执行lasql语句时，服务器无法响应，将在5秒后进行 第" + (5-tryInsertNum) + "次重试");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						LogRecord.info(e1.getMessage());
					}
				}else{
					LogRecord.error("批量执行lasql语句失败" + e.getMessage() + "(lasql:"
							+ sb.toString().substring(0, 100) + "...)");
				}
			}finally{
				try {
					statement.close();
				} catch (SQLException e) {
					LogRecord.error("关闭statement异常。");
				}
			}
		}
		
		releaseConnection(connection);
		
		return rst;
	}

	@Override
	public Map<String,String> findData(String keyspace,String rowKey, String startColumn , String endColumn) throws RSPException{
		Map<String,String> rst = new LinkedHashMap<String,String>();
		try {
			//获取连接，创建Statement对象
			Connection con = connectPool.getCon();
			Statement statement = con.createStatement();
			statement.execute("use "+keyspace);
			//查询某台设备某个工况的在一段时间内的工况数据，将查询结果返回给rSet，注意SQL语句中java变量名与变量值
			ResultSet rSet = statement.executeQuery("select "+startColumn+".."+endColumn+" from sany where id="+rowKey);
			//关闭Statement对象，释放连接
			statement.close();
			connectPool.release(con);
			//获取数据
			for(int i=1;i<=rSet.getMetaData().getColumnCount();i++){
				rst.put(rSet.getMetaData().getColumnName(i), rSet.getObject(i).toString());
			}
			return rst;
		} catch (Exception e) {
			throw new RSPException("查询LAUD数据发生异常!",e);
		}
	}
}
