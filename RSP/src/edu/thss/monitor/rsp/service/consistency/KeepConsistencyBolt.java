package edu.thss.monitor.rsp.service.consistency;

import java.util.Map;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
/**
 * 一致性维护Bolt示例
 * @author Livio
 *
 */
public class KeepConsistencyBolt extends BaseRichBolt{
	
	OutputCollector _collector;
	String commonKey;
	String uniqueKey;
	
	/**
	 * 描述了调用BOMSyncManager的伪代码
	 */
	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		
		//从流数据中获得CommonKey
		//判断commonKeyExists()
		//if commonKey不存在{
		//		createCommonUniqueItem();
		//		createSyncBOM();
		//      挨个同步件调用keepConsistency();
		//}		
		//else {
		//		getUniqueKeyByCommonKey();		
		//		if UniqueKey与流数据中不相同
		//			rematchKeys();
		//		else
		//			挨个同步件调用keepConsistency();
		//}
		//
		//	  
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		_collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//还未确定
		declarer.declare(new Fields("headId","data"));
	}

}
