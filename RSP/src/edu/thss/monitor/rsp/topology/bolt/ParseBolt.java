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
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.parse.process.ReusablePacketParse;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;

/**
 * 报文解析Bolt
 * @author zhuangxy
 * 2013-1-18
 */
public class ParseBolt extends ObservableBolt {

	private static final long serialVersionUID = -8580235222054380988L;

	private OutputCollector _collector;

	private ParsedDataPacket parsedData;

	private Protocol protocol;

	private RawDataPacket rawData;

	private ReusablePacketParse rpp;
	

	@Override
	public void execute(Tuple input) {
	
		try {
//			System.out.print("接收协议识别的输入数据--------");			
			protocol = (Protocol) input.getValue(0);
			rawData = (RawDataPacket) input.getValue(1);
			
//			System.out.print("输入数据为：协议"+protocol.getProtocalName()+"报文来源"+rawData.getPacketSource());	
			
			
			
			rpp = new ReusablePacketParse();
			rpp.setProtocol(protocol);
//			System.out.print("将获取的协议传入，设置协议成功");			
			this.parsedData = rpp.reusableParse(rawData);
			
			
			this.parsedData.setTimestamp(rawData.getTimestamp());
			this.parsedData.setIp(rawData.getIp());
			

			_collector.emit(input,new Values(this.parsedData));
			_collector.ack(input);
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
//			System.out.print("----------处理异常11111---");		
			
			super.failCount();
			_collector.fail(input);
		} catch (Exception e) {
			
			LogRecord.error(LogConstant.LOG_FLAG_PARSE+"ParseBolt捕获未知异常。");
//			System.out.print("----------处理异常22222---");
			e.printStackTrace();
			super.failCount();
			_collector.fail(input);
		}
		//执行计数操作
		super.count();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {

		_collector = collector;
		//rpp = new ReusablePacketParse();

		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		
		System.out.print("----------------资源加载"+context.getThisWorkerPort());		
		// 加载资源
		AppContext.getSpringContext();//初始化spring环境
		try {
			RegionalRC.loadResource(new String[] { "protocol", "template" });
			
			
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declare(new Fields("parsedDataPacket"));
	}

}
