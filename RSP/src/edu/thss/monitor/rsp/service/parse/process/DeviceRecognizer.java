package edu.thss.monitor.rsp.service.parse.process;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.util.TypeUtil;
import edu.thss.monitor.rsp.service.parse.util.SyncUtil;

public class DeviceRecognizer {

	/**
	 * 识别的设备
	 */
	private Device device;

	/**
	 * 公有标识
	 */
	private TemplatePara commonKey;

	/**
	 * 唯一标识
	 */
	private TemplatePara uniqueKey;

	public Device recognize(ParsedDataPacket parsedData) throws RSPException {

		System.out.print("开始获取公有标识");
		
		this.commonKey = parsedData.getCommonKey();
		//this.uniqueKey = parsedData.getUniqueKey();
		
//		System.out.print("公有标识："+commonKey.getParameterName());
		
		if (this.commonKey != null) { // 有公有标识，通过公有标志查找设备

			String commonValue = parsedData.getBaseInfoMap().get( // 通过公有标识获取
					commonKey.getParameterID());
			
						
//			System.out.print("公有标识值为："+commonValue);
			
			if(commonValue.equals(null)){
				device = null;								
			}else
				this.device = SyncUtil.getDeviceByCommonKey(commonValue);	
			
			if (device == null) { // commonKey不存在或设备号不存在

				System.out.print("设备不存在--");		
				
//				if (uniqueKey != null) { // 如果报文中存在唯一标识的数据，则新建commonKey与uniqueKey的映射
//					String uniqueValue = parsedData.getWorkStatusMap().get(
//							uniqueKey.getParameterID());
//														
//					this.device = SyncUtil.getDeviceByUniqueKey(uniqueValue);
//					if (device == null) {
//						throw new RSPException(
//								LogConstant.LOG_FLAG_DEVICERECONG, uniqueValue,
//								"唯一标识" + uniqueValue + "无对应设备，创建关系映射失败。");
//					} else { //可成功创建映射
//						//同步资源
//						System.out.print("设备不存在--同步资源？？？？？-");	
//						
//						ChangeInfo changeInfo = new ChangeInfo(
//								ChangeInfo.CHANGETYPE_UPDATE,
//								"commonKey2DeviceID", commonValue, device.getDeviceID());
//						RegionalRC.syncChange(changeInfo);
//						
//						//同步持久化数据库
//						SyncUtil.addSynBOMInDB(commonKey, commonValue, uniqueKey, uniqueValue);
//						//同步oracle数据库，记录变更日志
//						SyncUtil.recordBomChange(uniqueValue, uniqueKey, null , uniqueValue);
//						SyncUtil.recordBomChange(uniqueValue, commonKey, null , commonValue);
//					}
//				} else {
//					System.out.print("无对应设备，无设备信息创建关联。");
//					throw new RSPException(LogConstant.LOG_FLAG_DEVICERECONG,
//							commonValue, "公有标识" + commonValue + "无对应设备，无设备信息创建关联。");
//				}
				
				

			}
//			LogRecord.debug(LogConstant.LOG_FLAG_DEVICERECONG + "获取的设备号为："
//					+ this.device.getDeviceID());
//			System.out.print(LogConstant.LOG_FLAG_DEVICERECONG + "获取的设备号为："
//					+ this.device.getDeviceID());
			
			
//			//=========调试代码，输出查看识别的设备号，非调试状态下，可删除
//			if(this.device.getDeviceID().equals("320110110131")){
//				LogRecord.debug(LogConstant.LOG_FLAG_DEVICERECONG+"deviceID:"
//						+device.getDeviceID()+"mt:"+commonValue+"  bc129:" 
//						+ parsedData.getWorkStatusMap().get("bc129")
//						+"  bc130:"+parsedData.getWorkStatusMap().get("bc130")
//						+ TypeUtil.getBytesString((byte[]) rawData.getPacketData()));
//			}
//			//===============================

		}else if(this.uniqueKey != null){ // 无公有标识，唯一标识（即设备号），直接获取设备
			String uniqueValue = getDeviceIDByUniqueKey(parsedData);
			this.device = SyncUtil.getDeviceByUniqueKey(uniqueValue);
			if (device == null) {
				throw new RSPException(LogConstant.LOG_FLAG_DEVICERECONG,
						uniqueValue, "唯一标识" + uniqueValue + "无对应设备");
			}

		}else {
			System.out.print("数据包无公有标识或唯一标识信息，无法进行设备识别。");
			throw new RSPException(LogConstant.LOG_FLAG_DEVICERECONG,
					null, "数据包无公有标识或唯一标识信息，无法进行设备识别。");
		}		
		return device;

	}

	private String getDeviceIDByUniqueKey(ParsedDataPacket parsedData) {

		String value = parsedData.getBaseInfoMap().get(
				uniqueKey.getParameterID());
		if (value != null) {
			return value;
		} else {
			return parsedData.getWorkStatusMap()
					.get(uniqueKey.getParameterID());
		}
	}
}
