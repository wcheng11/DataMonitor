package edu.thss.monitor.base.resource.demo;

import org.junit.Test;

import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 资源管理测试
 * @author yangtao
 */
public class ResourceTest2 {
	
//	@Test
//	public void testNativeSql(){
//		IBaseDAO dao = (IBaseDAO)AppContext.getSpringContext().getBean("baseDAO");
//		List<Object> obj = dao.findListByNativeSql("select t.name,t.value from TB_TEST t");
//		System.out.println("test");
//	}
	
	@Test
	public void start() throws InterruptedException, RSPException{
		
		/*
		 * "中央"资源容器初始化
		 */
		//初始化
//		CentralRC.init();
		
		/*
		 * "地方"资源容器初始化
		 */
		//设置服务器信息
		System.out.println("====================");
		String serverIP = "localhost";
		Integer serverPort = 7911;
		SyncNodeInfo serverInfo = new SyncNodeInfo(serverIP,serverPort);
		RegionalRC.setServerInfo(serverInfo);
		//注册
		RegionalRC.register();
////		/*
////		 * "地方"资源容器请求资源及测试
////		 */
////		//地方资源容器请求资源
		RegionalRC.loadResource(new String[]{"device"});
		IResource resource = (IResource)RegionalRC.getResource("device");
		Device test = (Device)resource.getResourceItem("26EFD10E3C525671013C525676D2000C");
		System.out.println(test.getOid()+"==="+test.getDeviceID());
		
	}
	
}
