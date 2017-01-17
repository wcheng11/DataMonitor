package edu.thss.monitor.rsp.topology.observe;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;
import edu.thss.monitor.rsp.service.receive.imp.UDPServer;

/**
 * 观察者(单例)
 * @author yangtao
 */
@SuppressWarnings("unchecked")
public class ComponentObserver implements Runnable{

	//单例对象
	private static ComponentObserver observer;
	
	protected ComponentObserver(){}
	
	/**
	 * 获得实例
	 */
	public static ComponentObserver getInstance(){
		if(observer==null){
			return new ComponentObserver();
		}
		return observer;
	}
	
	//可观察Bolt的Map集合(key-boltID,value-bolt对象)
	private static Map<String,IObservable> componentMap = new HashMap<String,IObservable>();
	
	//计数刷新秒数
	private static Integer flushSeconds;

	//redis存储key：Component信息
	public static String infoKey;

	//计数信息Map的key：Bolt处理总计数更新时间
	public static String timeKey;

	//计数信息Map的key：UDP接收队列大小
	public static String receiveQueueSizeKey;
	
	public static Jedis jedis;
	
	/**
	 * 设置内存数据库redis的统计数据存储位置信息
	 * @param redisIP - redis内存数据库IP
	 * @param redisPort - redis内存数据库端口
	 * @param redisTableIndex - redis存储Bolt处理计数数据的表索引
	 */
	public void setRedisInfo(String redisIP,Integer redisPort,Integer redisTableIndex,
			String infoKey,String timeKey,String receiveQueueSizeKey,Integer flushSeconds){
		//初始化jedis连接
		jedis = new Jedis(redisIP,redisPort);
		jedis.select(redisTableIndex);
		//设置变量值
		ComponentObserver.infoKey = infoKey;
		ComponentObserver.timeKey = timeKey;
		ComponentObserver.receiveQueueSizeKey = receiveQueueSizeKey;
		ComponentObserver.flushSeconds = flushSeconds;
	}
	
	/**
	 * 运行
	 */
	@Override
	public void run() {
		//循环存储计数信息
		while(true){
			//启动计数
			startCount();
			try {
				Thread.sleep(flushSeconds*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//停止计数
			stopCount();
			
			Map<String,String> infoMap = new HashMap<String,String>();
			//获得总计数结果
			observeTotalCount(infoMap);
			//获得实时计数结果
			observeCount(infoMap);
			
			//获得总异常计数
			observeTotalFailCount(infoMap);
			
			//获得实时异常计数
			observeFailCount(infoMap);
			
			//获得总工况计数
			observeTotalWSCount(infoMap);
			
			//获得实时工况计数
			observeWSCount(infoMap);
			
//			LogRecord.info("Bolt总计数("+flushSeconds+"秒)："+infoMap);
//			Map<String,String> totalCountMap = observeTotalCount();
			
			//添加当前计算时间以及接收队列大小
			infoMap.put(timeKey, String.valueOf(new Date().getTime()));
			
			//日志打印
			//存储到内存数据库
			jedis.hmset(infoKey, infoMap);
		}
	}

	/**
	 * 添加可观察Component
	 */
	public static void addComponent(String componentId, IObservable component){
		componentMap.put(componentId, component);
	}
	
	/**
	 * 观察Bolt实时计数信息
	 */
	public static void observeCount(Map<String,String> infoMap){
		Iterator it = componentMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			infoMap.put(entry.getKey(), String.valueOf(entry.getValue().getCount()));
			if(entry.getKey().startsWith("onlineSpout")&&
					entry.getKey().indexOf('_')==entry.getKey().lastIndexOf('_')){
				//获得UDP接收队列计数结果
				int receiveQueueSize = UDPServer.rawDataQueue.size();
				String index = entry.getKey().substring(entry.getKey().indexOf("onlineSpout")+11);
				infoMap.put(receiveQueueSizeKey+index, String.valueOf(receiveQueueSize));
			}
		}
	}
	
	/**
	 * 观察Bolt总计数信息
	 */
	public static void observeTotalCount(Map<String,String> infoMap){
		Iterator it = componentMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			infoMap.put(entry.getKey()+"_t", String.valueOf(entry.getValue().getTotalCount()));
		}
	}
	
	/**
	 * 观察bolt的总异常计数
	 */
	public static void observeTotalFailCount(Map<String, String> infoMap) {
		Iterator it = componentMap.entrySet().iterator();
		long count = 0;
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			count += entry.getValue().getTotalFailCount();			
		}
		infoMap.put("total_fail", String.valueOf(count));
	}

	/**
	 * 观察bolt的实时异常计数
	 */
	public static void observeFailCount(Map<String, String> infoMap) {
		Iterator it = componentMap.entrySet().iterator();
		long count = 0;
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			count += entry.getValue().getFailCount();			
		}
		infoMap.put("real_fail", String.valueOf(count));
	}
	

	/**
	 * 观察bolt的实时工况计数
	 */
	private void observeWSCount(Map<String, String> infoMap) {
		Iterator it = componentMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			infoMap.put(entry.getKey()+"_ws", String.valueOf(entry.getValue().getWsCount()));
		}
	}

	/**
	 * 观察bolt的总工况计数
	 */
	private void observeTotalWSCount(Map<String, String> infoMap) {
		Iterator it = componentMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			infoMap.put(entry.getKey()+"_ws_t", String.valueOf(entry.getValue().getTotalWSCount()));
		}
	}
	
	
	/**
	 * 启动计数
	 */
	public static void startCount(){
		Iterator it = componentMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			entry.getValue().clearCount();//计数变量还原为0
			entry.getValue().setState(true);//设置计数状态
		}
	}
	
	/**
	 * 停止计数
	 */
	public static void stopCount(){
		Iterator it = componentMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,IObservable> entry = (Entry<String,IObservable>)it.next();
			entry.getValue().setState(false);
		}
	}
	
}
