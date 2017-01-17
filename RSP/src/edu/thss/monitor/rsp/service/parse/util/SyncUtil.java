package edu.thss.monitor.rsp.service.parse.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.dao.impl.BOMChangeRecDAO;
import edu.thss.monitor.pub.dao.impl.DeviceDAO;
import edu.thss.monitor.pub.dao.impl.SynBomDAO;
import edu.thss.monitor.pub.dao.impl.TemplateParaDAO;
import edu.thss.monitor.pub.entity.BomChangeRec;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.SynBom;
import edu.thss.monitor.pub.entity.SynBomItem;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.sys.AppContext;

public class SyncUtil {
	
	protected static ApplicationContext context = AppContext.getSpringContext();

	/**
	 * 根据公有标识的值获取设备对象
	 * @param commonValue 公有标识的值
	 * @return 设备对象
	 */
	public static Device getDeviceByCommonKey(String commonValue) {
		
		//LogRecord.debug(LogConstant.LOG_FLAG_DEVICERECONG + "获得SYMT号为：" + commonValue);
		//System.out.print(LogConstant.LOG_FLAG_DEVICERECONG + "获得SYMT号为：" + commonValue);
		
		String deviceID="";
		deviceID = (String) RegionalRC.getResourceItem("commonKey2DeviceID",
				commonValue);
		
		System.out.print("设备ID为："+deviceID);
		
		if(deviceID == null){    //commonKey不存在
			System.out.print("设备标识为空----");
			return null;
		}
//		LogRecord.debug(LogConstant.LOG_FLAG_DEVICERECONG + "获取的设备号为：" + deviceID);
		Device device = (Device) RegionalRC.getResourceItem("device", deviceID);
		return device;
	}
	
	/**
	 * 根据唯一标识的值获取设备对象
	 * @param commonValue 唯一标识的值
	 * @return 设备对象
	 */
	public static Device getDeviceByUniqueKey(String uniqueValue) {

		LogRecord.debug(LogConstant.LOG_FLAG_DEVICERECONG + "获取的设备号为：" + uniqueValue);
		Device device = (Device) RegionalRC.getResourceItem("device", uniqueValue);
		return device;
	}
	
	
	/**
	 * 更新Oracle数据库中的同步BOM表
	 */
	public static void updateSyncBOMInDB(String uniqueKey, String syncItemID, String syncValue, int syncType){
		
		//写入同步BOM表
		SynBomDAO sbDao = (SynBomDAO) context.getBean("synBomDAO");
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("device", uniqueKey);
		SynBom syncBOM=(SynBom) singleFilter(sbDao.findByAttr(SynBom.class, queryMap));
		
		List<SynBomItem> itemList = syncBOM.getSyncBomItemList();
		boolean updateFlag=false;
		for (SynBomItem item : itemList) {
			if (item.getSynItem().getParameterID() == syncItemID) {
				item.setSynValue(syncValue);
				updateFlag=true;
			}
		}
		
		//若还未更新，就新建这个同步项
		if (!updateFlag){
			
			SynBomItem item= new SynBomItem();
			TemplateParaDAO tpDao = (TemplateParaDAO) context.getBean("templateParaDAO");
			queryMap=new HashMap<String, String>();
			queryMap.put("parameterID", syncItemID);
			TemplatePara templatePara=(TemplatePara) singleFilter(tpDao.findByAttr(TemplatePara.class, queryMap));
			item.setSynItem(templatePara);
			item.setSynValue(syncValue);
			
			syncBOM.getSyncBomItemList().add(item);
		}
		sbDao.save(syncBOM);
	}

	
	
	/**
	 * 返回List中的第一个元素
	 */
	@SuppressWarnings("unchecked")
	private static Object singleFilter(List itemList){
		if (null!=itemList)
			return itemList.get(0);		
		else 
			return null;
	}

	/**
	 * 创建一个新的同步BOM
	 */
	public static void addSynBOMInDB(TemplatePara commonKey,
			String commonValue, TemplatePara uniqueKey, String uniqueValue) {
		
		
		SynBom bom = new SynBom();
		SynBomItem uniqueItem = new SynBomItem();
		uniqueItem.setSynItem(uniqueKey);
		uniqueItem.setSynValue(uniqueValue);
		uniqueItem.setValueType(1);
		
		SynBomItem commonItem = new SynBomItem();
		commonItem.setSynItem(commonKey);
		commonItem.setSynValue(commonValue);
		commonItem.setValueType(2);
		
		List<SynBomItem> list = new ArrayList<SynBomItem>();
		list.add(uniqueItem);
		list.add(commonItem);
		
		bom.setDevice(uniqueValue);
		bom.setSyncBomItemList(list);
		SynBomDAO sbDao = (SynBomDAO) context.getBean("synBomDAO");
		
		sbDao.save(bom);
		
		
	}
	
	
	/**
	 * 删除一个同步BOM
	 * @param uniqueValue
	 */
	public static void deleteSynBOMInDB(String uniqueValue){
		SynBomDAO sbDao = (SynBomDAO) context.getBean("synBomDAO");
		SynBom syncBOM = getSynBOMByUniqueValue(uniqueValue);
		sbDao.delete(SynBom.class, syncBOM);
	}
	
	
	
	/**
	 * 通过uniqueValue获取同步BOM
	 * @param uniqueValue 唯一标识
	 * @return	同步BOM
	 */
	public static SynBom getSynBOMByUniqueValue(String uniqueValue){
		SynBomDAO sbDao = (SynBomDAO) context.getBean("synBomDAO");
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("device", uniqueValue);
		SynBom syncBOM=(SynBom) singleFilter(sbDao.findByAttr(SynBom.class, queryMap));
		return syncBOM;
	}


	
	
	/**
	 * 记录同步件变更
	 * @param 公有标识、同步件号、旧值、新值
	 * @return void
	 */	
	public static void recordBomChange(String uniqueKey, String syncItemID, String oldValue, String newValue){
		
		//写入变更表
		BomChangeRec bomChangeRec=new BomChangeRec();
		bomChangeRec.setDevice(getDevice(uniqueKey).getOid());		
		bomChangeRec.setChangedComponent((TemplatePara) RegionalRC.getResourceItem("templatePara", syncItemID));
		bomChangeRec.setBeforeValue(oldValue);
		bomChangeRec.setAfterValue(newValue);
		BOMChangeRecDAO bDao = (BOMChangeRecDAO) context.getBean("bOMChangeRecDAO");
		bDao.save(bomChangeRec);		
	}
	
	
	/**
	 * 记录同步件变更
	 * @param 公有标识、同步件、旧值、新值
	 * @return void
	 */	
	public static void recordBomChange(String uniqueValue, TemplatePara syncItem, String oldValue, String newValue){
		
		//写入变更表
		BomChangeRec bomChangeRec=new BomChangeRec();
		bomChangeRec.setDevice(uniqueValue);		
		bomChangeRec.setChangedComponent(syncItem);
		bomChangeRec.setBeforeValue(oldValue);
		bomChangeRec.setAfterValue(newValue);
		BOMChangeRecDAO bDao = (BOMChangeRecDAO) context.getBean("bOMChangeRecDAO");
		bDao.save(bomChangeRec);		
	}
	
	
	/**
	 * 通过uniqueKey来获得Device对象	
	 */
	public static Device getDevice(String uniqueKey){
		DeviceDAO dDao = (DeviceDAO) context.getBean("deviceDAO");
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("deviceID", uniqueKey);
		return (Device) singleFilter(dDao.findByAttr(Device.class, queryMap));
		
	}


}
