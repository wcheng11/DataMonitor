package demo.framework.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import demo.framework.storm.spout.CustomObject;

/**
 * Bolt: 从tuple中取出自定义对象并打印
 */
public class CheckCustomTypeBolt extends BaseRichBolt {

	private static final long serialVersionUID = -9101503428236294465L;
	
	OutputCollector _collector;
	
    @SuppressWarnings("unchecked")
	@Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }
    @Override
    public void execute(Tuple tuple) {
    	//从tuple中取出自定义类型并打印
    	CustomObject obj = (CustomObject)tuple.getValue(0); 
    	System.out.println("CustomObject.id=======>"+obj.getId());
    	System.out.println("CustomObject.name=======>"+obj.getName());
        System.out.println("CustomObject.list.get(0)=======>"+obj.getList().get(0));
        System.out.println("CustomObject.list.get(1)=======>"+obj.getList().get(1));
        System.out.println("CustomObject.cobj.id=======>"+obj.getCobj().getId());
        System.out.println("CustomObject.cobj.name=======>"+obj.getCobj().getName());
        System.out.println("CustomObject.cobj.list.get(0)=======>"+obj.getCobj().getList().get(0));
        System.out.println("CustomObject.cobj.list.get(1)=======>"+obj.getCobj().getList().get(1));
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}