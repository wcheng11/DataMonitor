package edu.thss.monitor.rsp.service.consistency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.pub.dao.impl.BOMChangeRecDAO;
import edu.thss.monitor.pub.dao.impl.DeviceDAO;
import edu.thss.monitor.pub.dao.impl.SynBomDAO;
import edu.thss.monitor.pub.dao.impl.TemplateParaDAO;
import edu.thss.monitor.pub.entity.BomChangeRec;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.DeviceSeries;
import edu.thss.monitor.pub.entity.SynBom;
import edu.thss.monitor.pub.entity.SynBomItem;
import edu.thss.monitor.pub.entity.SynConfig;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.parse.util.SyncUtil;

/**
 * 维护同步件一致性 
 * @author Livio
 * 
 */
public class BOMSyncManager implements IBOMSyncService{
	
	protected ApplicationContext context = AppContext.getSpringContext();
	Map<String, String> syncBOM;
	String commonKey,uniqueKey,commonKeyID,uniqueKeyID;
	Device device;
	
	/**
	 * 主方法1- BOM同步
	 * @param 解析后数据报文
	 */
	@Override
	public void BOMSync(ParsedDataPacket dp) {
		commonKeyID=dp.getCommonKey().getParameterID();
		commonKey=dp.getBaseInfoMap().get(commonKeyID);
				
		//如果这个公有标识不存在（新设备或新换了SYMT）
		if (!commonKeyExists(commonKey)){
			//设备怎么知道啊？
			uniqueKeyID=getTemplateParaOfUniqueKey(device);
			uniqueKey=dp.getWorkStatusMap().get(uniqueKeyID);
			
			//创建Common-Unique对应
			if (null!=uniqueKey)
				createCommonUniqueItem(commonKey,uniqueKey);
			else 
				createCommonUniqueItem(commonKey,"");

							
			//创建同步BOM
			createSyncBOM(commonKey,uniqueKey,dp.getDevice().getDeviceSeries());
					
		}
		
		//如果公有标识存在
		else {
			uniqueKey=getUniqueKeyByCommonKey(commonKey);
			device=SyncUtil.getDeviceByCommonKey(commonKey);
			uniqueKeyID=getTemplateParaOfUniqueKey(device);
			String newUniqueKey=dp.getWorkStatusMap().get(uniqueKeyID);
			
			//数据包中包含唯一标识
			if (null!=newUniqueKey){
				//两个唯一标识不一致
				if (!uniqueKey.equals(newUniqueKey)){
					rematchKeys(commonKey, newUniqueKey, device);
					uniqueKey=newUniqueKey;
				}
			}			
		}
		
		//依次匹配其他同步件
		normalItemsSync(dp);
		
		//再次获取设备
		device=SyncUtil.getDeviceByCommonKey(commonKey);
		//封入数据包
		dp.setDevice(device);		

	}

