package edu.thss.monitor.rsp.service.consistency;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.thss.monitor.base.redis.IRedisDAO;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ResourceInfo;
import edu.thss.monitor.base.resource.imp.RedisResource;
import edu.thss.monitor.pub.dao.IDeviceDAO;
import edu.thss.monitor.pub.dao.ITemplateParaDAO;
import edu.thss.monitor.pub.entity.BomChangeRec;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.DeviceSeries;
import edu.thss.monitor.pub.entity.SynBom;
import edu.thss.monitor.pub.entity.SynBomItem;
import edu.thss.monitor.pub.entity.SynConfig;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.sys.AppContext;

public class BOMSyncManagerTest1 {
	IDeviceDAO ddao;
	ITemplateParaDAO tdao;
	
	ParsedDataPacket PT1DP,PT2DP,PT3DP,PT4DP;
	
	DeviceSeries PTSeries;//泵车
	
//	Device[] dArray = new Device[3];
	
	List<Device> PTList;//PTSeries' DeviceList
	List<SynConfig> PTSynConfigList;//PTSeries' PTSynConfigList
	List<SynBomItem> PTSynBomItemList1,PTSynBomItemList2,PTSynBomItemList3;//PTSynBom's SynBomItemList
	
	SynConfig PTDeviceID,PTSYMT,PTSYMC,PTSYLD,PTRC;//RC:遥控器；DAS：倾角传感器
	
	Device PT1,PT2,PT3;
	SynBom PTSynBom1,PTSynBom2,PTSynBom3;
	SynBomItem DeviceID1,DeviceID2,DeviceID3;
	SynBomItem SYMT1,SYMT2,SYMT3,SYMT4;
	SynBomItem SYMC1,SYMC2,SYMC3;
	SynBomItem SYLD1,SYLD2,SYLD3,SYLD4;
	SynBomItem RC1,RC2,RC3;

	TemplatePara PTDeviceIDSynComponent;
	TemplatePara PTSYMTSynComponent;
	TemplatePara PTSYMCSynComponent;
	TemplatePara PTSYLDSynComponent;
	TemplatePara PTRCSynComponent;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ddao = (IDeviceDAO)AppContext.getSpringContext().getBean("deviceDAO");
		tdao = (ITemplateParaDAO)AppContext.getSpringContext().getBean("templateParaDAO");
		
		PTSeries =new DeviceSeries();
		
		PTSeries.setSeriesName("PTSeries");
		
		PT1 =new Device();
		PT2 =new Device();
		PT3 =new Device();
		
//		for(int i=0;i<dArray.length;i++){
//			Device d = new Device();
//			d.setDeviceID("PT"+i);
//			dArray[i] = d;
//		}
		
		PT1.setDeviceID("PT1");
		PT2.setDeviceID("PT2");
		PT3.setDeviceID("PT3");
		
		PT1.setDeviceSeries(PTSeries);
		PT2.setDeviceSeries(PTSeries);
		PT3.setDeviceSeries(PTSeries);
		
		PTDeviceIDSynComponent = new TemplatePara();
		PTSYMTSynComponent =new TemplatePara();
		PTSYMCSynComponent =new TemplatePara();
		PTSYLDSynComponent =new TemplatePara();
		PTRCSynComponent= new TemplatePara();
		
		PTDeviceIDSynComponent.setParameterID("PTDeviceIDSynItem");
		PTSYMTSynComponent.setParameterID("PTSYMTSynItem");
		PTSYMCSynComponent.setParameterID("PTSYMCSynItem");
		PTSYLDSynComponent.setParameterID("PTSYLDSynItem");
		PTRCSynComponent.setParameterID("PTRCSynItem");
		
		
		tdao.save(PTDeviceIDSynComponent);
		tdao.save(PTSYMTSynComponent);
		tdao.save(PTSYMCSynComponent);
		tdao.save(PTSYLDSynComponent);
		tdao.save(PTRCSynComponent);

