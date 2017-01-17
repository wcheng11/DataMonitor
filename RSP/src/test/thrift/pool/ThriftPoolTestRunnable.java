package test.thrift.pool;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.thss.monitor.base.communication.thrift.autogen.ThriftPlatform;
import edu.thss.monitor.base.communication.thrift.pool.IThriftConnectionPool;

public class ThriftPoolTestRunnable implements Runnable{

	private static ApplicationContext context = 
		new ClassPathXmlApplicationContext(
				new String[]{"test/thrift/pool/beans-test.xml"}
		);
	
	private static IThriftConnectionPool pool = (IThriftConnectionPool)context.getBean("thriftConnectionProvider");
	
	private int id;
	
	public ThriftPoolTestRunnable(int id){
		this.id = id;
	}
	
	@Override
	public void run() {
		for(int i=0;i<10000;i++){
			TTransport transport = pool.getConnection();
			System.out.println("transport object form pool:"+transport);
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftPlatform.Client client = new ThriftPlatform.Client(protocol);
			try {
				client.thriftReceiveLog("source"+id, "info", "test thrift...");
			} catch (TException e) {
				e.printStackTrace();
			}
			pool.releaseConn((TSocket)transport);
		}
	}

}
