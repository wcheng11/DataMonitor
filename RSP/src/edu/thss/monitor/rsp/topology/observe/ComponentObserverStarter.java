package edu.thss.monitor.rsp.topology.observe;
/**
 * Component（包括Bolt和Spout）观察者启动器
 * @author yangtao
 */
public class ComponentObserverStarter {

//	//当前进程是否nimbus进程
//	private static boolean isNimbusWorker;
//	
//	public static void setNimbusWorker(boolean isNimbusWorker) {
//		ComponentObserverStarter.isNimbusWorker = isNimbusWorker;
//	}

	//redis内存数据库IP
	private String redisIP;
	
	//redis内存数据库端口
	private Integer redisPort;
	
	//redis存储Bolt处理计数数据的表索引
	private Integer redisTableIndex;

	//redis存储Bolt处理实时计数更新时间的key
	private String timeKey;

	//redis存储Bolt处理总计数数据的key
	private String infoKey;
	
	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}

	//redis存储Bolt处理总计数数据的key
	private String receiveQueueSizeKey;

	//计数刷新秒数
	private Integer flushSeconds;
	
	public void setReceiveQueueSizeKey(String receiveQueueSizeKey) {
		this.receiveQueueSizeKey = receiveQueueSizeKey;
	}

	public void setFlushSeconds(Integer flushSeconds) {
		this.flushSeconds = flushSeconds;
	}

	public void setRedisIP(String redisIP) {
		this.redisIP = redisIP;
	}

	public void setRedisPort(Integer redisPort) {
		this.redisPort = redisPort;
	}

	public void setRedisTableIndex(Integer redisTableIndex) {
		this.redisTableIndex = redisTableIndex;
	}

	public void setTimeKey(String timeKey) {
		this.timeKey = timeKey;
	}

	/**
	 * 启动观察者线程
	 */
	public void start(){
		ComponentObserver observer = ComponentObserver.getInstance();
		observer.setRedisInfo(redisIP, redisPort, redisTableIndex,infoKey,
				timeKey,receiveQueueSizeKey,flushSeconds);
		Thread thread = new Thread(observer);
		thread.start();
	}
	
}