		//SynConfig的初始化
		PTDeviceID =initSynConfig(new SynConfig(), 1, PTSeries, PTDeviceIDSynComponent);
		PTSYMT =initSynConfig(new SynConfig(), 2, PTSeries, PTSYMTSynComponent);
		PTSYMC =initSynConfig(new SynConfig(), 3, PTSeries, PTSYMCSynComponent);
		PTSYLD =initSynConfig(new SynConfig(), 3, PTSeries, PTSYLDSynComponent);
		PTRC =initSynConfig(new SynConfig(),3,PTSeries, PTRCSynComponent);
		
		PTSynBom1 =new SynBom();
		PTSynBom2 =new SynBom();
		PTSynBom3 =new SynBom();
		
		//PTSynBom's SynBomItemList
		PTSynBomItemList1 =new ArrayList<SynBomItem>();
		PTSynBomItemList1.add(DeviceID1);
		PTSynBomItemList1.add(SYMT1);
		PTSynBomItemList1.add(SYMC1);
		PTSynBomItemList1.add(SYLD1);
		PTSynBomItemList1.add(RC1);
		PTSynBom1.setSyncBomItemList(PTSynBomItemList1);
		PTSynBomItemList2 =new ArrayList<SynBomItem>();
		PTSynBomItemList2.add(DeviceID2);
		PTSynBomItemList2.add(SYMT2);
		PTSynBomItemList2.add(SYMC2);
		PTSynBomItemList2.add(SYLD2);
		PTSynBomItemList2.add(RC2);
		PTSynBom2.setSyncBomItemList(PTSynBomItemList2);
		PTSynBomItemList3 =new ArrayList<SynBomItem>();
		PTSynBomItemList3.add(DeviceID3);
		PTSynBomItemList3.add(SYMT3);
		PTSynBomItemList3.add(SYMC3);
		PTSynBomItemList3.add(SYLD3);
		PTSynBomItemList3.add(RC3);
		PTSynBom3.setSyncBomItemList(PTSynBomItemList3);
		
		//DeviceID
		PTSynBom1.setDevice(PT1.getDeviceID());
		PTSynBom2.setDevice(PT2.getDeviceID());
		PTSynBom3.setDevice(PT3.getDeviceID());
		
		ddao.save(PTSynBom1);
		ddao.save(PTSynBom2);
		ddao.save(PTSynBom3);
		
		//SynBomItem初始化
		DeviceID1= initSynBomItem(new SynBomItem(),"PT1", 1,PTSynBom1, PTDeviceIDSynComponent);
		SYMT1= initSynBomItem(new SynBomItem(),"symt1", 2,PTSynBom1, PTSYMTSynComponent);
		SYMC1= initSynBomItem(new SynBomItem(),"symc1", 3, PTSynBom1, PTSYMCSynComponent);
		SYLD1= initSynBomItem(new SynBomItem(),"syld1", 3,PTSynBom1, PTSYLDSynComponent);
		RC1= initSynBomItem(new SynBomItem(),"rc1", 3,PTSynBom1, PTRCSynComponent);

		DeviceID2= initSynBomItem(new SynBomItem(),"PT2", 1,PTSynBom2, PTDeviceIDSynComponent);
		SYMT2= initSynBomItem(new SynBomItem(),"symt2", 2,PTSynBom2, PTSYMTSynComponent);
		SYMC2= initSynBomItem(new SynBomItem(),"symc2", 3,PTSynBom2, PTSYMCSynComponent);
		SYLD2= initSynBomItem(new SynBomItem(),"syld2", 3,PTSynBom2, PTSYLDSynComponent);
		RC2= initSynBomItem(new SynBomItem(),"rc2", 3,PTSynBom2, PTRCSynComponent);

