package edu.thss.monitor.pub.exception;

import java.util.Date;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.entity.RealtimeLog;

public class RSPException extends Exception{

	private static final long serialVersionUID = 1L;

//	public static Log logger = LogFactory.getLog(RSPException.class);
	
	private String customMsg;//自定义消息
	
	private Exception ept; //异常
	
	public RSPException(){}
	
	public RSPException(String customMsg){
		super(customMsg);
		this.customMsg = customMsg;
		LogRecord.error(customMsg);
	}
	
	public RSPException(String customMsg,Exception e){
		super(customMsg,e);
		this.customMsg = customMsg;
		this.ept = e;
		LogRecord.error(customMsg,e);
	}
	
	public RSPException(String logFlag,String logDataRecog, String recordContent){
		RealtimeLog log = new RealtimeLog();
		log.setLogFlag(logFlag);
		log.setLogDataRecog(logDataRecog);
		log.setRecordContent(recordContent);
		log.setTimeStamp(new Date());
		//LogRecord.writeLog(log);
	}
	
	public String getCustomMsg(){
		return this.customMsg;
	}
	
	public Exception getException(){
		return this.ept;
	}
	
}
