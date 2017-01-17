package edu.thss.monitor.rsp.service.timer.imp;

import edu.thss.monitor.base.dataaccess.imp.CassandraBaseDAO;
import edu.thss.monitor.rsp.service.timer.ITimeCaculator;

public class TimeCaculator extends CassandraBaseDAO implements ITimeCaculator {
	
	private String keyspace;
	
	private String column;
	
	private String urls;
	
	private int port;
		
	public void init() {
		// TODO Auto-generated constructor stub
		String [] contactPoints = urls.split(",");
		this.connect(contactPoints, port);
	}
	
	@Override
	public boolean insert(Long timestamp, int delay) {
		// TODO Auto-generated method stub
		String statement = "INSERT INTO " + keyspace + "." + column + "(time, delay) VALUES (%ld, %d)";
		if(session.execute(String.format(statement, timestamp, delay)) != null){
			return true;
		}
		
		return false;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
