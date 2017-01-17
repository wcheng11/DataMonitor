package edu.thss.monitor.base.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.framework.jpa.entity.TestEntity;

import sun.misc.Resource;

import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.bean.ResourceConstant;
import edu.thss.monitor.base.resource.bean.ResourceInfo;
import edu.thss.monitor.base.resource.imp.RedisResource;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.entity.AppLog;
import edu.thss.monitor.pub.exception.RSPException;


public class CentralRCTest {

	@BeforeClass 
	public static void initTest() throws RSPException, InterruptedException
	{
		CentralRC.init();
		//Thread.sleep(10000);
		SyncNodeInfo info = new SyncNodeInfo("127.0.0.1", 7911);
		RegionalRC.setServerInfo(info);
		Thread.sleep(1000);
		RegionalRC.register();
		RegionalRC.loadResource(new String[]{"testMemory","testRedisObject", "testRedisMap", "testRedisSet", "testRedisString", "testRedisAppLog"});
	}
	
	
	@Test
	public void sleep() throws InterruptedException
	{
		//Thread.sleep(Long.MAX_VALUE);
	}
/**
	@throws RSPException 
	 * @Test
	public void testRedis() throws InterruptedException, RSPException
	{
		Thread.sleep(1000);
		SyncNodeInfo info = new SyncNodeInfo("166.111.82.29", 7911);
		RegionalRC.setServerInfo(info);
		RegionalRC.register();
		Thread.sleep(1000);
		String resourceKey = "testServerChangeAdd";
		TestEntity entity = new TestEntity();
		entity.setId("213");
		entity.setName("name");
		entity.setValue("value");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testRedisObject", resourceKey, entity);
		RegionalRC.syncChange(changeInfo);

		Thread.sleep(Long.MAX_VALUE);
	}

	@Test
	public void testMemory() throws InterruptedException, RSPException
	{
		String resourceKey = "testServerChangeAdd";
		AppLog entity = new AppLog();
//		entity.setId("213");
//		entity.setName("name");
//		entity.setValue("value");
		ChangeInfo info = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testMemory", resourceKey, entity);
		//info.setResourceType(ResourceConstant.CONFIG_RESOURCE_TYPE_REDIS);
		RegionalRC.syncChange(info);

		Thread.sleep(Long.MAX_VALUE);
		//CentralRC.syncChange()
	}**/
	
	
	@Test
	public void testMemoryAdd() throws RSPException
	{
		AppLog appLog = new AppLog();
		appLog.setOperateDetail("detail");
		appLog.setOperateResult("result");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testMemory", "applog", appLog);		
		RegionalRC.syncChange(changeInfo);
		AppLog appLog1 = (AppLog) RegionalRC.getResource("testMemory").getResourceItem("applog");
		assertEquals(appLog1.getOperateDetail(), "detail");
		assertEquals(appLog1.getOperateResult(), "result");
	}
	
	@Test
	public void testRedisAdd() throws RSPException
	{
		TestEntity entity = new TestEntity();
		entity.setId("213");
		entity.setName("name");
		entity.setValue("value");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testRedisObject", "testEntity", entity);		
		RegionalRC.syncChange(changeInfo);
		TestEntity entity2 = (TestEntity) RegionalRC.getResource("testRedisObject").getResourceItem("testEntity");
		assertEquals(entity2.getId(), "213");
		assertEquals(entity2.getName(), "name");
		assertEquals(entity2.getValue(), "value");
	}
	
	@Test
	public void testServerChange() throws RSPException, InterruptedException
	{
		AppLog appLog = new AppLog();
		appLog.setOperateDetail("detail");
		appLog.setOperateResult("result");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testMemory", "applog", appLog);		
		changeInfo.setChangeSponsor("166.111.8.28");
		CentralRC.syncChange(changeInfo);
		Thread.sleep(1000);
		AppLog newAppLog = (AppLog) RegionalRC.getResource("testMemory").getResourceItem("applog");
		assertEquals(newAppLog.getOperateDetail(), appLog.getOperateDetail());
		assertEquals(newAppLog.getOperateResult(), appLog.getOperateResult());
	}
	
	@Test
	public void testMemoryUpdate() throws RSPException, InterruptedException
	{
		AppLog appLog = new AppLog();
		appLog.setOperateDetail("detail2");
		appLog.setOperateResult("result2");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE, "testMemory", "applog", appLog);		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		AppLog appLog1 = (AppLog) RegionalRC.getResource("testMemory").getResourceItem("applog");
		assertEquals(appLog1.getOperateDetail(), "detail2");
		assertEquals(appLog1.getOperateResult(), "result2");
	}
	
	@Test
	public void testRedisUpdate() throws RSPException, InterruptedException
	{
		TestEntity entity = new TestEntity();
		entity.setId("213a");
		entity.setName("namea");
		entity.setValue("valuea");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE, "testRedisObject", "testEntity", entity);		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		TestEntity entity2 = (TestEntity) RegionalRC.getResource("testRedisObject").getResourceItem("testEntity");
		assertEquals(entity2.getId(), "213a");
		assertEquals(entity2.getName(), "namea");
		assertEquals(entity2.getValue(), "valuea");
	}
	
	@Test
	public void testMemoryDelete() throws RSPException, InterruptedException
	{
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_DELETE, "testMemory", "applog", new AppLog());		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		AppLog appLog1 = (AppLog) RegionalRC.getResource("testMemory").getResourceItem("applog");
		assertEquals(appLog1, null);
	}
	
	@Test
	public void testRedisDelete() throws RSPException, InterruptedException
	{
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_DELETE, "testRedisObject", "testEntity", new TestEntity());		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		TestEntity entity2 = (TestEntity) RegionalRC.getResource("testRedisObject").getResourceItem("testEntity");
		assertEquals(entity2, null);
	}
	
	@Test 
	public void testRedisMap() throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testRedisMap", "map", map);		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		Map<String, String> map2 = (Map<String, String>) RegionalRC.getResourceItem("testRedisMap", "map");
		assertEquals(map, map2);
	}
	
	@Test 
	public void testRedisSet() throws Exception
	{
		Set<String> set = new TreeSet<String>();
		set.add("1");
		set.add("2");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testRedisSet", "set", set);		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		Set<String> set2 = (Set<String>) RegionalRC.getResourceItem("testRedisSet", "set");
		assertEquals(set, set2);
	}
	
	@Test
	public void testRedisString() throws Exception
	{
		String string = "tt";
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testRedisString", "string", string);		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		String string2 = (String) RegionalRC.getResourceItem("testRedisString", "string");
		assertEquals(string, string2);
	}
	
	@Test
	public void testRedisConfig() throws RSPException, InterruptedException
	{
		AppLog appLog = new AppLog();
		appLog.setOperateDetail("detail2");
		appLog.setOperateResult("result2");
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testRedisAppLog", "applog", appLog);		
		RegionalRC.syncChange(changeInfo);
		Thread.sleep(1000);
		AppLog appLog1 = (AppLog) RegionalRC.getResource("testRedisAppLog").getResourceItem("applog");
		assertEquals(appLog1.getOperateDetail(), "detail2");
		assertEquals(appLog1.getOperateResult(), "result2");
	}
}
