package demo.framework.thrift.test;

import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import demo.framework.thrift.gen.Protocol;
import demo.framework.thrift.gen.ThriftServerService;

public class Client {

	static Map<String, String> map = new HashMap<String, String>();
	
	public static void main(String[] args) throws Exception {
		try {
			TTransport transport = new TSocket("localhost", 7911);
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftServerService.Client client = new ThriftServerService.Client(protocol);
			transport.open();
			Map<String,Protocol> rstMap = client.getBlogResource("resource2");
			transport.close();
			System.out.println("resource size======"+rstMap.size());
		} catch (TException x) {
			x.printStackTrace();
		}
	}
}