		DeviceID3= initSynBomItem(new SynBomItem(),"PT3", 1,PTSynBom3, PTDeviceIDSynComponent);
		SYMT3= initSynBomItem(new SynBomItem(),"symt3", 2,PTSynBom3, PTSYMTSynComponent);
		SYMC3= initSynBomItem(new SynBomItem(),"symc3", 3,PTSynBom3, PTSYMCSynComponent);
		SYLD3= initSynBomItem(new SynBomItem(),"syld3", 3,PTSynBom3, PTSYLDSynComponent);
		RC3= initSynBomItem(new SynBomItem(),"rc3", 3,PTSynBom3, PTRCSynComponent);
		
		SYMT4= initSynBomItem(new SynBomItem(),"symt4",2,null,PTSYMTSynComponent);
		SYLD4= initSynBomItem(new SynBomItem(),"syld4", 3,null, PTSYLDSynComponent);
		
		saveObjArray(new Object[]{DeviceID1,DeviceID2,DeviceID3,
				SYMT1,SYMT2,SYMT3,SYMT4,SYMC1,SYMC2,SYMC3,
				SYLD1,SYLD2,SYLD3,SYLD4,RC1,RC2,RC3});
		
		tdao.save(PTSeries);
		
		ddao.save(PT1);
		ddao.save(PT2);
		ddao.save(PT3);
		
		tdao.save(PTDeviceID);
		tdao.save(PTSYMT);
		tdao.save(PTSYMC);
		tdao.save(PTSYLD);
		tdao.save(PTRC);

		//PTSeries' DeviceList
		PTList =new ArrayList<Device>();
		PTList.add(PT1);
		PTList.add(PT2);
		PTList.add(PT3);
//		PTSeries.setDeviceList(PTList);
		
		//PTSeries' PTSynConfigList
		PTSynConfigList = new ArrayList<SynConfig>();
		PTSynConfigList.add(PTDeviceID);
		PTSynConfigList.add(PTSYMT);
		PTSynConfigList.add(PTSYMC);
		PTSynConfigList.add(PTSYLD);
		PTSynConfigList.add(PTRC);
		PTSeries.setSynConfigList(PTSynConfigList);
		
		
		//设置device资源对象
		ResourceInfo rDevice =new ResourceInfo();
		rDevice.setDataType("object");
		rDevice.setClassObj(Device.class);
		RedisResource deviceResource = new RedisResource(13);
		deviceResource.resourceInfo = rDevice;
		RegionalRC.resourceMap.put("device", deviceResource);
		
		//设置deviceID到device的映射
		RegionalRC.getResource("device").addResourceItem(PT1.getDeviceID(), PT1);
		RegionalRC.getResource("device").addResourceItem(PT2.getDeviceID(), PT2);
		RegionalRC.getResource("device").addResourceItem(PT3.getDeviceID(), PT3);
		
		
		//设置synBom资源对象
		ResourceInfo rSynBom = new ResourceInfo();
		rSynBom.setDataType("map");
		RedisResource sunBomResource = new RedisResource(11);
		sunBomResource.resourceInfo = rSynBom;
		RegionalRC.resourceMap.put("syncBOM",sunBomResource);
		
		
		//设置泵车1syncBom
		Map PT1SyncBom = new HashMap();
		PT1SyncBom.put(PTDeviceID.getSynComponent().getParameterID(), DeviceID1.getSynValue());
		PT1SyncBom.put(PTSYMT.getSynComponent().getParameterID(), SYMT1.getSynValue());
		PT1SyncBom.put(PTSYMC.getSynComponent().getParameterID(), SYMC1.getSynValue());
		PT1SyncBom.put(PTSYLD.getSynComponent().getParameterID(), SYLD1.getSynValue());
		PT1SyncBom.put(PTRC.getSynComponent().getParameterID(), RC1.getSynValue());
		RegionalRC.getResource("syncBOM").addResourceItem(PT1.getDeviceID(), PT1SyncBom);
		//设置泵车2synBom
		Map PT2SyncBom = new HashMap();
		PT2SyncBom.put(PTDeviceID.getSynComponent().getParameterID(), DeviceID2.getSynValue());
		PT2SyncBom.put(PTSYMT.getSynComponent().getParameterID(), SYMT2.getSynValue());
		PT2SyncBom.put(PTSYMC.getSynComponent().getParameterID(), SYMC2.getSynValue());
		PT2SyncBom.put(PTSYLD.getSynComponent().getParameterID(), SYLD2.getSynValue());
		PT2SyncBom.put(PTRC.getSynComponent().getParameterID(), RC2.getSynValue());
		RegionalRC.getResource("syncBOM").addResourceItem(PT2.getDeviceID(), PT2SyncBom);
		//设置泵车3synBom
		Map PT3SyncBom = new HashMap();
		PT3SyncBom.put(PTDeviceID.getSynComponent().getParameterID(), DeviceID3.getSynValue());
		PT3SyncBom.put(PTSYMT.getSynComponent().getParameterID(), SYMT3.getSynValue());
		PT3SyncBom.put(PTSYMC.getSynComponent().getParameterID(), SYMC3.getSynValue());
		PT3SyncBom.put(PTSYLD.getSynComponent().getParameterID(), SYLD3.getSynValue());
		PT3SyncBom.put(PTRC.getSynComponent().getParameterID(), RC3.getSynValue());
		RegionalRC.getResource("syncBOM").addResourceItem(PT3.getDeviceID(), PT3SyncBom);
		
