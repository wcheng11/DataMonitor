package edu.thss.monitor.base.dataaccess.imp;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import clojure.reflect.java__init;

import java.util.*;

import antlr.collections.List;


import edu.thss.monitor.base.dataaccess.imp.BaseDAO;
import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.pub.entity.AppLog;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.DeviceSeries;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;

public class TestDataAccess extends TestCase {

	ApplicationContext ctx;
	BaseDAO baseDAO;
	
	public TestDataAccess()
	{
		ctx = AppContext.getSpringContext();
		baseDAO = (BaseDAO)ctx.getBean("baseDAO");
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("testbegin");
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	@Test
	@Ignore
	public void testFindAttr() {
		Hashtable<String, String> attr = new Hashtable<String, String>();
		attr.put("operateResult", "'lihubin'");
		attr.put("operateDetail","'tsinghua'");
		baseDAO.findByAttr(AppLog.class, attr);
	}
	
	@Test
	@Ignore
	public void testConnectPool() throws RSPException
	{
		ConnectPool cPool = (ConnectPool)ctx.getBean("connectPool");
		//获取一个数据库连接
		//Connection cn1 = cPool.getCon();
		Connection connection;
		for (int i = 0; i < 100; i++)
		{ 
			connection = cPool.getCon();
			System.out.println(1);
		}
	}
	
	@Test
	@Ignore
	public void testSave1()
	{
		Device device = new Device();
		device.setDeviceID("T1234567890");
		device.setDeviceSeries(null);
		device.setSubscribePlans(null);
		device.setWarnPlans(null);
		baseDAO.save(device);
		System.out.println(device.getOid());
	}
	
	@Test
	@Ignore
	public void testSave2()
	{
		Device device = new Device();
		device.setDeviceID("QQ1234567890");
		device.setDeviceSeries(null);
		device.setSubscribePlans(null);
		device.setWarnPlans(null);
		baseDAO.save(device);
		System.out.println(device.getOid());
	}
	
	@Test
	@Ignore
	public void testSave3()
	{
		Device device =m new Device();
		device.setDeviceID("QQT1234567890");
		device.setDeviceSeries(null);
		device.setSubscribePlans(null);
		device.setWarnPlans(null);
		baseDAO.save(device);
		System.out.println(device.getOid());
	}
	@Test
	public void testUpdate()
	{
		Device device = (Device) baseDAO.findById(Device.class, "4028CC813C233091013C2330D3A20000");
		device.setDeviceID("t32149tt");
		baseDAO.update(device);
	}
	
	@Test
	public void testFindById()
	{
		Device device = (Device) baseDAO.findById(Device.class, "4028CC813C233091013C2330D46D0002");
		System.out.println(device.getDeviceID());
	}
	@Test
	public void testDelete()
	{
		Device device = (Device) baseDAO.findById(Device.class, "4028CC813C233091013C2330D46D0002");
		baseDAO.delete(Device.class, device.getOid());
	}
	@Test
	public void testFindByAttr()
	{
		Hashtable<String, String> attr = new Hashtable<String, String>();
		attr.put("deviceID", "'id7'");
		java.util.List<Object> list = baseDAO.findByAttr(Device.class, attr);
		System.out.println(list.size());
	}/**

	@Test
	public void testSaveBatch()
	{
		java.util.List<Object> devices = new ArrayList<Object>();
		for (int i = 0; i < 100; i++)
		{
			Device device = new Device();
			device.setDeviceID("id" + i);
			devices.add(device);
		}
		baseDAO.saveBatch(devices);
	}
	
	@Test
	public void testFindAll()
	{
		java.util.List<Object> list = baseDAO.findAll(Device.class);
		System.out.println(list.size());
		for (Object object : list) {
			Device device = (Device) object;
			System.out.println(device.getDeviceID());
		}
		System.out.println("end");
	}
	
	@Test
	public void testFindAllOverload()
	{

		java.util.List<Object> list = baseDAO.findAll(Device.class, 6, 770);
		System.out.println(list.size());
		for (Object object : list) {
			Device device = (Device) object;
			System.out.println(device.getDeviceID());
		}
		System.out.println("end");
	}**/
	@Test 
	public void testFindList()
	{
		Object[] queryParams = new Object[]{"id7"};
		java.util.List<Object> list = baseDAO.findList("select t from Device t where t.deviceID=?0", queryParams, 0, 90 );
		for (Object object : list) {
			Device device = (Device) object;
			System.out.println(device.getDeviceID());
		}
		System.out.println("end");
	}
	
}