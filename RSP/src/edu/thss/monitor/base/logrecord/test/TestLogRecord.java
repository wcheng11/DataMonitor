package edu.thss.monitor.base.logrecord.test;

import org.junit.Test;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.sys.AppContext;

public class TestLogRecord {

	static{
		AppContext.getSpringContext();
	}
	
	@Test
	public void test() {
		LogRecord.info("test_info");
	}

}
