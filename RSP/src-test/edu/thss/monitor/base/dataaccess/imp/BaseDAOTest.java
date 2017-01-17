package edu.thss.monitor.base.dataaccess.imp;

import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.pub.dao.impl.DeviceDAO;
import edu.thss.monitor.pub.entity.*;

public class BaseDAOTest {
	protected static ApplicationContext context = 
		new ClassPathXmlApplicationContext(
				new String[]{"META-INF/beans-base.xml"}
		);

	@Before
	public void setUp() throws Exception {
	}

/*	@Test
	public void testAppLog() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testBasicConfig() {
		BasicConfig testBasicConfig = new BasicConfig();
		testBasicConfig.setAttribute("BasicConfig_attribute");
		testBasicConfig.setValue("BasicConfig_Value");
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testBasicConfig);
	}
	@Test
	public void testBomChangeRec() {
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		
		TemplatePara tp = new TemplatePara();
		tp.setParameterName("parameterName");
		tp.setParameterType(new Integer(1));
		tp.setLength(new Long(128));
		TemplatePara tpnew = (TemplatePara)baseDAO.save(tp);
		TemplatePara tpnew2 = (TemplatePara)baseDAO.findById(TemplatePara.class, tpnew.getOid().toUpperCase());
		
		Device d = new Device();
		DeviceDAO dao = (DeviceDAO) context.getBean("deviceDAO");
		Device d2 = (Device) dao.save(d);
		
		BomChangeRec testBomChangeRec = new BomChangeRec();
		testBomChangeRec.setBeforeValue("beforeValue");
		testBomChangeRec.setTimeStamp(new Date());
		testBomChangeRec.setAfterValue("afterValue");
		testBomChangeRec.setChangedComponent(tpnew2);
		testBomChangeRec.setDeviceID(d2.getOid().toUpperCase());
//		testBomChangeRec.setDeviceID("DeviceID");
		
		baseDAO.save(testBomChangeRec);
	}
	@Test
	public void testCode() {
		Code testCode = new Code();
		testCode.setMajorCode(new Integer(1));
		testCode.setName("Code_Name");
		testCode.setSubcode(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testCode);
	}
	@Test
	public void testCustomerInfo() {
		CustomerInfo testCustomerInfo = new CustomerInfo();
		testCustomerInfo.setCustomerName("customerName");
		testCustomerInfo.setEmail("email");
		testCustomerInfo.setIdentityCard("identityCard");
		testCustomerInfo.setTelephone("13838384438");
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testCustomerInfo);
	}
	@Test  //等待庄雪吟修改完成
	public void testDevice() {
		Device testDevice = new Device();
		DeviceDAO dao = (DeviceDAO) context.getBean("deviceDAO");
		Device d2 = (Device) dao.save(d);
		testDevice.setDeviceID(deviceID);
		testDevice.setTimeStamp(new Date());
		testDevice.setOperateType(new Integer(1));
		testDevice.setOperateResult("AppLog_OperateResult");
		testDevice.setOperateDetail("AppLog_operateDetail");
		testDevice.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testDevice);
	}
	@Test
	public void testDeviceSeries() {
		DeviceSeries testDeviceSeries = new DeviceSeries();
		testDeviceSeries.setSeriesName("seriesName");
		testDeviceSeries.setSeriesRule("seriesRule");
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testDeviceSeries);
	}*/
	@Test
	public void testParaTreatClass() {
		ParaTreatClass testParaTreatClass = new ParaTreatClass();
		testParaTreatClass.setClassName("className");
//		testParaTreatClass.setTreatName("我就要试试多少个字符我就要试试多少个字符我就要试试多少个字符我就");
//		testParaTreatClass.setTreatName("我就要试试多少个字符我就要试试多少个字符我就要试试多少个字符我yy");
//		testParaTreatClass.setTreatName("12345678901234567890123456789012");
//		testParaTreatClass.setTreatName("123456789012345678901234567890123");
//		testParaTreatClass.setTreatName("あの日文yy");
//		testParaTreatClass.setTreatName("");
		testParaTreatClass.setTreatName(null);
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testParaTreatClass);
	}
	@Test
	public void testPerfoMonitor() {
		PerfoMonitor testPerfoMonitor = new PerfoMonitor();
		testPerfoMonitor.setStormDeliveryNum(new Long(12));
		testPerfoMonitor.setTimeStamp(new Date());
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testPerfoMonitor);
	}
	@Test
	public void testPlatformManageLog() {
		PlatformManageLog testPlatformManageLog = new PlatformManageLog();
		testPlatformManageLog.setEventType(new Integer(1));
		testPlatformManageLog.setTimeStamp(new Date());
//		testPlatformManageLog.setMpmEventTypeMC(new Integer(1));
		testPlatformManageLog.setRecordContent("recordContent");
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testPlatformManageLog);
	}
	@Test
	public void testProducerInfo() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testProtocol() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testProTreatClass() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testRawData() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testRealtimeLog() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testService() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testServiceLog() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testServicePack() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testSubPlan() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testSynBom() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testSynConfig() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testTemplate() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testTemplatePara() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testTempTreClass() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testTopoComp() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testTopology() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testUserBill() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testWarnAlgClass() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}
	@Test
	public void testWarnPlan() {
		AppLog testAppLog = new AppLog();
		testAppLog.setUserid("AppLog_ID");
		testAppLog.setTimeStamp(new Date());
		testAppLog.setOperateType(new Integer(1));
		testAppLog.setOperateResult("AppLog_OperateResult");
		testAppLog.setOperateDetail("AppLog_operateDetail");
//		testAppLog.setAppOperateTypeMC(new Integer(1));
		
		BaseDAO baseDAO = (BaseDAO)context.getBean("baseDAO");
		baseDAO.save(testAppLog);
	}

}
