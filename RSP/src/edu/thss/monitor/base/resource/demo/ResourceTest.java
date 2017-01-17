package edu.thss.monitor.base.resource.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import demo.framework.jpa.entity.TestEntity;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 资源管理测试
 * @author yangtao
 */
public class ResourceTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void start() throws InterruptedException, RSPException{
		
		/*
		 * "中央"资源容器初始化
		 */
		//初始化
//		CentralRC.init();
//		
//		//本地访问资源测试
		String resourceKey = "26efd2c73c1330dc013c1330e0be0000"; //测试用的key，请确认数据库中存在该条记录
//		TestEntity centralObj = (TestEntity)CentralRC.getResource("testMemory").getResourceItem(resourceKey);
//		Assert.assertNotNull(centralObj);
//		
//		Map<String,String> mapObj = (Map<String,String>)CentralRC.getResource("testRedisMap").getResourceItem(resourceKey);
//		System.out.println(mapObj.get("name"));
//		Set<String> setObj = (Set<String>)CentralRC.getResource("testRedisSet").getResourceItem("name");
//		System.out.println(setObj);
//		String strObj = (String)CentralRC.getResource("testRedisString").getResourceItem(resourceKey);
//		System.out.println(strObj);
//		Object obj = (Object)CentralRC.getResource("testRedisObject").getResourceItem(resourceKey);
//		System.out.println(obj);
		
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
		//地方资源容器访问内存资源测试
		TestEntity reginalObj = (TestEntity)RegionalRC.getResource("testMemory").getResourceItem(resourceKey);
		Assert.assertNotNull(reginalObj);
		System.out.println("取得内存中资源testMemory的数据对象:"+reginalObj);
		/**
		 * 地方资源容器访问Redis资源测试
		 */
		//Map型资源测试
		String mapResourceName = "testRedisMap";
		String addResourceKey = "addResourceKey";
			//增加
		Map<String,String> tmpMap = new HashMap<String,String>();
		tmpMap.put("key1", "value1");
		tmpMap.put("key2", "value2");
		RegionalRC.getResource(mapResourceName).addResourceItem(addResourceKey,tmpMap);
			//查找
		Map<String,String> findMap = (Map<String,String>)RegionalRC.getResourceItem(mapResourceName,addResourceKey);
		Assert.assertNotNull(findMap);
		System.out.println("查找到testRedisMap中数据:"+findMap);
			//更改
		tmpMap.put("key2", "updatedValue");
		RegionalRC.getResource(mapResourceName).updateResourceItem(addResourceKey, tmpMap);
		tmpMap = (Map<String,String>)RegionalRC.getResource(mapResourceName).getResourceItem(addResourceKey);
		System.out.println("更改testRedisMap中数据:"+tmpMap);
			//删除
		RegionalRC.getResource(mapResourceName).deleteResourceItem(addResourceKey);
		tmpMap = (Map<String,String>)RegionalRC.getResource(mapResourceName).getResourceItem(addResourceKey);
		System.out.println("删除testRedisMap中数据:"+tmpMap);
		
		//Set型资源测试
			//增加
		String setResourceName = "testRedisSet";
		Set<String> tmpSet = new HashSet<String>();
		tmpSet.add("value1");
		tmpSet.add("value2");
		RegionalRC.getResource(setResourceName).addResourceItem(addResourceKey,tmpSet);
			//查找
 		tmpSet = (Set<String>)RegionalRC.getResourceItem(setResourceName,addResourceKey);
		Assert.assertNotNull(tmpSet);
		System.out.println("查找到testRedisSet中数据:"+tmpSet);
			//更改
		tmpSet.remove("value2");
		tmpSet.add("updateValue");
		RegionalRC.getResource(setResourceName).updateResourceItem(addResourceKey, tmpSet);
		tmpSet = (Set<String>)RegionalRC.getResource(setResourceName).getResourceItem(addResourceKey);
 		System.out.println("更改testRedisSet中数据:"+tmpSet);
			//删除
		RegionalRC.getResource(setResourceName).deleteResourceItem(addResourceKey);
		tmpSet = (Set<String>)RegionalRC.getResource(setResourceName).getResourceItem(addResourceKey);
		System.out.println("删除testRedisSet中数据:"+tmpSet);
		
		//String型资源测试
		String stringResourceName = "testRedisString";
			//增加
		String tmpStr = "value1";
		RegionalRC.getResource(stringResourceName).addResourceItem(addResourceKey,tmpStr);
			//查找
		tmpStr = (String)RegionalRC.getResourceItem(stringResourceName,addResourceKey);
		Assert.assertNotNull(tmpStr);
		System.out.println("查找到testRedisString中数据:"+tmpStr);
			//更改
		RegionalRC.getResource(stringResourceName).updateResourceItem(addResourceKey, "updateValue");
		tmpStr = (String)RegionalRC.getResource(stringResourceName).getResourceItem(addResourceKey);
		System.out.println("更改testRedisString中数据:"+tmpStr);
			//删除
		RegionalRC.getResource(stringResourceName).deleteResourceItem(addResourceKey);
		tmpStr = (String)RegionalRC.getResource(stringResourceName).getResourceItem(addResourceKey);
		System.out.println("删除testRedisString中数据:"+tmpStr);
		
		//Object型资源测试
		String objectResourceName = "testRedisObject";
			//增加
		TestEntity tmpObj = new TestEntity();
		tmpObj.setId("111");
		tmpObj.setName("name");
		RegionalRC.getResource(objectResourceName).addResourceItem(addResourceKey,tmpObj);
			//查找
		tmpObj = (TestEntity)RegionalRC.getResourceItem(objectResourceName,addResourceKey);
		Assert.assertNotNull(tmpObj);
		System.out.println("查找到testRedisString中数据:"+tmpObj);
			//更改
		tmpObj.setName("updatedName");
		RegionalRC.getResource(objectResourceName).updateResourceItem(addResourceKey,tmpObj);
		tmpObj = (TestEntity)RegionalRC.getResource(objectResourceName).getResourceItem(addResourceKey);
		System.out.println("更改testRedisString中数据:"+tmpObj);
			//删除
		RegionalRC.getResource(objectResourceName).deleteResourceItem(addResourceKey);
		tmpObj = (TestEntity)RegionalRC.getResource(objectResourceName).getResourceItem(addResourceKey);
		System.out.println("删除testRedisString中数据:"+tmpObj);
		
//		tmpStr = (String)RegionalRC.getResourceItem("testRedisString",resourceKey);
//		System.out.println("取得testRedisString中数据:"+tmpStr);
//		Object tmpObj = (Object)RegionalRC.getResourceItem("testRedisObject",resourceKey);
//		Assert.assertNotNull(tmpObj);
//		System.out.println("取得testRedisObject中数据:"+tmpObj);
		
		/*
		 * "地方"资源容器发送更改
		 */
		reginalObj.setName("changedName");//修改内存数据对象的名称
		
		/*
		 * 设置更改信息,进行更改同步
		 */
		//更改内存资源
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"testMemory",resourceKey,reginalObj);
		RegionalRC.syncChange(changeInfo);
			//检查中央资源容器的对象状态
//		centralObj = (TestEntity)CentralRC.getResource("testMemory").getResourceItem(resourceKey);
//		Assert.assertEquals(centralObj.getName(), reginalObj.getName());
		//更改Redis资源
//		changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"testRedisString",resourceKey,"updatedString");
//		RegionalRC.syncChange(changeInfo);
	}
	
}
