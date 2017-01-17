package edu.thss.monitor.rsp.topology.observe;

import java.util.Map;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 * Bolt计数示例
 * Step1: Bolt类继承ObservableBolt类/Spout类继承ObservableSpout类
 * Step2: prepare初始化Bolt时，加入观察者观测集合
 * Step2: execute/open处理tuple完毕后，执行计数操作
 * @author yangtao
 */
@SuppressWarnings("serial")
public class ObservableCompDemo extends ObservableBolt{

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
		
	}
	
	@Override
	public void execute(Tuple input) {
		//To do something...
		
		//执行计数操作
		super.count();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

}
