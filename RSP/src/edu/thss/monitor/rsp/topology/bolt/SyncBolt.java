package edu.thss.monitor.rsp.topology.bolt;

import java.util.Date;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.timerecord.TimeLogger;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.consistency.BOMSyncManager;
import edu.thss.monitor.rsp.topology.ComponentId;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;
import edu.thss.monitor.rsp.topology.observe.TimeRecordBolt;

public class SyncBolt extends TimeRecordBolt {

	private static final long serialVersionUID = 5545947661285829507L;

	private OutputCollector _collector;
	
	private ParsedDataPacket parsedData;
	
	private BOMSyncManager manager;
	
	@Override
	public void execute(Tuple input) {
		Date start = new Date();
		parsedData = (ParsedDataPacket) input.getValue(0);
		
		try {
			manager.BOMSync(parsedData);
		} catch (Exception e) {
			super.failCount();
			_collector.fail(input);//处理失败
			// 发生异常,不作处理，只记录日志
		}
		//执行计数操作
		super.count();
		Date end = new Date();
		TimeLogger.recordTime(parsedData.getDevice().getDeviceID(), ComponentId.SYNC_BOLT, start.getTime(), end.getTime());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {

		LogRecord.setSource(""+context.getThisWorkerPort());
		_collector = collector;
		
		manager = new BOMSyncManager();
		
		//TODO: 加载资源
		AppContext.getSpringContext();//初始化spring环境
		try {
			RegionalRC.loadResource(new String[]{"commonKey2DeviceID", "templatePara", "syncBOM"});
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
