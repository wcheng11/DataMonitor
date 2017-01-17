package edu.thss.monitor.base.dataaccess.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

import edu.thss.monitor.base.dataaccess.IConnectPool;
import edu.thss.monitor.pub.exception.RSPException;


/**
 * LaUD数据库的连接池的实现
 * @author lihubin
 *
 */
public class ConnectPool implements IConnectPool{
	
	private String driver_class;
	private String url;
	private String username;
	private String password;
	private int min_size;
	private int max_size;
	private int increment;
	private List<Connect> pool = new ArrayList<Connect>();
	
	public int hostIndex = 0;
	
	@Override
	public Connection create() throws RSPException{
		String hosts[] = url.split(";");
//		String host=System.getProperty("url",this.url);
		try {
			Class.forName(this.driver_class);
		} catch (ClassNotFoundException e) {
			throw new RSPException("未找到LaUD驱动类，请查看Jar包是否导入项目路径！",e);
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(hosts[hostIndex], this.username, this.password);
		} catch (SQLException e) {
			throw new RSPException("连接LaUD数据库发生异常！请检查LaUD节点" + hosts[hostIndex] + "是否正常提供服务！",e);
		}finally{
			hostIndex++;
			if(hostIndex == hosts.length){
				hostIndex = 0;
			}
		}
		
		return con;
	}
	
	@Override
	public void add(int num){
		for (int i = 0; i < num; i++) {
			//创建一个Connect对象
			Connect cn = new Connect();
			boolean flag = false;
			while (!flag) {
				try {
					cn.setConnection(this.create());
					cn.setState(true);
					flag = true;
				} catch (Exception e) {
					//不做处理，已打印日志
				}
				
			}
			
			
			//将连接对象加入到pool中
			this.pool.add(cn);
		}
	}
	
	@Override
	public void init(){
		this.add(this.min_size);
	}
	
	@Override
	public synchronized Connection getCon() throws RSPException{
		
		int i=0;
		//从现有的连接池中获取一个可用连接
		for (i = 0; i < this.pool.size(); i++) {
			if(this.pool.get(i).isState() == true){
				this.pool.get(i).setState(false);
				return this.pool.get(i).getConnection();
			}
		}
		
		//如现有连接池中无可用连接，则新增一定数量的连接到连接池，并返回一个可用连接
		if(i == this.max_size){
			System.out.println("已达最大连接数，获取连接失败！");
		}else if (this.max_size-(i) >= this.increment) {
			this.add(increment);
			this.pool.get(i).setState(false);
			return this.pool.get(i).getConnection();
		}else {
			this.add(this.max_size-(i));
			this.pool.get(i).setState(false);
			return this.pool.get(i).getConnection();
		}
		return null;
		
	}
	
	@Override
	public synchronized void release(Connection con){
		for (int i = 0; i < this.pool.size(); i++) {
			if(this.pool.get(i).getConnection() == con){
				this.pool.get(i).setState(true);
				return;
			}
		}
		System.out.println("释放连接失败，连接池中无此连接！");
	}
	
	@Override
	public void clear() throws RSPException{
		for (Connect connect : this.pool) {
			try {
				connect.getConnection().close();
			} catch (SQLException e) {
				throw new RSPException("清空连接池时，关闭连接错误！ ",e);
			}
		}
		this.pool.clear();
	}
	
	
	public String getDriver_class() {
		return driver_class;
	}
	public void setDriver_class(String driver_class) {
		this.driver_class = driver_class;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMin_size() {
		return min_size;
	}
	public void setMin_size(int min_size) {
		this.min_size = min_size;
	}
	public int getMax_size() {
		return max_size;
	}
	public void setMax_size(int max_size) {
		this.max_size = max_size;
	}
	public int getIncrement() {
		return increment;
	}
	public void setIncrement(int increment) {
		this.increment = increment;
	}
	public List<Connect> getPool() {
		return pool;
	}
	public void setPool(List<Connect> pool) {
		this.pool = pool;
	}
	
	public class Connect {
		
		private Connection connection;
		private boolean state;
		
		public Connection getConnection() {
			return connection;
		}
		
		public void setConnection(Connection connection) {
			this.connection = connection;
		}
		
		public boolean isState() {
			return state;
		}
		
		public void setState(boolean state) {
			this.state = state;
		}
		
	}
	
}
