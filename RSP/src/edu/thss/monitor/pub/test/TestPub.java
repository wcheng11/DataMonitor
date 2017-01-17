package edu.thss.monitor.pub.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import edu.thss.monitor.pub.dao.impl.DeviceSeriesDAO;
import edu.thss.monitor.pub.dao.impl.ProTreatClassDAO;
import edu.thss.monitor.pub.dao.impl.ProtocolDAO;
import edu.thss.monitor.pub.dao.impl.TemplateDAO;
import edu.thss.monitor.pub.entity.AppLog;
import edu.thss.monitor.pub.entity.DeviceSeries;
import edu.thss.monitor.pub.entity.ProTreatClass;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.sys.AppContext;

public class TestPub {

	protected static ApplicationContext context = AppContext.getSpringContext();

	@Test
	public void testRelationShips() {

		this.testManyToMany();
		this.testEmbeddedManyToMany();
//		this.testPltId();
	}

	private void testEmbeddedManyToMany() {
//		TemplateDAO tDao = (TemplateDAO) context.getBean("templateDAO");
//		ProtocolDAO pDao = (ProtocolDAO) context.getBean("protocolDAO");
//
//		Template t1 = new Template();
//		t1.setTemplateName("aaaa");
//		Template t2 = (Template) tDao.save(t1);
//		Template t3 = (Template) tDao.findById(Template.class, t2.getOid()
//				.toUpperCase());
//
//		Protocol p = new Protocol();
//		p.setProtocalName("zxy");
//		Protocol p2 = (Protocol) pDao.save(p);
//		Protocol p3 = (Protocol) pDao.findById(Protocol.class, p2.getOid()
//				.toUpperCase());
//
//		List<Template> list = new ArrayList<Template>();
//		list.add(t3);
//
//		p3.setTemplateList(list);
//		pDao.update(p3);
//
//		Protocol p4 = (Protocol) pDao.findById(Protocol.class, p3.getOid());
//		Template t4 = (Template) tDao.findById(Template.class, t3.getOid());
//
//		int size1 = p4.getTemplateList().size();
//		int size2 = p4.getPro2TempList().size();
//
////		int size3 = t4.getProtocolList().size();
////		int size4 = t4.getPro2TempList().size();
//		System.out.println("Template list size:" + size1);
//		System.out.println("Pro2Temp list size:" + size2);
//		Assert.assertEquals(size1, size2);
//
////		System.out.println("Protocol list size:" + size3);
////		System.out.println("Pro2Temp list size:" + size4);
////		Assert.assertEquals(size3, size4);
//		
//		System.out.println("Protocol name: " + p4.getPro2TempList().get(0).getProtocol().getProtocalName());

	}

	private void testManyToMany() {
		// 协议处理方案
		ProTreatClass ptc = new ProTreatClass();
		ptc.setAcceptedParameter("a");
		ptc.setClassName("b");
		ptc.setTreatName("3");
		ptc.setTreatType(2);

		ProTreatClassDAO ptcDao = (ProTreatClassDAO) context
				.getBean("proTreatClassDAO");
		ProTreatClass ptc2 = (ProTreatClass) ptcDao.save(ptc);

		System.out.println("proTreatClass UUID:" + ptc2.getOid());

		// 协议
		Protocol p = new Protocol();
		p.setProtocalName("abcd");
		p.setTemplateRecogClass(ptc2);

		ProtocolDAO pDao = (ProtocolDAO) context.getBean("protocolDAO");
		Protocol p2 = (Protocol) pDao.save(p);
		Protocol p3 = (Protocol) pDao.findById(Protocol.class, p2.getOid()
				.toUpperCase());

		System.out.println("Protocol2 UUID:" + p2.getOid());
		System.out.println("Protocol3 UUID:" + p3.getOid());

		// 设备系列
		DeviceSeries ds = new DeviceSeries();
		ds.setSeriesName("ds1");
		ds.setSeriesRule("aaaa");

		DeviceSeriesDAO dsDao = (DeviceSeriesDAO) context
				.getBean("deviceSeriesDAO");
		DeviceSeries ds2 = (DeviceSeries) dsDao.save(ds);
		DeviceSeries ds3 = (DeviceSeries) dsDao.findById(DeviceSeries.class,
				ds2.getOid().toUpperCase());

		System.out.println("DeviceSeries2 UUID:" + ds2.getOid());
		System.out.println("DeviceSeries3 UUID:" + ds3.getOid());

		// List<Protocol> list = new ArrayList<Protocol>();
		// list.add(p3);
		//		
		// ds3.setProtocolList(list);
		// dsDao.update(ds3);

		List<DeviceSeries> list = new ArrayList<DeviceSeries>();
		list.add(ds3);

//		p3.setDeviceSeriesList(list);
//		dsDao.update(p3);

		DeviceSeries ds4 = (DeviceSeries) dsDao.findById(DeviceSeries.class,
				ds2.getOid().toUpperCase());

//		System.out.println("Device series list size:"
//				+ p3.getDeviceSeriesList().size());
	}
	
	private void testPltId(){
		for (int i = 0; i < 10; i++) {
			@SuppressWarnings("unused")
			AppLog appLog = new AppLog();
		}
	}

}
