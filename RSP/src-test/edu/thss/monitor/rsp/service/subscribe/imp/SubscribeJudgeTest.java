package edu.thss.monitor.rsp.service.subscribe.imp;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.thss.monitor.base.resource.CentralRC;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.subscribe.PlanResult;

public class SubscribeJudgeTest {
	
	private SubscribeJudge judge = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CentralRC.init();
		SyncNodeInfo info = new SyncNodeInfo("127.0.0.1", 7911);
		RegionalRC.setServerInfo(info);
		Thread.sleep(1000);
		RegionalRC.register();
	}

	@Before
	public void setUp() throws Exception {
		judge = new SubscribeJudge();
	}

	@After
	public void tearDown() throws Exception {
		judge = null;
	}
	
	@Test
	public void testJudgeDeviceIsSubscribedFromDatabase() {
		//FIXME: encounter bug
		String deviceID = "2035";
		System.out.println(judge.judgeDeviceIsSubscribedFromDatabase(deviceID));
		fail("Not yet implemented");	
	}
	
	@Test
	public void testGetSubscribeJudge() throws ClassCastException, RSPException, ParseException {
		// Configure parsed data packet
		ParsedDataPacket data = new ParsedDataPacket();
		Device device = new Device();
		String deviceID = "2035";
		
		device.setDeviceID(deviceID);
		data.setDevice(device);
		
		List<JudgeResult> results = judge.getSubscribeJudge(data);
		assertNotNull(results);		
	}
	
	@Test
	public void testGetSubPlanByDevice() throws RSPException {
		//FIXME: encounter bug
		String deviceID = "2035";
	
		judge.getSubPlanByDevice(deviceID);		
	}
	
	@Test
	public void testGetSubPlanFromEntry() throws RSPException, ParseException {
		String key = "2035";
		String beginDate = "2012-03-05 12:00:00";
		String endDate = "2012-03-05 14:00:00";
		String workStatusName1 = "temper";
		String value = beginDate +","+endDate + "," + workStatusName1;
		
		Map.Entry<String, String> entry = new AbstractMap.SimpleEntry(key, value);

		PlanResult pr = judge.getSubPlanFromEntry(entry);
		assertEquals("Mon Mar 05 12:00:00 GMT+08:00 2012", pr.getBeginTime().toString());
		assertEquals("Mon Mar 05 14:00:00 GMT+08:00 2012", pr.getEndTime().toString());
		assertEquals(key, pr.getOid());
	}
}
