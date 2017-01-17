package demo.framework.thrift.test;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TCompactProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import demo.framework.thrift.business.BusinessImpl;
import demo.framework.thrift.gen.ThriftServerService;

public class Server {
	
	@SuppressWarnings("unchecked")
	public void start() {
		try {
			TServerSocket serverTransport = new TServerSocket(7911);
			ThriftServerService.Processor processor = new ThriftServerService.Processor( new BusinessImpl() );
			Factory protFactory = new TCompactProtocol.Factory();
			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(protFactory);
			TServer server = new TThreadPoolServer(args);
			System.out.println("Starting server on port 7911 ...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Server srv = new Server();
		srv.start();
	}
}
