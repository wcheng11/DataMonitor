package edu.thss.monitor.base.communication.thrift.listener;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TCompactProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import edu.thss.monitor.base.communication.thrift.autogen.ThriftProcess;
import edu.thss.monitor.base.communication.thrift.service.ThriftProcessService;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
/**
 * Storm处理节点(资源客户端)监听器
 * @author yangtao
 */
public class ProcessListener implements Runnable{

	private Integer port;
	
	public void setPort(Integer port){
		this.port = port;
	}
	
	public ProcessListener(){}
	
	public ProcessListener(Integer port){
		this.port = port;
	}
	
	/**
	 * 运行监听
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			TServerSocket serverTransport = new TServerSocket(port);
			ThriftProcess.Processor processor = new ThriftProcess.Processor( new ThriftProcessService() );
			Factory protFactory = new TCompactProtocol.Factory();
			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(protFactory);
			TServer server = new TThreadPoolServer(args);
			LogRecord.info("启动Storm处理节点(资源客户端)的资源服务监听线程，开始监听 ...");
			server.serve();
		} catch (TTransportException e) {
			LogRecord.error("启动Storm处理节点(资源客户端)的监听线程发生异常!",e);
		}
	}

	public static void main(String args[]) {
		ProcessListener srv = new ProcessListener();
		srv.run();
	}
	
}
