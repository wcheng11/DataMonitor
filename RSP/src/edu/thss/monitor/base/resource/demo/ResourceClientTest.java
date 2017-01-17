package edu.thss.monitor.base.resource.demo;

import java.util.Set;

import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 资源管理测试
 * @author yangtao
 */
public class ResourceClientTest {
	
//	@Test
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, RSPException{
		
		/*
		 * "地方"资源容器初始化
		 */
		//设置服务器信息
		String serverIP = "localhost";
		Integer serverPort = 7911;
		SyncNodeInfo serverInfo = new SyncNodeInfo(serverIP,serverPort);
		RegionalRC.setServerInfo(serverInfo);
		
//		LogRecord.init();
		//注册
		RegionalRC.register();
		
		//
		RegionalRC.loadResource(new String[]{"device2SubPlan"});
		IResource resource = (IResource)RegionalRC.getResource("device2SubPlan");
		Set<String> test = (Set<String>)resource.getResourceItem("bc0001");
		System.out.println(test);
		
		/*
		 * "地方"资源容器请求资源及测试
		 */
		//地方资源容器请求资源

//		RegionalRC.loadResource(new String[]{"template"});
//		IResource resource = (IResource)RegionalRC.getResource("template");
//		Template test = (Template)resource.getResourceItem("3030");
//		System.out.println(test.getOid()+"==="+test.getTemplateID());
		

//		RegionalRC.loadResource(new String[]{"deviceTest"});
//		
//		RegionalRC.loadResource(new String[]{"subPlanTest"});
//		
//		RegionalRC.loadResource(new String[]{"device","template","protocol","device1"});
//
//		RegionalRC.loadResource(new String[]{"templatePara"});
//		
//		RegionalRC.loadResource(new String[]{"synbom"});
//
//		RegionalRC.loadResource(new String[]{"commonKey2DeviceID"});
//
//		RegionalRC.loadResource(new String[]{"device2ip"});
//		
//		RegionalRC.loadResource(new String[]{"source2Protocol"});
//		
//		RegionalRC.loadResource(new String[]{"protocol"});
//
//		RegionalRC.loadResource(new String[]{"device2SubPlan"});
//
//		RegionalRC.loadResource(new String[]{"subPlan"});
//		
//		IResource resource = (IResource)RegionalRC.getResource("device");
//		Device test = (Device)resource.getResourceItem("26EFD10E3C525671013C525676D2000C");
//		System.out.println(test.getOid()+"==="+test.getDeviceID());
//		//地方资源容器请求资源
//		RegionalRC.loadResource(new String[]{"device1"});
//		IResource resource2 = (IResource)RegionalRC.getResource("device1");
//		Device test2 = (Device)resource2.getResourceItem("26EFD10E3C525671013C525676D2000C");
//		System.out.println(test.getOid()+"==="+test2.getDeviceID());
	}
	
}
