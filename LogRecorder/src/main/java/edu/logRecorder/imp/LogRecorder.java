package edu.logRecorder.imp;

import edu.logRecorder.ILogRecorder;
import edu.logRecorder.dao.ILogDAO;
import edu.logRecorder.dao.imp.LogDAO;
import edu.logRecorder.entity.TimeLog;

public class LogRecorder implements ILogRecorder {

	ILogDAO dao;
	
	public LogRecorder(){
		dao = (ILogDAO) new LogDAO();
	}
	
	public boolean log(String log) {
		// TODO Auto-generated method stub
		String args[] = log.split("-");
		dao.recordTimeLog(new TimeLog(args[0], args[1], Long.parseLong(args[2]), Long.parseLong(args[3])));
		return true;
	}

	public boolean time(String time) {
		// TODO Auto-generated method stub
		String times []= time.split("-");
		dao.recordTime(times);
		return true;
	}

	public boolean data(String data) {
		// TODO Auto-generated method stub
		dao.log(data);
		return false;
	}

}
