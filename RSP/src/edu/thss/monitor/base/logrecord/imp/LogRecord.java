package edu.thss.monitor.base.logrecord.imp;

import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.spel.ast.RealLiteral;

import clojure.main;

import edu.thss.monitor.base.communication.thrift.autogen.ThriftPlatform;
import edu.thss.monitor.base.communication.thrift.pool.IThriftConnectionPool;
import edu.thss.monitor.base.logrecord.ILogRecord;
import edu.thss.monitor.pub.dao.impl.LogDAO;
import edu.thss.monitor.pub.entity.RealtimeLog;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.pub.util.MachineEnvUtil;

/**
 * 记录日志
 * @author lihubin
 *
 */
public class LogRecord implements ILogRecord{
	
	//日志记录器
	private static org.apache.commons.logging.Log logger = LogFactory.getLog(LogRecord.class);

	//日志级别
	private static final String LOGLEVEL_DEBUG = "debug";
	private static final String LOGLEVEL_INFO = "info";
	private static final String LOGLEVEL_WARN = "warn";
	private static final String LOGLEVEL_ERROR = "error";
	
	//发送到平台服务器的日志级别，默认为error
	@SuppressWarnings("unused")
	private static String sendLogLevel = "error"; 
	//发送到平台服务器的日志状态
	private static boolean isDebugEnabled = false;
	private static boolean isWarnEnabled = false;
	private static boolean isInfoEnabled = false;
	private static boolean isErrorEnabled = true;
	
	//平台服务器Thrift连接池
	private static IThriftConnectionPool platformThriftConnPool;
	public void setPlatformThriftConnPool( //spring注入
			IThriftConnectionPool platformThriftConnPool) {
		LogRecord.platformThriftConnPool = platformThriftConnPool;
	}
	
	//ip前缀过滤,默认为空字符串
	private static String ipFrontFilter = "";
	public void setIpFrontFilter(String ipFrontFilter) { //spring注入
		LogRecord.ipFrontFilter = ipFrontFilter;
		LogRecord.source = MachineEnvUtil.getMachineIPByFilterNoExp(ipFrontFilter);
		System.out.println("注入ip头部过滤："+ipFrontFilter+"，获得ip:"+LogRecord.source);
	}
	
	//设置是否本地记录，为true则记录本地，为false则通过thrift发送到指定位置
	private static boolean isLocalRecord = true;
	public void setIsLocalRecord(boolean isLocalRecord) { //spring注入
		LogRecord.isLocalRecord = isLocalRecord;
	}
	
	//日志来源，标明日志来自哪个处理节点上
	private static String source; 
	
	//component初始化时设置，设置的值为运行机器IP+worker的slot
	public static synchronized void setSource(String source) {
		if(LogRecord.source==null||LogRecord.source.equals("")||!LogRecord.source.contains(":")){
			LogRecord.source = MachineEnvUtil.getMachineIPByFilterNoExp(ipFrontFilter)+":"+source;
			LogRecord.info("设置日志来源："+LogRecord.source);
		}
	}

	public static void error(String content){
//		logger.error(content);
//		if(!isLocalRecord&&isErrorEnabled)
//			sendLog(source,LOGLEVEL_ERROR,content);
	}
	
	public static void error(String content,Exception e){
//		logger.error(content,e);
//		if(!isLocalRecord&&isErrorEnabled)
//			sendLog(source,LOGLEVEL_ERROR,content+"\n"+e.getLocalizedMessage());
	}
	
	public static void info(String content){
//		logger.info(content);
//		if(!isLocalRecord&&isInfoEnabled)
//			sendLog(source,LOGLEVEL_INFO,content);
	}
	
	public static void warn(String content){
//		logger.warn(content);
//		if(!isLocalRecord&&isWarnEnabled)
//			sendLog(source,LOGLEVEL_WARN,content);
	}
	
	public static void debug(String content){
//		logger.debug(content);
//		if(!isLocalRecord&&isDebugEnabled)
//			sendLog(source,LOGLEVEL_DEBUG,content);
	}
	
	public static boolean isInfoEnabled(){
		return LogRecord.isInfoEnabled;
	}
	
	public static boolean isDebugEnabled(){
		return LogRecord.isDebugEnabled;
	}
	
	private static void sendLog(String source,String level,String content){
		try {
			TTransport transport = platformThriftConnPool.getConnection();
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftPlatform.Client client = new ThriftPlatform.Client(protocol);
			client.thriftReceiveLog(source, level, content);
			platformThriftConnPool.releaseConn((TSocket)transport);
		}catch (TException e) {
			new RSPException("日志客户端发送日志信息时发生异常!",e);
		}
	}
	
	/**
	 * 设置日志级别
	 * @param level - 日志级别:info/
	 */
	public static void setLogLevel(String level){
		sendLogLevel = level.toLowerCase();
		if(level.equals(LOGLEVEL_DEBUG)){
			isDebugEnabled = true;
			isInfoEnabled = true;
			isWarnEnabled = true;
			isErrorEnabled = true;
		}else if(level.equals(LOGLEVEL_INFO)){
			isDebugEnabled = false;
			isInfoEnabled = true;
			isWarnEnabled = true;
			isErrorEnabled = true;
		}else if(level.equals(LOGLEVEL_WARN)){
			isDebugEnabled = false;
			isInfoEnabled = false;
			isWarnEnabled = true;
			isErrorEnabled = true;
		}else if(level.equals(LOGLEVEL_ERROR)){
			isDebugEnabled = false;
			isInfoEnabled = false;
			isWarnEnabled = false;
			isErrorEnabled = true;
		}
	}
	
	//将日志持久化到关系数据库时，需要的DAO对象
	private static LogDAO logDAO = new LogDAO();
	public void setLogDAO(LogDAO logDAO) {
		LogRecord.logDAO = logDAO;
	}

	/**
	 * 将一个日志对象持久化到关系数据库中
	 * @param log 日志对象
	 * @return
	 */
	public static void writeLog(Log log){
		logDAO.save(log);
	}
	
//	@Test
//	public void test() {
//		
//		String[] configLocations = new String[] {"META-INF/beans-base.xml"};
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
//		
//		RealtimeLog log = new RealtimeLog();
//		log.setLogDataRecog("aaaaaa");
//		log.setLogFlag("bbbbb");
//		log.setTimeStamp(new Date());
//		LogRecord.writeLog(log);
//	}

}
