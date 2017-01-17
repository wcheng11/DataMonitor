package edu.thss.monitor.rsp.topology.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.parse.process.ProtocolRecognizer;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;

/**
 * 协议识别的Bolt
 * @author zhuangxy
 * 2013-1-18
 */
public class PRecogBolt extends ObservableBolt {

	private static final long serialVersionUID = -3664439853873159037L;

	private OutputCollector _collector;

	private ProtocolRecognizer pr;

	private Protocol protocol;

	@Override
	public void execute(Tuple input) {

		RawDataPacket rawData = (RawDataPacket) input.getValue(0);
		
		try {
			pr = new ProtocolRecognizer();
			//System.out.print("0-开始-----准备协议识别");			
			protocol = pr.recognizeProtocol(rawData.getPacketSource());
			
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
			_collector.fail(input);
		} catch (Exception e) {
			
			LogRecord.error(LogConstant.LOG_FLAG_PROTOCOLRECONG+"PRecogBolt捕获未知异常。");
			e.printStackTrace();
			super.failCount();
			_collector.fail(input);
		}

		_collector.emit(input,new Values(protocol, rawData));
//		_collector.emit(new Values(protocol, rawData));		
		
		_collector.ack(input);
		//System.out.print("协议识别发送给ParseBolt"+rawData.getPacketSource());		
		//执行计数操作
		super.count();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		
		_collector = collector;		
		//pr = new ProtocolRecognizer();

		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		//加载资源
		System.out.println("----------------------------------------------");
		System.out.println(AppContext.getSpringContext().toString());//初始化spring环境
		System.out.println("----------------------------------------------");
		try {
			RegionalRC.loadResource(new String[]{"source2Protocol"});
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declare(new Fields("protocol", "rawDataPacket"));
	}

}
