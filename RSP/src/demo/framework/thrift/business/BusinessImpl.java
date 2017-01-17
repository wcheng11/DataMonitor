
package demo.framework.thrift.business;

import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.TException;
import demo.framework.thrift.gen.Protocol;
import demo.framework.thrift.gen.ThriftServerService;

public class BusinessImpl implements ThriftServerService.Iface {
	
	public static Map<String,Map<String, Protocol>> resourceLib = new HashMap<String,Map<String, Protocol>>();
	{
		Protocol p1 = new Protocol("1",1);
		Protocol p2 = new Protocol("2",2);
		Protocol p3 = new Protocol("3",3);
		Protocol p4 = new Protocol("4",4);
		Protocol p5 = new Protocol("5",5);
		Map<String, Protocol> resource1 = new HashMap<String, Protocol>();
		resource1.put(p1.getAttr1(), p1);
		resource1.put(p2.getAttr1(), p2);
		Map<String, Protocol> resource2 = new HashMap<String, Protocol>();
		resource2.put(p3.getAttr1(), p3);
		resource2.put(p4.getAttr1(), p4);
		resource2.put(p5.getAttr1(), p5);
		resourceLib.put("resource1", resource1);
		resourceLib.put("resource2", resource2);
	}
	@Override
	public Map<String, Protocol> getBlogResource(String resourceKey)
			throws TException {
		System.out.println("client require resource : "+resourceKey);
		return resourceLib.get(resourceKey);
	}

	@Override
	public Map<String, String> getStringResource(String resourceKey)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}


}
