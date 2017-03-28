package edu.thss.monitor.rsp.topology.bolt;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.store.IDataStore;
import edu.thss.monitor.rsp.topology.ComponentId;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.TimeRecordBolt;

/**
 * 数据存储bolt
 * @author lihubin
 */
public class DataStoreBolt extends TimeRecordBolt{

	private static final long serialVersionUID = 7657189031026301298L;

	OutputCollector _collector;
	
	IDataStore dataStore;
	
	static int countTest1 = 0;
	static int countTest10 = 0;
	int storeTag;
	
	private String param;
	
	/**
	 * 构造器
	 * @param param - 传入参数格式为：{daoClassName:"xxx.xxx.xxx",keyspace:"xxx",columnFamily:"xxx"}
	 * 				例如：edu.thss.monitor.rsp.service.store.imp.SanyDataDAO-{columnfamily:'sany',keyspace:'workStatusData'}
	 * @throws Exception 
	 */
	public DataStoreBolt(String param) throws Exception{
		super();
		this.param = param;
	}
	
	private String workerFlag;
	
	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		_collector = collector;
		try {
			dataStore = (IDataStore)AppContext.getSpringContext().getBean("kafkaStore");
			dataStore.setConfig(param);
		} catch (Exception e) {
			new RSPException("数据存储Bolt初始化失败!",e);
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
		
		//定时器，满足一定时间后即将缓冲区中的工况存到数据库中
//		Timer timer=new Timer();//实例化Timer类    
//		timer.schedule(new TimerTask(){   
//			public void run(){   
//				try {
//					synchronized (dataStore) {
//						if(new Date().getTime()-lastExecuteTime>1000)
//							dataStore.saveDataPeriod();
//					}
//				} catch (RSPException e) {
//					//can do-sth
//				}
//			}
//		},5000,5000);//延时5秒后，每5秒执行一次
		
		workerFlag = "[worker port:"+context.getThisWorkerPort()+", taskId:"+context.getThisTaskId()+"]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Tuple input) {
		@SuppressWarnings("unchecked")
		String times = (String) input.getValue(input.size()-1);
		times = registerTime(times, ComponentId.DATA_STORE_BOLT);
		//获取解析后的数据包
		ParsedDataPacket parsedDataPacket = (ParsedDataPacket)input.getValue(0);
		//存储数据
		try {			
			if(parsedDataPacket.getDevice().getDeviceID() != null){
				dataStore.saveData(parsedDataPacket);
			}
			_collector.ack(input);//处理成功
			
			recordTime(parsedDataPacket.getDevice().getDeviceID(), ComponentId.DATA_STORE_BOLT);
			flushAllTimes(times);
			
		} catch (RSPException e) {
			
			//测试使用
			LogRecord.info("时间:" + parsedDataPacket.getTimestamp() + "; "
					+ "流水号:" + parsedDataPacket.getBaseInfoMap().get("serialNo") + "; "
					+ "设备号:" + parsedDataPacket.getDevice().getDeviceID() + "; "
					+"workerFlag:"+workerFlag
					);
			
			Iterator iterator = parsedDataPacket.getWorkStatusMap().entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
				LogRecord.info("key:" + entry.getKey() + "; value:" + entry.getValue() + "; "); 
				
			}
			//测试使用
			super.failCount();
			_collector.fail(input);//处理失败
//			 发生异常,不作处理，只记录日志
		} catch (Exception e) {
			super.failCount();
			_collector.fail(input);//处理失败
			LogRecord.error(LogConstant.LOG_FLAG_STORE+"DataStoreBolt捕获到异常!!!");
			e.printStackTrace();
			//计算异常包数
			
		}
//		finally{
//			_collector.ack(input);//处理成功
//		}
		
		//执行计数操作
		//计算SCP包数
//		super.count();
		//计算工况数
		super.count(parsedDataPacket.getWorkStatusMap().size());
//		System.out.println("==================" + parsedDataPacket.getWorkStatusMap().size());
		//计算20个特殊工况数量(2012-03-27 昌航杨老师要求选定工况数据的数量对比)
//		int countNum = 0;
//		for(String tmp:array){
//			if(parsedDataPacket.getWorkStatusMap().get(tmp)!=null)
//				countNum++;
//		}
//		super.count(countNum);
	}

//	String[] array = {"bc1112","bc1113","bc1114","bc1115","bc1021","bc1022",
//			"bc137","bc138","bc1162","bc23","bc125","bc1033","bc1174","bc11",
//			"bc12","bc1044","bc1156","bc1157","bc126","bc124"};
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//declarer.declare(new Fields("data"));
		
	}

	
}
