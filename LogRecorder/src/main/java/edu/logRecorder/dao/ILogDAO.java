package edu.logRecorder.dao;

import edu.logRecorder.entity.TimeLog;

public interface ILogDAO {

	public void recordTimeLog(TimeLog timeLog);
	
	public void recordTime(String [] times);
	
	public void log(String log);
}