		//设置泵车1baseInfoMap
		Map PT1BaseInfoMap = new HashMap();
		PT1BaseInfoMap.put(PTSYMT.getSynComponent().getParameterID(), SYMT4.getSynValue());  //改动
		
		//设置泵车1workStatusMap
		Map PT1WorkStatusMap = new HashMap();
		PT1WorkStatusMap.put(PTDeviceID.getSynComponent().getParameterID(), DeviceID1.getSynValue());
		PT1WorkStatusMap.put(PTSYMC.getSynComponent().getParameterID(), SYMC1.getSynValue());
		PT1WorkStatusMap.put(PTSYLD.getSynComponent().getParameterID(), SYLD4.getSynValue());  //改动
		PT1WorkStatusMap.put(PTRC.getSynComponent().getParameterID(), RC1.getSynValue());
		
		//设置泵车2baseInfoMap
		Map PT2BaseInfoMap = new HashMap();
		PT2BaseInfoMap.put(PTSYMT.getSynComponent().getParameterID(), SYMT2.getSynValue());
		
		//设置泵车2workStatusMap
		Map PT2WorkStatusMap = new HashMap();
		PT2WorkStatusMap.put(PTDeviceID.getSynComponent().getParameterID(), DeviceID2.getSynValue());
		PT2WorkStatusMap.put(PTSYMC.getSynComponent().getParameterID(), SYMC1.getSynValue());  //改动
		PT2WorkStatusMap.put(PTSYLD.getSynComponent().getParameterID(), SYLD2.getSynValue());
		PT2WorkStatusMap.put(PTRC.getSynComponent().getParameterID(), RC2.getSynValue());
		
		//设置泵车3baseInfoMap
		Map PT3BaseInfoMap = new HashMap();
		PT3BaseInfoMap.put(PTSYMT.getSynComponent().getParameterID(), SYMT1.getSynValue());   //改动
		
		//设置泵车3workStatusMap
		Map PT3WorkStatusMap = new HashMap();
		PT3WorkStatusMap.put(PTDeviceID.getSynComponent().getParameterID(), DeviceID3.getSynValue());
		PT3WorkStatusMap.put(PTSYMC.getSynComponent().getParameterID(), SYMC2.getSynValue());   //改动
		PT3WorkStatusMap.put(PTSYLD.getSynComponent().getParameterID(), SYLD3.getSynValue());
		PT3WorkStatusMap.put(PTRC.getSynComponent().getParameterID(), RC3.getSynValue());
		
		//设置templatePara资源对象
		ResourceInfo rTemplatePara = new ResourceInfo();
		rTemplatePara.setDataType("object");
		rTemplatePara.setClassObj(TemplatePara.class);
		RedisResource templateParaResource = new RedisResource(12);
		templateParaResource.resourceInfo = rTemplatePara;
		RegionalRC.resourceMap.put("templatePara",templateParaResource);
		
