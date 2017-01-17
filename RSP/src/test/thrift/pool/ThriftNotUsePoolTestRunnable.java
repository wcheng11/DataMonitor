package test.thrift.pool;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import edu.thss.monitor.base.communication.thrift.autogen.ThriftPlatform;

public class ThriftNotUsePoolTestRunnable implements Runnable{

	private int id;
	
	public ThriftNotUsePoolTestRunnable(int id){
		this.id = id;
	}
	
	@Override
	public void run() {
		for(int i=0;i<10000;i++){
			TTransport transport = new TSocket("localhost", 7912);;
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftPlatform.Client client = new ThriftPlatform.Client(protocol);
			try {
				transport.open();
				client.thriftReceiveLog("source"+id, "info", "test thrift...");
				transport.close();
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}

}
