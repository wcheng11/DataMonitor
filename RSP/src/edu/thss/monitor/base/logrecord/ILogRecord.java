package edu.thss.monitor.base.logrecord;

/**
 * 日志记录类，用来将系统中的日志持久化。
 * @author lihubin
 *
 */
public interface ILogRecord {
	
	public boolean log(String logType, String log);
	
}
