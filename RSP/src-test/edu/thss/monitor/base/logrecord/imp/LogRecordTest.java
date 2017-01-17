/**
 * 
 */
package edu.thss.monitor.base.logrecord.imp;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.thss.monitor.pub.dao.ILogDAO;
import edu.thss.monitor.pub.entity.AppLog;
import edu.thss.monitor.pub.entity.PlatformManageLog;
import edu.thss.monitor.pub.entity.RealtimeLog;
import edu.thss.monitor.pub.entity.ServiceLog;
import edu.thss.monitor.pub.sys.AppContext;

/**
 * @author fx
 *
 */
public class LogRecordTest {
	ILogDAO dao;
	PlatformManageLog pl;
	ServiceLog sl;
	RealtimeLog rl;
	AppLog al;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dao = (ILogDAO)AppContext.getSpringContext().getBean("logDAO");
		
		pl = new PlatformManageLog(1, "测试日志之Groovy的崛起：PlatformManageLog", new Date());
		sl = new ServiceLog(2, "测试日志之Scala命名很怪异：ServiceLog", new Date());
		rl = new RealtimeLog(3, "测试日志之如何用Scala单元测试Java：RealtimeLog", new Date());
		al = new AppLog(null, 4, null, "测试日志之如何用Groovy单元测试Java：AppLog", new Date());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		dao.delete(pl.getClass(), pl.getOid());
		dao.delete(sl.getClass(), sl.getOid());
		dao.delete(rl.getClass(), rl.getOid());
		dao.delete(al.getClass(), al.getOid());
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.logrecord.imp.LogRecord#writeLog(edu.thss.monitor.base.logrecord.imp.Log)}.
	 */
	@Test
	public final void testWriteLog() {
		LogRecord.writeLog(pl);
		LogRecord.writeLog(sl);
		LogRecord.writeLog(rl);
		LogRecord.writeLog(al);
		
		Log plOrl = (Log)dao.findById(pl.getClass(), pl.getOid().toUpperCase());
		Log slOrl = (Log)dao.findById(sl.getClass(), sl.getOid().toUpperCase());
		Log rlOrl = (Log)dao.findById(rl.getClass(), rl.getOid().toUpperCase());
		Log alOrl = (Log)dao.findById(al.getClass(), al.getOid().toUpperCase());
	
		assertEquals(pl.getOid().toUpperCase(),
				((PlatformManageLog)plOrl).getOid());
		assertEquals(sl.getOid().toUpperCase(),
				((ServiceLog)slOrl).getOid());
		assertEquals(rl.getOid().toUpperCase(),
				((RealtimeLog)rlOrl).getOid());
		assertEquals(al.getOid().toUpperCase(),
				((AppLog)alOrl).getOid());
	}

}
