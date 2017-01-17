package edu.thss.monitor.rsp.topology.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.push.IPushService;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableBolt;

/**
 * 推送数据Bolt
 */
public class PushBolt extends ObservableBolt {

	private static final long serialVersionUID = -9101503428236294465L;
	
	OutputCollector _collector;
	
	IPushService pushService;
	
    @SuppressWarnings("unchecked")
	@Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        pushService = (IPushService)AppContext.getSpringContext().getBean("pushService");
		//日志来源设置
		LogRecord.setSource(""+context.getThisWorkerPort());
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
    }
    
    @Override
    public void execute(Tuple tuple) {
    	LogRecord.info(LogConstant.LOG_FLAG_PUSH+"数据推送收到订阅判断结果!");
    	//获得前面参数
    	JudgeResult jr = (JudgeResult)tuple.getValue(0); 
    	//推送该数据
    	try {
			pushService.push(jr);
			_collector.ack(tuple);//处理成功
			LogRecord.info(LogConstant.LOG_FLAG_PUSH+"数据推送执行完毕! 工况数据条数:"+(jr.getWorkStatusList()==null?0:jr.getWorkStatusList().size())+")");
		} catch (Exception e) {
			//发生异常,不作处理，只记录日志
//			new RSPException(LogConstant.LOG_FLAG_PUSH+"数据推送发生异常!(推送类型:"+jr.getPushType()+"推送参数:"+jr.getPushParam()+")",e);
			super.failCount();
			_collector.fail(tuple);//处理失败
		}
		//执行计数操作
		super.count();
    }
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("word"));
    }
}