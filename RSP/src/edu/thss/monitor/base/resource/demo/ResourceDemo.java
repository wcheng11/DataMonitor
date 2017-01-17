package edu.thss.monitor.base.resource.demo;

import java.util.Map;

import org.junit.Test;

import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 资源管理及同步流程演示
 * @author yangtao
 */
@SuppressWarnings("unused")
public class ResourceDemo {
	
	@SuppressWarnings("unchecked")
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
		String serverIP = "localhost";
		Integer serverPort = 7911;
		SyncNodeInfo serverInfo = new SyncNodeInfo(serverIP,serverPort);
		RegionalRC.setServerInfo(serverInfo);
		//注册
		RegionalRC.register();
		
		/*
		 * "地方"资源容器请求资源及测试
		 */
		//地方资源容器请求资源
		RegionalRC.loadResource(new String[]{"testMemory","testRedisMap","testRedisString","testRedisSet","testRedisObject"});
		
		/*
		 * 地方资源容器操作Redis资源测试
		 * 示例：资源类型（testMemory）、资源项key(1000000)
		 */
		//增加资源项
		RegionalRC.getResource("testMemory").addResourceItem("1000000","add");
		//获得资源项
		Map<String,String> findMap = (Map<String,String>)RegionalRC.getResource("testMemory").getResourceItem("1000000");
		//更新资源项
		RegionalRC.getResource("testMemory").updateResourceItem("1000000","update");
		//删除资源项
		RegionalRC.getResource("testMemory").deleteResourceItem("1000000");
		
		/*
		 * "地方"资源容器内存资源发送更改
		 * 示例：资源类型（testMemory）、资源项key(1000000)
		 */
		/*
		 * 设置更改信息,进行更改同步
		 */
		//更改内存资源
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"testMemory","1000000","修改对象");
		RegionalRC.syncChange(changeInfo);
		
	}
	
}
