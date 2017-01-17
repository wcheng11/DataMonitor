package edu.thss.monitor.rsp.service.consistency;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.pub.dao.impl.BOMChangeRecDAO;
import edu.thss.monitor.pub.dao.impl.SynBomDAO;
import edu.thss.monitor.pub.entity.BomChangeRec;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.SynBom;
import edu.thss.monitor.pub.entity.SynBomItem;
import edu.thss.monitor.pub.entity.SynConfig;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.util.SyncUtil;

public class BOMSyncManager3 implements IBOMSyncService{
	
	/**
	 * 公有标识
	 */
	private TemplatePara commonKey;

	/**
	 * 唯一标识
	 */
	private TemplatePara uniqueKey;

	@Override
	public void BOMSync(ParsedDataPacket dp) {
		
		
		this.commonKey = dp.getCommonKey();
		this.uniqueKey = dp.getUniqueKey();
		
		if(commonKey == null) return;
		

		// 公有标识参数值(这里是SYMT号)
		String commonValue = dp.getBaseInfoMap().get(this.commonKey.getParameterID());
		
		// 数据库中公有标识对应的唯一标识参数值
		String oldUniqueValue = this.getUniqueKeyByCommonKey(commonValue);
		
		// 唯一标识参数值
		String uniqueValue = oldUniqueValue;
		
		
		
		if(this.uniqueKey != null){
			
			//数据包中的唯一标识参数值
			uniqueValue = dp.getWorkStatusMap().get(this.uniqueKey.getParameterID());	
			
			// 数据包中的唯一标识和数据库中查询到的唯一标识不一致
			if (!uniqueValue.equals(oldUniqueValue)) {

				// 重新匹配公有标识和唯一标识
				this.rematchKeys(commonKey, commonValue, uniqueKey, oldUniqueValue, uniqueValue);
			}
		}
		
		
		// 依次匹配其他同步件
		this.normalItemsSync(dp, commonValue, uniqueValue);
	}

	@Override
	public void IPSync(ParsedDataPacket dp) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 通过公有标识来获得唯一标识
	 * 
	 * @param 公有标识
	 * @return 唯一标识
	 */
	private String getUniqueKeyByCommonKey(String commonKey) {
		String uniqueKey = (String) RegionalRC.getResourceItem(
				"commonKey2DeviceID", commonKey);
		return uniqueKey;
	}
	
	
	/**
	 * 维护CommonKey-UniqueKey表的对应关系
	 * 
	 * @param 公有标识、唯一标识
	 * @return 是否创建成功（成功则为true）
	 */
	private boolean rematchKeys(TemplatePara commonKey,
			String commonValue, TemplatePara uniqueKey, String oldUniqueValue, String uniqueValue) {

		try {
			// 查询与要修改的唯一标识相冲突的项
			String conflictCommonKey = this.getCommonKeyOfUniqueKey(uniqueValue);

			// 若冲突项存在，则删除该冲突项(CommonKey-UniqueKey)
			if (conflictCommonKey != null) {
				ChangeInfo changeInfo = new ChangeInfo(
						ChangeInfo.CHANGETYPE_DELETE, "commonKey2DeviceID",
						conflictCommonKey, null);
				RegionalRC.acceptChange(changeInfo);
				
				Map<String, String> syncBOM = (HashMap) RegionalRC.getResourceItem("syncBOM", uniqueValue);
				Set<String> key = syncBOM.keySet();
				for (Iterator it = key.iterator(); it.hasNext();) {
			         String s = (String) it.next(); 
			         SyncUtil.recordBomChange(uniqueValue, s, syncBOM.get(s), "");
			    }
				
				SyncUtil.deleteSynBOMInDB(uniqueValue);
			}

			// 修改commonKey对应的uniqueKey为新值
			ChangeInfo changeInfo = new ChangeInfo(
					ChangeInfo.CHANGETYPE_UPDATE, "commonKey2DeviceID",
					commonValue, uniqueValue);
			RegionalRC.acceptChange(changeInfo);
			
			// 记录BOM变更
			SyncUtil.recordBomChange(uniqueValue, uniqueKey.getParameterID(), oldUniqueValue, uniqueValue);

			// 写入Oracle数据库
			SyncUtil.addSynBOMInDB(commonKey, commonValue, uniqueKey, uniqueValue);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	
	/**
	 * 根据唯一标识从Redis数据库(commonKey2DeviceID)中查出对应的公有标识
	 * 
	 * @param uniqueKey
	 *            唯一标识
	 * @return 若存在该唯一标识则返回对应的公有标识，否则返回null
	 */
	private String getCommonKeyOfUniqueKey(String uniqueKey) {
		
		SynBom syncBOM = SyncUtil.getSynBOMByUniqueValue(uniqueKey);
		
		if(syncBOM == null) return null;
		
		for(SynBomItem item : syncBOM.getSyncBomItemList()){
			
			//等于2是公有标识
			if(item.getValueType() == 2){
				return item.getSynValue();
			}
		}
		
		return null;
	}
	
	
	/**
	 * 匹配普通同步件
	 * 
	 * @param 解析后数据报文
	 */
	public void normalItemsSync(ParsedDataPacket dp, String commonKey,
			String uniqueKey) {

		//List<SynConfig> syncItems = getSyncItems(commonKey, uniqueKey);
		List<SynConfig> syncItems = SyncUtil.getDeviceByCommonKey(commonKey).getDeviceSeries().getSynConfigList();

		for (String key : dp.getWorkStatusMap().keySet()) {
			for (SynConfig item : syncItems)
				// 判定是否普通同步件（公有标识与唯一标识就不同步了）
				if (item.getSyncComponentType() == 3
						&& key.equals(item.getSynComponent().getParameterID()))
					itemSync(uniqueKey, key, dp.getWorkStatusMap().get(key));
		}
	}
	
	
	
	/**
	 * 判断同步BOM是否一致，并维持同步BOM一致性
	 * 
	 * @param 唯一标识
	 *            、同步件号、同步件值
	 * @return 是否一致（一致则为true）
	 */
	public void itemSync(String uniqueKey, String syncItemID,
			String syncItemValue) {

		// 获取同步BOM资源
		Map<String, String> syncBOM = (HashMap) RegionalRC.getResourceItem("syncBOM", uniqueKey);
		
		try {

			String value = (String) syncBOM.get(syncItemID);
			
			if(value == null || !value.equals(syncItemValue)){
				syncBOM.put(syncItemID, syncItemValue);
				ChangeInfo changeInfo = new ChangeInfo(
						ChangeInfo.CHANGETYPE_UPDATE, "syncBOM", uniqueKey, syncBOM);
				
				RegionalRC.acceptChange(changeInfo);
				
	
				// 记录BOM变更
				SyncUtil.recordBomChange(uniqueKey, syncItemID, value, syncItemValue);
	
				// 记录Oracle数据库BOM表
				SyncUtil.updateSyncBOMInDB(uniqueKey, syncItemID, syncItemValue, 3);
			}
		} catch (RSPException e) {
			e.printStackTrace();
		}
	}
	

	
}
