package edu.thss.monitor.base.timerecord;

import java.util.Date;
import java.util.Map;

import edu.thss.monitor.base.timerecord.imp.TimeRecorder;
import edu.thss.monitor.pub.Property;

public class TimeLogger {

	private static ITimeRecorder timeRecorder = new TimeRecorder();
	
	private static boolean debugTime = Boolean.parseBoolean(Property.getProperty("debug.time"));
	
	private static boolean debugLog = Boolean.parseBoolean(Property.getProperty("debug.log"));
	
	private static boolean debugTimes = Boolean.parseBoolean(Property.getProperty("debug.times"));
	
	public static void recordTime(String id, String process, long start, long end){
		if(debugTime)
			timeRecorder.logTime(id, process, start, end);
	}
	
	public static void recordTimes(String id, String times){
		if(debugTimes)
			timeRecorder.flushTimes(id, times);
	}
	
	public static void log(String log){
		if(debugLog)
			timeRecorder.log(log);
	}
}