		//设置PTSynConfig的templatePara映射
		RegionalRC.getResource("templatePara").addResourceItem(PTDeviceIDSynComponent.getParameterID(),PTDeviceIDSynComponent);
		RegionalRC.getResource("templatePara").addResourceItem(PTSYMTSynComponent.getParameterID(),PTSYMTSynComponent);
		RegionalRC.getResource("templatePara").addResourceItem(PTSYMCSynComponent.getParameterID(),PTSYMCSynComponent);
		RegionalRC.getResource("templatePara").addResourceItem(PTSYLDSynComponent.getParameterID(),PTSYLDSynComponent);		
		RegionalRC.getResource("templatePara").addResourceItem(PTRCSynComponent.getParameterID(),PTRCSynComponent);
				
		
		//设置commonKey到uniqueKey的映射
		ResourceInfo rCommonKeytoUniqueKey = new ResourceInfo();
		rCommonKeytoUniqueKey.setDataType("string");
		RedisResource commonKeytoUniqueKeyResource = new RedisResource(10);
		commonKeytoUniqueKeyResource.resourceInfo = rCommonKeytoUniqueKey;
		RegionalRC.resourceMap.put("commonKey2DeviceID", commonKeytoUniqueKeyResource);
		
		//设置PT的commonKey到uniqueKey映射
		RegionalRC.getResource("commonKey2DeviceID").addResourceItem(SYMT1.getSynValue(), PT1.getDeviceID());
		RegionalRC.getResource("commonKey2DeviceID").addResourceItem(SYMT2.getSynValue(), PT2.getDeviceID());
		RegionalRC.getResource("commonKey2DeviceID").addResourceItem(SYMT3.getSynValue(), PT3.getDeviceID());
		
		PT1DP = initParsedDataPacket(new ParsedDataPacket(), PT1, SYMT1.getSynItem(), PT1BaseInfoMap, PT1WorkStatusMap);
		PT2DP = initParsedDataPacket(new ParsedDataPacket(), PT2, SYMT2.getSynItem(), PT2BaseInfoMap, PT2WorkStatusMap);//有变动
		PT3DP = initParsedDataPacket(new ParsedDataPacket(), PT3, SYMT3.getSynItem(), PT3BaseInfoMap, PT3WorkStatusMap);
		
	}

	@After
	public void tearDown() throws Exception {
		
		ddao.delete(PTSynBom1.getClass(),PTSynBom1.getOid());
		ddao.delete(PTSynBom2.getClass(),PTSynBom2.getOid());
		ddao.delete(PTSynBom3.getClass(),PTSynBom3.getOid());		
		
		ddao.delete(PT1.getClass(), PT1.getOid());
		ddao.delete(PT2.getClass(), PT2.getOid());
		ddao.delete(PT3.getClass(), PT3.getOid());
		
		tdao.delete(PTDeviceID.getClass(), PTDeviceID.getOid());
		tdao.delete(PTSYMT.getClass(), PTSYMT.getOid());
		tdao.delete(PTSYMC.getClass(), PTSYMC.getOid());
		tdao.delete(PTSYLD.getClass(), PTSYLD.getOid());
		tdao.delete(PTRC.getClass(), PTRC.getOid());
		
		tdao.delete(PTDeviceIDSynComponent.getClass(),PTDeviceIDSynComponent.getOid());
		tdao.delete(PTSYMTSynComponent.getClass(),PTSYMTSynComponent.getOid());
		tdao.delete(PTSYMCSynComponent.getClass(),PTSYMCSynComponent.getOid());
		tdao.delete(PTSYLDSynComponent.getClass(),PTSYLDSynComponent.getOid());
		tdao.delete(PTRCSynComponent.getClass(),PTRCSynComponent.getOid());
		
		tdao.delete(PTSeries.getClass(), PTSeries.getOid());
		
		IRedisDAO redisDAO = (IRedisDAO)AppContext.getSpringContext().getBean("redisDAO");
		redisDAO.clearTable(10);
		redisDAO.clearTable(11);
		redisDAO.clearTable(12);
		redisDAO.clearTable(13);
	}

	//无变动的情况:需改动PT1的SYMC，SYMT与原PT1的SynBom一致
