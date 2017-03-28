package edu.thss.monitor.rsp.topology.spout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.timerecord.TimeLogger;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.receive.IOnlineReceiver;
import edu.thss.monitor.rsp.service.receive.IReceivable;
import edu.thss.monitor.rsp.service.receive.OnlineReceiver;
import edu.thss.monitor.rsp.service.receive.util.RawDataPacket2;
import edu.thss.monitor.rsp.topology.ComponentId;
import edu.thss.monitor.rsp.topology.observe.ComponentObserver;
import edu.thss.monitor.rsp.topology.observe.ObservableSpout;

/**
 * 在线接收UDP包的Spout
 * @author yangtao
 */
public class OnlineSpout extends ObservableSpout implements IReceivable{
	
	private static final long serialVersionUID = -4295899123976695527L;

	SpoutOutputCollector _collector;
	
	private IOnlineReceiver receiver;
	
	private TTransport transport;
	private TProtocol protocol;
	private OnlineReceiver.Client client;
	
	//port为接收器的服务端口
	private int port;
	
	public void setPort(int port) {
		this.port = port;
	}

	public OnlineSpout(String port){
		super();
		this.port = Integer.parseInt(port);
	}
	
	//数据缓冲为空时休眠时间间隔
//	private Integer receiveInterval = 50; //单位毫秒

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//测试用
    
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
		//日志来源设置
//		LogRecord.setSource(""+context.getThisWorkerPort());
		
		//////////////////////////未使用在线接收器时
//		//获得接收实例
//		receiver = (IOnlineReceiver)AppContext.getSpringContext().getBean("onlineReceiver");
//		LogRecord.info(LogConstant.LOG_FLAG_RECEIVE+"在线接收Spout初始化... ");
////		receiver = new OnlineReceiver();
//		receiver.setPort(port);
//		//启动UDP监听线程
//		receiver.startUDPListenThread();
		/////////////////////////未使用在线接收器时
		
		
		/////////////////////////使用在线接收器
		AppContext.getSpringContext();
		LogRecord.info(LogConstant.LOG_FLAG_RECEIVE+"在线接收Spout初始化... ");
		// 设置调用的服务地址为本地，端口为port
	    transport = new TSocket("192.168.15.213", port);
	    boolean flag = true;
	    while (flag) {
	    	try {
				transport.open();
				System.out.println("与接收器的连接成功............");
				flag = false;
			} catch (TTransportException e) {
				System.out.println("与接收器的连接出现异常,正在重新连接");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					//TODO
				}
			} 
		}
	    
	    // 设置传输协议为 TBinaryProtocol 
	    protocol = new TBinaryProtocol(transport); 
	    client = new OnlineReceiver.Client(protocol);
		/////////////////////////使用在线接收器
		
		//加入Bolt观察者观测集合
		ComponentObserver.addComponent(context.getThisComponentId()+"_"+context.getThisTaskIndex(), this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//发送数据格式
		declarer.declare(new Fields("data", "times"));
	}

	/**
	 * 接收
	 */
	@Override
	public void receive() {
		
		/***************************************************未使用接收器
		int i = 0;
		while(true){
			//STEP1：从UDP数据队列中取出一条
			RawDataPacket packet = UDPServer.rawDataQueue.poll();
			//Step2: 如果不为空则发送到拓扑内部，为空则休眠一段时间
	        if(packet==null) {
//	            Utils.sleep(receiveInterval);
	        	break;
	        } else {
	        	//调试时打印接收到的报文内容
//	        	if(LogRecord.isInfoEnabled()){
//	    	    	StringBuffer sb = new StringBuffer(LogConstant.LOG_FLAG_RECEIVE+"接收到UDP数据：来源IP(");
//	    	    	sb.append(packet.getIp()).append(");数据来源(").append(packet.getPacketSource())
//	    	    		.append(");接收时间(").append(sdf.format(packet.getTimestamp()))
//	    	    		.append(");数据(").append(TypeUtil.getBytesString((byte[])packet.getPacketData())).append(")");
//	    	    	LogRecord.info(sb.toString());
//	        	}
	            _collector.emit(new Values(packet));
	            i++;
	            if(i%100==0){
	            	super.count(i);
	            	i=0;
	            }
	        }
		}
		//计数
        super.count(i);
		***************************************************/
		
		/***********************************使用接收器*********************************/
		int i = 0;
		// 调用服务的 getRawDataPacket2方法获取数据
	    while(true){
	    	//STEP1：从接收器的数据队列中取出一条
	    	RawDataPacket2 rawDataPacket2 = null;
	    	
	    	try {
	    		rawDataPacket2 = client.getRawDataPacket2();
	    	}catch (TTransportException e) { 
//	    		e.printStackTrace(); 
//	    		System.out.println("与接收器的连接出现异常");
		    }catch (TException e) { 
		        if (e instanceof TApplicationException && ((TApplicationException) e).getType() ==   
		                                 TApplicationException.MISSING_RESULT) { 
		        	try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
//						e1.printStackTrace();
					}
//		        	System.out.println("从接收器中获取的数据为null。"); 
		        } 
		    } 

	    	//Step2: 如果不为空则发送到拓扑内部，为空则休眠一段时间
		    if(rawDataPacket2==null) {
		    	//Utils.sleep(receiveInterval);
		    	continue;
		    }else{
		        //调试时打印接收到的报文内容
//		        if(LogRecord.isInfoEnabled()){
//		    	    StringBuffer sb = new StringBuffer(LogConstant.LOG_FLAG_RECEIVE+"接收到UDP数据：来源IP(");
//		    	    sb.append(packet.getIp()).append(");数据来源(").append(packet.getPacketSource())
//		    	    	.append(");接收时间(").append(sdf.format(packet.getTimestamp()))
//		    	    	.append(");数据(").append(TypeUtil.getBytesString((byte[])packet.getPacketData())).append(")");
//		    	    LogRecord.info(sb.toString());
//		        }
//		    	System.out.println(rawDataPacket2.getIp());
		    	Date start = new Date();
		    	RawDataPacket rawDataPacket = new RawDataPacket();
		    	rawDataPacket.setIp(rawDataPacket2.getIp());
		    	rawDataPacket.setPacketSource(rawDataPacket2.getPacketSource());
		    	rawDataPacket.setTimestamp(rawDataPacket2.getTimestamp());
		    	rawDataPacket.setPacketData(rawDataPacket2.getPacketData());
		    	
		    	String times = "" + ComponentId.ONLINE_SPOUT + ":" + start.getTime() + "-";
		        _collector.emit(new Values(rawDataPacket, times));
		        Date end = new Date();
		        TimeLogger.recordTime("0", ComponentId.ONLINE_SPOUT, start.getTime(), end.getTime());
		        i++;
		        if(i%100==0){
		        	super.count(i);
		        	i=0;
		        }
		    }
	    }
	    //计数
//	    super.count(i);
           
//        transport.close(); 
	}

}
