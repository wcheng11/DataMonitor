package edu.thss.monitor.rsp.topology.test.receive;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.util.MachineEnvUtil;
import edu.thss.monitor.rsp.service.receive.IOnlineReceiver;
import edu.thss.monitor.rsp.service.receive.IReceivable;
import edu.thss.monitor.rsp.service.receive.imp.OnlineReceiver;
import edu.thss.monitor.rsp.service.receive.imp.UDPServer;

/**
 * 在线接收UDP包的Spout
 * @author yangtao
 */
public class TestReceiveSpout extends BaseRichSpout implements IReceivable{

	private static final long serialVersionUID = -4295899123976695527L;

	private Log logger = LogFactory.getLog(TestReceiveSpout.class); 
	
	SpoutOutputCollector _collector;
	
	private IOnlineReceiver receiver;
	
	private int port;
	
	public void setPort(int port) {
		this.port = port;
	}

	public TestReceiveSpout(String port){
		super();
		this.port = Integer.parseInt(port);
	}
	
	//数据缓冲为空时休眠时间间隔
	private Integer receiveInterval = 50; //单位毫秒

	@Override
	public void nextTuple() {
		receive();//接收
	}

	@SuppressWarnings("unchecked")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		//初始化Spout
		_collector = collector;
		logger.info("在线接收Spout初始化... ");
		receiver = new OnlineReceiver();
		receiver.setPort(port);
		//启动UDP监听线程
		logger.info("启动UDP接收服务器，开始监听...(IP:"+MachineEnvUtil.getMachineIPNoException()+",端口号:"+port+")");
		receiver.startUDPListenThread();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//发送数据格式
		declarer.declare(new Fields("data"));
	}

	/**
	 * 接收
	 */
	@Override
	public void receive() {
		//STEP1：从UDP数据队列中取出一条
		RawDataPacket packet = UDPServer.rawDataQueue.poll();
		//Step2: 如果不为空则发送到拓扑内部，为空则休眠一段时间
        if(packet==null) {
            Utils.sleep(receiveInterval);
        } else {
            _collector.emit(new Values(packet));
        }
	}

}