//	@Test
//	public void testNormal() {     
//		BOMSyncManager BOMSM= new BOMSyncManager();
//		BOMSM.BOMSync(PT1DP);
//		assertEquals(PT1DP.getDevice().getOid(),PT1.getOid());
//		assertEquals(PT1DP.getCommonKey(),SYMT1.getSynItem());
//	}


	//测试能够维护同步件变更记录 注：此测试前需要修改recordBomChange的方法类型为static BomChangeRec
//	@Test
//	public void testRecordBomChange() {
//		BomChangeRec BCR= new BomChangeRec();
//		BCR.setBeforeValue(SYMC1.getSynValue());
//		BCR.setAfterValue(SYMC2.getSynValue());
//		BCR.setDevice(PT1.getOid());
//		BCR.setChangedComponent(PTSYMC.getSynComponent());
//		BOMChangeRecDAO bDao = (BOMChangeRecDAO)AppContext.getSpringContext().getBean("bOMChangeRecDAO");
//		bDao.save(BCR);
//		List<SynConfig> lst = PT1.getDeviceSeries().getSynConfigList();
//		SynConfig mc = null;
//		for(SynConfig sc : lst){
//			if(sc.getSyncComponentType()==3){
//				mc = sc;
//				break;
//			}
//		}
//		BOMSyncManager BOMSM;
//		BOMSM =new BOMSyncManager();
//		assertEquals(BOMSM.recordBomChange(PT1.getOid(), mc.getSynComponent().getParameterID(), SYMC1.getSynValue(), SYMC2.getSynValue()).getAfterValue(), BCR.getAfterValue());
//		assertEquals(BOMSM.recordBomChange(PT1.getOid(), mc.getSynComponent().getParameterID(), SYMC1.getSynValue(), SYMC2.getSynValue()).getBeforeValue(), BCR.getBeforeValue());
//		assertEquals(BOMSM.recordBomChange(PT1.getOid(), mc.getSynComponent().getParameterID(), SYMC1.getSynValue(), SYMC2.getSynValue()).getChangedComponent().getOid(), BCR.getChangedComponent().getOid());
//		assertEquals(BOMSM.recordBomChange(PT1.getOid(), mc.getSynComponent().getParameterID(), SYMC1.getSynValue(), SYMC2.getSynValue()).getDevice(), BCR.getDevice());
//	}
	
	//更换普通同步件：PT2使用SYMC1
	@Test
	public void testChangeSynBomItem() {      
		BOMSyncManager BOMSM= new BOMSyncManager();
		BOMSM.BOMSync(PT2DP);
		Map syncBOM = new HashMap();
		syncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", PT2DP.getDevice().getDeviceID());
		assertEquals(PT2DP.getDevice().getOid(),PT2.getOid());
		assertEquals(PT2DP.getWorkStatusMap().get(PTSYMC.getSynComponent().getParameterID()),syncBOM.get(PTSYMC.getSynComponent().getParameterID()));
		assertEquals(PT2DP.getBaseInfoMap().get(PTSYMT.getSynComponent().getParameterID()),syncBOM.get(PTSYMT.getSynComponent().getParameterID()));
	}
	
	//更换SYMT和普通同步件：PT3使用SYMT1，SYMC2
	@Test
	public void testChangeCommonKey() {   
		BOMSyncManager BOMSM= new BOMSyncManager();
		BOMSM.BOMSync(PT3DP);
		Map syncBOM = new HashMap();
		syncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", PT3DP.getDevice().getDeviceID());
		assertEquals(PT3DP.getDevice().getOid(),PT3.getOid());
		assertEquals(PT3DP.getWorkStatusMap().get(PTSYMC.getSynComponent().getParameterID()),syncBOM.get(PTSYMC.getSynComponent().getParameterID()));
		assertEquals(PT3DP.getBaseInfoMap().get(PTSYMT.getSynComponent().getParameterID()),syncBOM.get(PTSYMT.getSynComponent().getParameterID()));
	}

	//更换资源容器中未记录的SYMT和普通同步件：PT1使用SYMT4，SYLD4
	@Test
	public void testCommonKeynotExists() {     
		BOMSyncManager BOMSM= new BOMSyncManager();
		BOMSM.BOMSync(PT1DP);
		Map syncBOM = new HashMap();
		syncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", PT1DP.getDevice().getDeviceID());
		assertEquals(PT1DP.getDevice().getOid(),PT1.getOid());
		assertEquals(PT1DP.getWorkStatusMap().get(PTSYLD.getSynComponent().getParameterID()),syncBOM.get(PTSYLD.getSynComponent().getParameterID()));
		assertEquals(PT1DP.getBaseInfoMap().get(PTSYMT.getSynComponent().getParameterID()),syncBOM.get(PTSYMT.getSynComponent().getParameterID()));
		
	}
	
