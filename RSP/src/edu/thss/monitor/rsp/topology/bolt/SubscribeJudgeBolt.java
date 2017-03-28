package edu.thss.monitor.rsp.topology.bolt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.subscribe.ISubscribeJudge;
import edu.thss.monitor.rsp.topology.ComponentId;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;
import edu.thss.monitor.rsp.topology.observe.TimeRecordBolt;

/**
 * 订阅判断bolt
 * @author lihubin
 *
 */
public class SubscribeJudgeBolt extends TimeRecordBolt{
	
	private static final long serialVersionUID = 8412304705382477989L;

	OutputCollector _collector;
	
	ISubscribeJudge subscribeJudge;

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		
		_collector = collector;
		subscribeJudge = (ISubscribeJudge)AppContext.getSpringContext().getBean("subscribeJudge");

		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		//加载资源
		AppContext.getSpringContext();//初始化spring环境
		try {
			RegionalRC.loadResource(new String[]{"subPlan","device2SubPlan","subPlanTime","subPlan2ParamID"});
		} catch (RSPException e) {
			//发生异常,不作处理，只记录日志
		}
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void execute(Tuple input) {
		Date start = new Date();
		System.out.print("获取设备识别Bolt后的数据");
		ParsedDataPacket parsedDataPacket = (ParsedDataPacket)input.getValue(0);
		List<JudgeResult>  judgeResults = null;
		
		try {
			judgeResults = subscribeJudge.getSubscribeJudge(parsedDataPacket);
			for (JudgeResult judgeResult : judgeResults) {
				_collector.emit(new Values(judgeResult));
//				LogRecord.debug(LogConstant.LOG_FLAG_SUBSCRIBE+"订阅判断结束，" + judgeResult.getId());
			}
			LogRecord.info("订阅判断结果数："+judgeResults.size());
			System.out.print("订阅判断结果数："+judgeResults.size());
			
			_collector.ack(input);//处理成功
			Date end = new Date();
			TimeLogger.recordTime(parsedDataPacket.getDevice().getDeviceID(), ComponentId.SUB_JUDGE_BOLT, start.getTime(), end.getTime());
			
		} catch (ClassCastException e) {
			new RSPException(LogConstant.LOG_FLAG_SUBSCRIBE+"SubscribeJudgeBolt执行SubscribeJudge类的getSubscribeJudge()方法发生异常",e);
			super.failCount();
			_collector.fail(input);//处理失败
		} catch (RSPException e) {
			//发生异常,不作处理，只记录日志
			super.failCount();
			_collector.fail(input);//处理失败
		} catch (ParseException e) {
			new RSPException(LogConstant.LOG_FLAG_SUBSCRIBE+"SubscribeJudgeBolt执行SubscribeJudge类的getSubPlanFromEntry()方法发生异常",e);
			super.failCount();
			_collector.fail(input);//处理失败
		}catch (Exception e) {
			new RSPException(LogConstant.LOG_FLAG_SUBSCRIBE+"SubscribeJudgeBolt执行SubscribeJudge类的getSubPlanFromEntry()方法发生异常",e);
			super.failCount();
			_collector.fail(input);//处理失败
		}
		//执行计数操作
		super.count();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("judgeResult"));
	}

}
