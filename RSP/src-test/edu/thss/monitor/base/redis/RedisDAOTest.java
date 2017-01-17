package edu.thss.monitor.base.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import edu.thss.monitor.base.redis.imp.RedisConnPool;
import edu.thss.monitor.base.redis.imp.RedisDAO;
import edu.thss.monitor.base.resource.bean.ResourceConstant;
import edu.thss.monitor.pub.entity.AppLog;
import edu.thss.monitor.pub.sys.AppContext;

public class RedisDAOTest {

	private static RedisDAO redisDAO;
	public static final int TABLE_INDEX = 5;
	@BeforeClass
	public static void initTest()
	{
		redisDAO = (RedisDAO) AppContext.getSpringContext().getBean("redisDAO");
	}
	
	@Test
	public void testLoadTableObject()
	{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		AppLogTest[] testlog = new AppLogTest[10];
		for (int i = 0; i < testlog.length; i++)
		{
			testlog[i] = new AppLogTest();
			testlog[i].setOperateDetail("detail" + i);
			testlog[i].setOperateResult("result" + i);
			dataMap.put(String.valueOf(i), testlog[i]);
		}
		redisDAO.loadTable(TABLE_INDEX, ResourceConstant.CONFIG_RESOURCE_DATATYPE_OBJECT, dataMap);
		for (int i = 0; i < testlog.length; i++)
		{
			AppLogTest log = (AppLogTest) redisDAO.getObjectData(TABLE_INDEX, String.valueOf(i), AppLogTest.class);
			assertEquals(testlog[i], log);
		}
		redisDAO.clearTable(TABLE_INDEX);
	}
	
	@Test
	public void testLoadTableMap()
	{
		Map<String, HashMap> dataMap = new HashMap<String, HashMap>();
		AppLogTest[] testlog = new AppLogTest[10];
		for (int i = 0; i < testlog.length; i++)
		{
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("t1", "detail" + i);
			item.put("t2", "result" + i);
			dataMap.put(String.valueOf(i), item);
		}
		redisDAO.loadTable(TABLE_INDEX, ResourceConstant.CONFIG_RESOURCE_DATATYPE_MAP, dataMap);
		for (int i = 0; i < testlog.length; i++)
		{
			HashMap<String, String> item = (HashMap<String, String>) redisDAO.getMapData(TABLE_INDEX, String.valueOf(i));
			HashMap<String, String> toCompareHashMap = dataMap.get(String.valueOf(i));
			assertEquals(toCompareHashMap, item);
		}
		redisDAO.clearTable(TABLE_INDEX);
	}
	
	@Test
	public void testLoadTableSet()
	{
		Map<String, Set> dataMap = new HashMap<String, Set>();
		AppLogTest[] testlog = new AppLogTest[10];
		for (int i = 0; i < testlog.length; i++)
		{
			Set<String> set = new TreeSet<String>();
			set.add("detail" + i);
			set.add("result" + i);
			dataMap.put(String.valueOf(i), set);
		}
		redisDAO.loadTable(TABLE_INDEX, ResourceConstant.CONFIG_RESOURCE_DATATYPE_SET, dataMap);
		for (int i = 0; i < testlog.length; i++)
		{
			Set<String> set = redisDAO.getSetData(TABLE_INDEX, String.valueOf(i));
			assertEquals(dataMap.get(String.valueOf(i)), set);
		}
		redisDAO.clearTable(TABLE_INDEX);
	}
	
	@Test
	public void testLoadTableString()
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		AppLogTest[] testlog = new AppLogTest[10];
		for (int i = 0; i < testlog.length; i++)
		{
			String string = "detail" + i;
			dataMap.put(String.valueOf(i), string);
		}
		redisDAO.loadTable(TABLE_INDEX, ResourceConstant.CONFIG_RESOURCE_DATATYPE_STRING, dataMap);
		for (int i = 0; i < testlog.length; i++)
		{
			String t = redisDAO.getStringData(TABLE_INDEX, String.valueOf(i));
			assertEquals(dataMap.get(String.valueOf(i)), t);
		}
		redisDAO.clearTable(TABLE_INDEX);
	}
	
	@Test
	public void testMapOP()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("hello", "world");
		map.put("yes", "ok");
		redisDAO.addData(TABLE_INDEX, "map", map);
		Map<String, String> daoMap = redisDAO.getMapData(TABLE_INDEX, "map");
		assertEquals(map, daoMap);
		map.put("happy", "new year");
		redisDAO.updateData(TABLE_INDEX, "map", map);
		daoMap = redisDAO.getMapData(TABLE_INDEX, "map");
		assertEquals(map, daoMap);
		redisDAO.deleteData(TABLE_INDEX, "map");
		daoMap = redisDAO.getMapData(TABLE_INDEX, "map");
		assertEquals(daoMap, null);
	}
	
	@Test
	public void testSetOP()
	{
		Set<String> set = new TreeSet<String>();
		set.add("hello");
		set.add("yes");
		redisDAO.addData(TABLE_INDEX, "set", set);
		Set<String> daoSet = redisDAO.getSetData(TABLE_INDEX, "set");
		assertEquals(set, daoSet);
		set.add("happy");
		redisDAO.updateData(TABLE_INDEX, "set", set);
		daoSet = redisDAO.getSetData(TABLE_INDEX, "set");
		assertEquals(set, daoSet);
		redisDAO.deleteData(TABLE_INDEX, "set");
		daoSet = redisDAO.getSetData(TABLE_INDEX, "set");
		assertEquals(daoSet, null);
	}
	
	@Test
	public void testStringOP()
	{
		String string = "hello";
		redisDAO.addData(TABLE_INDEX, "string", string);
		String daoString = redisDAO.getStringData(TABLE_INDEX, "string");
		assertEquals(string, daoString);
		string = "happy";
		redisDAO.updateData(TABLE_INDEX, "string", string);
		daoString = redisDAO.getStringData(TABLE_INDEX, "string");
		assertEquals(string, daoString);
		redisDAO.deleteData(TABLE_INDEX, "string");
		daoString = redisDAO.getStringData(TABLE_INDEX, "string");
		assertEquals(daoString, null);
	}
	
	@Test
	public void testObjectOP()
	{
		AppLogTest object = new AppLogTest();
		object.setOperateDetail("detail");
		object.setOperateResult("result");
		redisDAO.addData(TABLE_INDEX, "object", object);
		AppLogTest daoObject = (AppLogTest) redisDAO.getObjectData(TABLE_INDEX, "object", AppLogTest.class);
		assertEquals(object, daoObject);
		object.setOperateDetail("detail2");
		redisDAO.updateData(TABLE_INDEX, "object", object);
		daoObject = (AppLogTest) redisDAO.getObjectData(TABLE_INDEX, "object", AppLogTest.class);
		assertEquals(object, daoObject);
		redisDAO.deleteData(TABLE_INDEX, "object");
		daoObject = (AppLogTest) redisDAO.getObjectData(TABLE_INDEX, "object", AppLogTest.class);
		assertEquals(daoObject, null);
	}
}