	/**
	 * 主方法2- IP同步
	 * @param 解析后数据报文
	 */
	@Override
	public void IPSync(ParsedDataPacket dp) {
		
		ChangeInfo changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_ADD,"device2ip",dp.getDevice().getDeviceID(),dp.getIp());
		try {
			RegionalRC.acceptChange(changeInfo);
		} catch (RSPException e) {			
			e.printStackTrace();
		}		
	}
	
	/**
	 * 匹配普通同步件
	 * @param 解析后数据报文
	 */
	public void normalItemsSync(ParsedDataPacket dp) {
		
		List<SynConfig> syncItems=getSyncItems(commonKey);
		
		for (String key:dp.getWorkStatusMap().keySet()){
			for (SynConfig item:syncItems)
			//判定是否普通同步件（公有标识与唯一标识就不同步了）	
			if (key.equals(item.getSynComponent().getParameterID()) && item.getSyncComponentType()==3)
				itemSync(uniqueKey,key,dp.getWorkStatusMap().get(key));
		}
	}
	/**
	 * 获取某设备的同步件列表
	 * @param 设备
	 * @return 同步件列表
	 */
	public List<SynConfig> getSyncItems(String commonKey){
		
		Device device= SyncUtil.getDeviceByCommonKey(commonKey);
		return device.getDeviceSeries().getSynConfigList();
		//SynBom syncBOM =(SynBom) RegionalRC.getResourceItem("syncBOM",device.getDeviceSeries().getOid());
		//return syncBOM.getSyncBomItemList();		
	}
	
	/**
	 * 获取某设备的公有标识对应的模板参数
	 * @param 设备
	 * @return 模板参数
	 */
	public String getTemplateParaOfCommonKey(Device device){	
		for (SynConfig config:device.getDeviceSeries().getSynConfigList()){
			if (config.getSyncComponentType()==2)
				return config.getSynComponent().getParameterID();
		}
		return null;
	}
	
	/**
	 * 获取某设备的唯一标识对应的模板参数
	 * @param 设备
	 * @return 模板参数
	 */
	public String getTemplateParaOfUniqueKey(Device device){	
		for (SynConfig config:device.getDeviceSeries().getSynConfigList()){
			if (config.getSyncComponentType()==1)
				return config.getSynComponent().getParameterID();
		}
		return null;
	}
	

	/**
	 * 通过公有标识来获得唯一标识
	 * @param 公有标识
	 * @return 唯一标识
	 */
	public String getUniqueKeyByCommonKey(String commonKey){
		if (null==uniqueKey)
			uniqueKey = (String) RegionalRC.getResourceItem("commonKey2DeviceID", commonKey);
		return uniqueKey;
	}

	/**
	 * 更新Oracle数据库中的同步BOM表
	 */
	public void updateSyncBOMInDB(String uniqueKey, String syncItemID, String syncValue, int syncType){
		
		//写入同步BOM表
		SynBomDAO sbDao = (SynBomDAO) context.getBean("synBomDAO");
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("device", uniqueKey);
		SynBom syncBOM=(SynBom) singleFilter(sbDao.findByAttr(SynBom.class, queryMap));
		
		//如果有这个同步BOM，说明是台老设备
		if (null!=syncBOM){
			List<SynBomItem> itemList = syncBOM.getSyncBomItemList();
			//用来标识是否已经更新（有可能这个同步BOMItem还不存在，就更新不了）
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
				item.setSynBom(syncBOM);
				item.setValueType(syncType);
			}
			sbDao.save(syncBOM);
		} 
		//如果还没有这个同步BOM，说明是台新设备
		else {
			//生成一个同步BOM
			syncBOM=new SynBom();
			syncBOM.setDevice(uniqueKey);
			sbDao.save(syncBOM);
			
			SynBomItem item= new SynBomItem();
			TemplateParaDAO tpDao = (TemplateParaDAO) context.getBean("templateParaDAO");
			queryMap=new HashMap<String, String>();
			queryMap.put("parameterID", syncItemID);
			TemplatePara templatePara=(TemplatePara) singleFilter(tpDao.findByAttr(TemplatePara.class, queryMap));
			item.setSynItem(templatePara);
			item.setSynValue(syncValue);
			item.setSynBom(syncBOM);
			item.setValueType(syncType);
			////
			syncBOM.getSyncBomItemList().add(item);
			sbDao.save(syncBOM);
			
		}
		
	}
	
	/**
	 * 记录同步件变更
	 * @param 公有标识、同步件号、旧值、新值
	 * @return void
	 */	
	public void recordBomChange(String uniqueKey, String syncItemID, String oldValue, String newValue){
		
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
	 * 通过uniqueKey来获得Device对象	
	 */
	public Device getDevice(String uniqueKey){
		DeviceDAO dDao = (DeviceDAO) context.getBean("deviceDAO");
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("deviceID", uniqueKey);
		return (Device) singleFilter(dDao.findByAttr(Device.class, queryMap));
		
	}
	
	
	/**
	 * 维护CommonKey-UniqueKey表的对应关系
	 * @param 公有标识、同步件号
	 * @return 是否创建成功（成功则为true）
	 */	
	public boolean rematchKeys(String commonKey, String uniqueKey, Device device){
		
		try {
			
			//获取资源			
			String oldUniqueKey=(String) RegionalRC.getResourceItem("commonKey2DeviceID", commonKey);
			Map<String, String> oldSyncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", oldUniqueKey);
						String commonKeyID=getTemplateParaOfCommonKey(device);
			String oldCommonKey = (String) oldSyncBOM.get(commonKeyID);			
			Map<String, String> syncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", uniqueKey);
			
			//删掉旧的CommonKey-UniqueKey对应		
			ChangeInfo changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_DELETE,"commonKey2DeviceID",oldCommonKey,null);
			RegionalRC.acceptChange(changeInfo);
			
			//维护新的CommonKey-UniqueKey对应
			changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"commonKey2DeviceID",commonKey,uniqueKey);
			RegionalRC.acceptChange(changeInfo);
			
			//维护旧的BOM
			oldSyncBOM.remove(commonKeyID);
			changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"syncBOM",oldUniqueKey,oldSyncBOM);
			RegionalRC.acceptChange(changeInfo);
			
			//维护新的BOM			
			syncBOM.put(commonKeyID, commonKey);
			changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"syncBOM",uniqueKey,syncBOM);
			RegionalRC.acceptChange(changeInfo);
			
			//记录BOM变更
			recordBomChange(uniqueKey,commonKeyID,oldSyncBOM.get(commonKeyID),commonKey);
			
			//写Oracle数据库
			updateSyncBOMInDB(uniqueKey,commonKeyID,commonKey,2);
			
			return true;
			
		} catch(RSPException e){
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * 判断同步BOM是否一致，并维持同步BOM一致性
	 * @param 唯一标识、同步件号、同步件值
	 * @return 是否一致（一致则为true）
	 */	
	public boolean itemSync(String uniqueKey, String syncItemID, String syncItemValue){
		if (null==syncBOM)
			syncBOM =(HashMap) RegionalRC.getResourceItem("syncBOM", uniqueKey);
		String value=(String)syncBOM.get(syncItemID);
		
		//如果是匹配的
		if (value.equals(syncItemValue))		
			return true;
		else {
			syncBOM.put(syncItemID, syncItemValue);
			ChangeInfo changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"syncBOM",uniqueKey,syncBOM);
			try {
				RegionalRC.acceptChange(changeInfo);
			} catch (RSPException e) {
				
				e.printStackTrace();
				return false;
			}
			//记录BOM变更
			recordBomChange(uniqueKey,syncItemID,value,syncItemValue);
			
			//记录Oracle数据库BOM表
			updateSyncBOMInDB(uniqueKey,syncItemID,syncItemValue,3);
			return false;
		}		
	}
	
	/**
	 * 判断是否存在这个commonKey
	 * @param 公有标识
	 * @return 是否存在（存在则返回true）
	 */		
	public boolean commonKeyExists(String commonKey){
		 
		if (RegionalRC.getResourceItem("commonKey2DeviceID", commonKey)!=null)
			return true;
		else return false;				
		
	}
	
	/**
	 * 创建CommonKey-Unique键值对
	 * @param 公有标识，唯一标识
	 * @return 是否创建成功（成功则返回true）
	 */	
	public boolean createCommonUniqueItem(String commonKey, String uniqueKey){
		ChangeInfo changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_ADD,"commonKey2DeviceID",commonKey,uniqueKey);
		//?上面把commonKey当作主键
		
		try {
			RegionalRC.acceptChange(changeInfo);
		} catch (RSPException e) {
			e.printStackTrace();
			return false;
		}
		return true;		
	}
	
	/**
	 * 创建新的同步BOM
	 * @param 公有标识，唯一标识，设备系列
	 * @return 是否创建成功（成功则返回true）
	 */	
	public boolean createSyncBOM(String commonKey, String uniqueKey, DeviceSeries deviceSeries ){
		Map<String,String> synBOM= new HashMap<String,String>();
		for (SynConfig config : deviceSeries.getSynConfigList()){
			//Type=1表示是uniqueKey，2表示commonKey，3表示普通同步件
			if (config.getSyncComponentType().intValue()!=1){
				synBOM.put(config.getSynComponent().getParameterID(), "");	
				
				if (config.getSyncComponentType().intValue()==2)
					synBOM.put(config.getSynComponent().getParameterID(), commonKey);
			}
		}
		
		ChangeInfo changeInfo=new ChangeInfo(ChangeInfo.CHANGETYPE_ADD,"syncBOM",uniqueKey,synBOM);
		try {
			RegionalRC.acceptChange(changeInfo);
		} catch (RSPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}	
	
	/**
	 * DAO总是返回列表很烦人，这里过滤一下
	 */
	private Object singleFilter(List itemList){
		if (null!=itemList)
			return itemList.get(0);		
		else 
			return null;
	}

}
