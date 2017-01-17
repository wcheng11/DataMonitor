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
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.parse.process.DeviceRecognizer;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;

/**
 * 设备识别Bolt
 * @author zhuangxy
 * 2013-1-18
 */
public class DRecogBolt extends ObservableBolt {

	private static final long serialVersionUID = 531029885602405281L;
	
	private OutputCollector _collector;
	
	private ParsedDataPacket parsedData;

	/**
	 * 识别的设备
	 */
	private Device device;
	
	private DeviceRecognizer dr;

	@Override
	public void execute(Tuple input) {
		
//		System.out.print("设备识别Bolt,接收数据转换发来的数据--");
		
		try {
//			System.out.print("实例化设备1-");
			device = new Device();
			//System.out.print("实例化设备方法2-");
			dr = new DeviceRecognizer();
			parsedData = (ParsedDataPacket) input.getValue(0);
			
			device = dr.recognize(parsedData);
//			System.out.print("获取的设备号为："+this.device.getDeviceID());		
			
			parsedData.setDevice(device);
//			System.out.print("设备识别完毕，发给后续订阅Bolt");
			_collector.emit(input,new Values(parsedData));
			//_collector.emit(input,new Values(this.parsedData));
			_collector.ack(input);
		} catch (RSPException e){
			super.failCount();
			_collector.fail(input);
			//只做日志记录，不做处理
			
		} catch (Exception e) {
			LogRecord.error(LogConstant.LOG_FLAG_DEVICERECONG+"DRecogBolt捕获到未知异常");
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
//		device = new Device();
//		dr = new DeviceRecognizer();

		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		System.out.print("加载资源---------------------");
		
		// 加载资源
		AppContext.getSpringContext();//初始化spring环境
		try {
			RegionalRC.loadResource(new String[] { "commonKey2DeviceID" , "device"});
			System.out.print("加载资源:commonKey2DeviceID和device");	
		} catch (RSPException e) {
			// 发生异常,不作处理，只记录日志
			System.out.print("加载资源--------失败---------");
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("parsedDataPacket"));
	}

}
