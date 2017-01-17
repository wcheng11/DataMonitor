package edu.thss.monitor.rsp.service.subscribe;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlanResultTest {
	
	private PlanResult result = null;
	private Date beginTime = null;
	private Date endTime = null;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Before
	public void setUp() throws ParseException {
		beginTime = format.parse("2013-01-16 12:00:00");
		endTime = format.parse("2013-01-16 14:00:00");
		
		result = new PlanResult();
		result.setOid("2035");
		result.setBeginTime(beginTime);
		result.setEndTime(endTime);
		result.setPushType(5);
		result.setPushParam("push param");		
	}
	
	@After
	public void tearDown() {
		result = null;
	}
	
	@Test
	public void testSetterGetter() {
		assertEquals("2035", result.getOid());
		assertEquals(beginTime, result.getBeginTime());
		assertEquals(endTime, result.getEndTime());
		assertEquals(new Integer(5), result.getPushType());
		assertEquals("push param", result.getPushParam());
	}
	
	@Test
	public void testIsValid() throws ParseException{
		//FIXME what if not set beginTime or endTime?
		assertTrue(result.IsValid(beginTime));
		assertTrue(result.IsValid(endTime));
		
		Date testTime = format.parse("2013-01-15 12:00:00");
		assertFalse(result.IsValid(testTime));
		
		testTime = format.parse("2013-01-16 11:59:59");
		assertFalse(result.IsValid(testTime));

		testTime = format.parse("2013-01-16 13:59:59");
		assertTrue(result.IsValid(testTime));

		testTime = format.parse("2013-01-16 14:00:01");
		assertFalse(result.IsValid(testTime));
		
		testTime = format.parse("2013-01-17 12:00:00");
		assertFalse(result.IsValid(testTime));
	}
	
	@Test
	public void testIsOutOfDate() throws ParseException{
		assertFalse(result.IsOutOfDate(beginTime));
		assertFalse(result.IsOutOfDate(endTime));
		
		Date testTime = format.parse("2013-01-15 12:00:00");
		assertFalse(result.IsOutOfDate(testTime));

		testTime = format.parse("2013-01-16 14:00:01");
		assertTrue(result.IsOutOfDate(testTime));
	}

}
