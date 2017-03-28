package edu.thss.monitor.rsp.topology.bolt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.timerecord.TimeLogger;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.parse.process.TempValueTransformer;
import edu.thss.monitor.rsp.topology.ComponentId;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;
import edu.thss.monitor.rsp.topology.observe.TimeRecordBolt;

/**
 * 数据转换Bolt
 * @author zhuangxy
 * 2013-1-18
 */
public class TransBolt extends TimeRecordBolt {

	private static final long serialVersionUID = 458693798829657546L;

	private OutputCollector _collector;

	private ParsedDataPacket preData;

	private ParsedDataPacket parsedData;

	private TempValueTransformer tvt;
	
	
	/**
	 * 以下两个变量用于生成流水号
	 */
	private static long preTime;
	private static long currentTime;

	@Override
	public void execute(Tuple input) {
		@SuppressWarnings("unchecked")
		String times = (String) input.getValue(1);
		times = registerTime(times, ComponentId.TRANS_BOLT);
		
		preData = (ParsedDataPacket) input.getValue(0);
//		System.out.print("接收模板识别和模板解析后的输入数据，开始解析"+preData.getIp());
		
		try {
			tvt = new TempValueTransformer();
			this.parsedData = tvt.transformTempValue(preData);
			
			//没有流水号，打上流水号以便存储
			if(parsedData.getBaseInfoMap().get("serialNo") == null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSS");
				currentTime = Long.parseLong(sdf.format(new Date()));
				
				if(currentTime <= preTime ){
					currentTime = preTime;
					currentTime += 1;
				}
				
				preTime = currentTime;
				
				parsedData.getBaseInfoMap().put("serialNo", String.valueOf(currentTime));
				
				//FIXME:不是全局流水号
				LogRecord.debug(LogConstant.LOG_FLAG_TRANS+"数据包流水号："+ String.valueOf(currentTime));
//				System.out.print(LogConstant.LOG_FLAG_TRANS+"数据包流水号："+ String.valueOf(currentTime));
			}
			
			parsedData.setCommonKey(preData.getCommonKey());
			parsedData.setIp(preData.getIp());
//			System.out.print("设置共有标识"+preData.getCommonKey().getParameterName()+"和IP，IP："+preData.getIp());	
			
//			//博创数据包接收时间特殊处理
//			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
//			if (parsedData.getWorkStatusMap().containsKey("bch0108")) {
//				parsedData.setTimestamp(df.parse(parsedData.getWorkStatusMap().get("bch0108")));
//			} else if(parsedData.getWorkStatusMap().containsKey("bch0210")) {
//				parsedData.setTimestamp(df.parse(parsedData.getWorkStatusMap().get("bch0210")));
//			} else if(parsedData.getWorkStatusMap().containsKey("bch0007")) {
//				parsedData.setTimestamp(df.parse(parsedData.getWorkStatusMap().get("bch0007")));
//			} else if(parsedData.getWorkStatusMap().containsKey("bch0309")) {
//				parsedData.setTimestamp(df.parse(parsedData.getWorkStatusMap().get("bch0309")));
//			} else {
//				parsedData.setTimestamp(preData.getTimestamp());
//			}
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
			if(parsedData.getWorkStatusMap().containsKey("bch0309")) {
				parsedData.setTimestamp(df.parse(parsedData.getWorkStatusMap().get("bch0309")).getTime());
			} else {
				parsedData.setTimestamp(preData.getTimestamp());
			}
			
//			System.out.print("数据转换完毕，发给后续设备识别Bolt");
			_collector.emit(input,new Values(parsedData, times));
			_collector.ack(input);
			recordTime("0", ComponentId.TRANS_BOLT);
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
			System.out.print("发生异常1,不作处理");
			super.failCount();
			_collector.fail(input);
		} catch (Exception e) {
			System.out.print(LogConstant.LOG_FLAG_TRANS+"TransBolt捕获未知异常。");
			LogRecord.error(LogConstant.LOG_FLAG_TRANS+"TransBolt捕获未知异常。");
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
		//tvt = new TempValueTransformer();

		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		// 加载资源
		AppContext.getSpringContext();//初始化spring环境
		try {
			RegionalRC.loadResource(new String[] { "templatePara" });
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("parsedDataPacket", "times"));
	}

}
