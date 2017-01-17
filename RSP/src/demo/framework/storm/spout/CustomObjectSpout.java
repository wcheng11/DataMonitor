package demo.framework.storm.spout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
@SuppressWarnings({"serial","unchecked"})

/**
 * Spout：发送含自定义对象的tuple
 */

public class CustomObjectSpout extends BaseRichSpout {
    public static Logger LOG = Logger.getLogger(CustomObjectSpout.class);
    boolean _isDistributed;
    SpoutOutputCollector _collector;
    int _id = 0;

    public CustomObjectSpout() {
        this(true);
    }

    public CustomObjectSpout(boolean isDistributed) {
        _isDistributed = isDistributed;
    }
        
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        _collector = collector;
    }
    
    public void close() {
        
    }
        
	public void nextTuple() {
    	Utils.sleep(100);
        //组织测试数据
    	final String[] words = new String[] {"nathan", "mike", "jackson"};
        final Random rand = new Random();
        ArrayList list = new ArrayList();
        list.add("test");
        list.add(100);
        CustomObject subObj = new CustomObject(1,"test",list,null);
        //设置自定义对象
        CustomObject obj = new CustomObject(++_id,
        		words[rand.nextInt(words.length)],list,subObj);
        _collector.emit(new Values(obj));
    }
    
    public void ack(Object msgId) {

    }

    public void fail(Object msgId) {
        
    }
    
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("customObject"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        if(!_isDistributed) {
            Map<String, Object> ret = new HashMap<String, Object>();
            ret.put(Config.TOPOLOGY_MAX_TASK_PARALLELISM, 1);
            return ret;
        } else {
            return null;
        }
    }    
}