//	@Test
//	public void testKeepConsistency() {
//		List<SynConfig> lst = PT1.getDeviceSeries().getSynConfigList();
//		SynConfig mc = null;
//		for(SynConfig sc : lst){
//			if(sc.getSyncComponentType()==3){
//				mc = sc;
//				break;
//			}
//		}
//		//参数分别是唯一标识（车号）、同步件号（同步件配置对应的模板参数ID）、同步件值
//		assertFalse(new BOMSyncManager().itemSync(PT1.getOid(),mc.getSynComponent().getParameterID(), SYMC2.getSynValue()));
//		assertEquals(((Map)RegionalRC.getResourceItem("syncBOM", PT1.getOid())).get(mc.getSynComponent().getParameterID()),SYMC2.getSynValue());
//	}
	

	
//	@Test
//	public void testCommonKeyExists() {
//		BOMSyncManager BOMSM= new BOMSyncManager();
//		BOMSM.BOMSync(PT4DP);
//		Map syncBOM = new HashMap();
//		syncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", PT4DP.getDevice().getDeviceID());
//		assertEquals(PT4DP.getWorkStatusMap().get(PTSYLD.getSynComponent().getParameterID()),syncBOM.get(PTSYLD.getSynComponent().getParameterID()));
//		assertEquals(PT4DP.getBaseInfoMap().get(PTSYMT.getSynComponent().getParameterID()),syncBOM.get(PTSYMT.getSynComponent().getParameterID()));
//		
//	}


	public final SynBomItem initSynBomItem(SynBomItem synBomItem,String synValue,Integer valueType, SynBom synBom, TemplatePara synItem){
		synBomItem.setSynBom(synBom);
		synBomItem.setSynValue(synValue);
		synBomItem.setValueType(valueType);
		synBomItem.setSynItem(synItem);
		return synBomItem;
	}
	public final SynConfig initSynConfig(SynConfig synConfig, Integer syncComponentType, DeviceSeries deviceSeries, TemplatePara synComponent){
		synConfig.setSyncComponentType(syncComponentType);
		synConfig.setDeviceSeries(deviceSeries);
		synConfig.setSynComponent(synComponent);
		return synConfig;
	}
	public final ParsedDataPacket initParsedDataPacket(ParsedDataPacket parsedDataPacket, Device device, TemplatePara commonKey,Map<String, String> baseInfoMap, Map<String, String> workStatusMap){
		parsedDataPacket.setDevice(device);
		parsedDataPacket.setCommonKey(commonKey);
		parsedDataPacket.setBaseInfoMap(baseInfoMap);
		parsedDataPacket.setWorkStatusMap(workStatusMap);
		return parsedDataPacket;
	}
	private void saveObjArray(Object[] objArray){
		for(Object obj:objArray){
			ddao.save(obj);
		}
	}
}
