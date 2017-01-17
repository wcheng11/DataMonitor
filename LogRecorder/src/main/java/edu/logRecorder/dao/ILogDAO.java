package edu.logRecorder.dao;

import edu.logRecorder.entity.TimeLog;

public interface ILogDAO {

	public void recordTime(TimeLog timeLog);
	
}
