package edu.thss.monitor.rsp.topology.test.receive;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.util.TypeUtil;

/**
 * 测试Bolt: 数据接收后
 */
public class TestReceiveBolt extends BaseRichBolt {

	private static final long serialVersionUID = -9101503428236294465L;
	
	private Log logger = LogFactory.getLog(TestReceiveBolt.class);
	
	OutputCollector _collector;
	
    @SuppressWarnings("unchecked")
	@Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void execute(Tuple tuple) {
    	//从tuple中取出自定义类型RawDataPacket
    	RawDataPacket packet = (RawDataPacket)tuple.getValue(0); 
    	//Debug模式下打印报文内容
//    	if(logger.isInfoEnabled()){
	    	StringBuffer sb = new StringBuffer("接收到UDP数据：来源IP(");
	    	sb.append(packet.getIp()).append(");数据来源(").append(packet.getPacketSource())
	    		.append(");接收时间(").append(sdf.format(packet.getTimestamp()))
	    		.append(");数据(").append(TypeUtil.getBytesString((byte[])packet.getPacketData())).append(")");
	    	logger.info(sb.toString());
//    	}
    	_collector.ack(tuple);
    }
    
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("word"));
    }
}