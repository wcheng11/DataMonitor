package edu.thss.monitor.rsp.topology.bolt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;

/**
 * 设备与IP映射维护Bolt
 * 
 * @author zhuangxy 2013-1-18
 */
public class IPBolt extends ObservableBolt {

	private static final long serialVersionUID = 4782215311633950123L;

	private OutputCollector _collector;

	private Device device;

	ParsedDataPacket parsedData;

	@Override
	public void execute(Tuple input) {

		// 进行更改同步
		try {
			parsedData = (ParsedDataPacket) input.getValue(0);
			device = (Device) parsedData.getDevice();
			String currentIP = parsedData.getIp(); // 当前ip
			Date timestamp = parsedData.getTimestamp(); //当前时间
			
			//确定日期的格式
			DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			System.out.println("最新的包的时间是：" + dFormat.format(timestamp));
			
			//获取之前的IP和时间
			String preRecord = ((String)RegionalRC.getResourceItem("device2ip", device.getDeviceID()));

			//如果记录为空则插入记录；否则，比较新旧记录后再插入
			if (preRecord == null) {
				ChangeInfo changeInfo = new ChangeInfo(
						ChangeInfo.CHANGETYPE_UPDATE, "device2ip", device
								.getDeviceID(), parsedData.getIp() + "#" + dFormat.format(timestamp));
				RegionalRC.syncChange(changeInfo);
			}else{
				String[] preRecordSplit = preRecord.split("#");
				//获取之前的IP
				String preIP = preRecordSplit[0]; 
				//获取之前的时间
				Date preTimestamp = dFormat.parse(preRecordSplit[1]);
				
				System.out.println("之前的时间是：" + dFormat.format(preTimestamp));
				
				//IP发生了变更或者记录超过两分钟，则进行以下操作
				if(!(currentIP.equals(preIP)) || (timestamp.getTime() - preTimestamp.getTime()) > 120000){
					ChangeInfo changeInfo = new ChangeInfo(
							ChangeInfo.CHANGETYPE_UPDATE, "device2ip", device
									.getDeviceID(), parsedData.getIp() + "#" + dFormat.format(timestamp));
					RegionalRC.syncChange(changeInfo);
				}
			}

		} catch (RSPException e) {
			
			// 发生异常,不作处理，只记录日志
			super.failCount();
			_collector.fail(input);//处理失败
		} catch (Exception e) {

			LogRecord.error(LogConstant.LOG_FLAG_IP + "IPBolt捕获未知异常。");
			e.printStackTrace();
			_collector.fail(input);//处理失败
		}

		// 无下游Bolt
		_collector.ack(input);
		//执行计数操作
		super.count();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {

		_collector = collector;

		// 日志来源设置
		LogRecord.setSource("" + context.getThisWorkerPort());
		// 加载资源
		AppContext.getSpringContext();// 初始化spring环境
		try {
			RegionalRC.loadResource(new String[] { "device2ip" });
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
