package edu.thss.monitor.base.timerecord;

import java.util.Date;
import java.util.Map;

public interface ITimeRecorder {

	public void logTime(String id, String process, long start, long end);
	
	public void flushTimes(String id, String times);
	
	public void log(String log);
	
}
