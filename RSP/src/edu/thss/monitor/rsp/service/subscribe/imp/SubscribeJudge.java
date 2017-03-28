package edu.thss.monitor.rsp.service.subscribe.imp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.imp.RedisResource;
import edu.thss.monitor.pub.dao.impl.SubPlanDAO;
import edu.thss.monitor.pub.entity.SubPlan;
import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.subscribe.ISubscribeJudge;

public class SubscribeJudge implements ISubscribeJudge {	
	
	@SuppressWarnings("unchecked")
	public List<JudgeResult> getSubscribeJudge(ParsedDataPacket pdu)throws ClassCastException,RSPException, ParseException {
		List<JudgeResult> results = new ArrayList<JudgeResult>();
		//获得设备ID
		String deviceID = pdu.getDevice().getDeviceID();
		//根据设备ID获得订阅方案集合
//		System.out.println(deviceID);
		
		Map<String,String> subPlanMap = new HashMap<String, String>();
		
		System.out.print("获取设备2订阅计划-");
		
		Object subPlans = RegionalRC.getResource("device2SubPlan").getResourceItem(deviceID);
		if(subPlans == null)
			return results;
		Set<String> subPlanSet = (Set<String>)subPlans;
		Iterator setIterator = subPlanSet.iterator();
		while(setIterator.hasNext()){
			String device2SubPlanOid = setIterator.next().toString();
			String subPlanTimePeriod = RegionalRC.getResource("subPlanTime").getResourceItem(device2SubPlanOid).toString();
			subPlanMap.put(device2SubPlanOid, subPlanTimePeriod);
		}
		
	
		
		Iterator it = subPlanMap.entrySet().iterator();
		Set<String> newSubPlanLst = new HashSet<String>();
		List<String> oldSubPlanLst = new ArrayList<String>();
		//遍历订阅方案集合进行订阅判断
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
			String subPlanOID = entry.getKey();
			String [] tmpArray = entry.getValue().split("#");
			long subPlanBeginTime = Long.parseLong(tmpArray[0]);
			long subPlanEndTime = Long.parseLong(tmpArray[1]);
			//订阅方案的未到期或过期判断
			if(subPlanBeginTime>new Date().getTime()){ //还未到订阅开始时间
				continue;
			}else if(subPlanEndTime<new Date().getTime()){ //订阅结束时间已过期
				oldSubPlanLst.add(subPlanOID);
				continue;
			}
			newSubPlanLst.add(subPlanOID);
			//生成订阅结果
			JudgeResult result = new JudgeResult();
			result.setId(subPlanOID);	
			
			List<WorkStatusData> list = new ArrayList<WorkStatusData>();
			
			Set<String> paramSet = (Set<String>)RegionalRC.getResource("subPlan2ParamID").getResourceItem(subPlanOID);
			Iterator paramIterator = paramSet.iterator();
			
			Date timestamp = new Date(pdu.getTimestamp());
			String serialNo = pdu.getBaseInfoMap().get("serialNo");
			while(paramIterator.hasNext()){ //遍历该订阅方案的参数ID
				String param = paramIterator.next().toString();
				if(pdu.getWorkStatusMap().containsKey(param)){
//					System.out.println(param);
					WorkStatusData newWsd = new WorkStatusData();
					newWsd.setDevice(pdu.getDevice());
					newWsd.setWorkStatus(param);
					newWsd.setDataValue(pdu.getWorkStatusMap().get(param));
					newWsd.setTimestamp(timestamp);
					newWsd.setSerialNo(serialNo);								
					list.add(newWsd);	
//					System.out.println(newWsd.getDataValue());
				}							
			}
			if(list.size()>0){
				result.setWorkStatusList(list);
				results.add(result);
			}
		}
		
		
		/*
		Map<String,String> subPlanMap = (Map<String,String>)RegionalRC.getResource("device2SubPlan").getResourceItem(deviceID);
		
		Iterator it = subPlanMap.entrySet().iterator();
		List<String> oldSubPlanLst = new ArrayList<String>();
		//遍历订阅方案集合进行订阅判断
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
			String subPlanOID = entry.getKey();
			String [] tmpArray = entry.getValue().split("#");
			long subPlanBeginTime = Long.parseLong(tmpArray[0]);
			long subPlanEndTime = Long.parseLong(tmpArray[1]);
			//订阅方案的未到期或过期判断
			if(subPlanBeginTime>new Date().getTime()){ //还未到订阅开始时间
				continue;
			}else if(subPlanEndTime<new Date().getTime()){ //订阅结束时间已过期
				oldSubPlanLst.add(subPlanOID);
				continue;
			}
			//生成订阅结果
			JudgeResult result = new JudgeResult();
			result.setId(subPlanOID);	
			List<WorkStatusData> list = new ArrayList<WorkStatusData>();
			for(int i=2;i<tmpArray.length;i++){ //遍历该订阅方案的参数ID
				if(pdu.getWorkStatusMap().containsKey(tmpArray[i])){
					WorkStatusData newWsd = new WorkStatusData();
					newWsd.setDevice(pdu.getDevice());
					newWsd.setWorkStatus(tmpArray[i]);
					newWsd.setDataValue(pdu.getWorkStatusMap().get(tmpArray[i]));
					newWsd.setTimestamp(pdu.getTimestamp());
					newWsd.setSerialNo(pdu.getBaseInfoMap().get("serialNo"));								
					list.add(newWsd);									
				}							
			}
			if(list.size()>0){
				result.setWorkStatusList(list);
				results.add(result);
			}
		}
		*/
		
		//TODO:如果存在过期订阅方案
		if(oldSubPlanLst.size()>0){
			//修改device2SubPlan资源项
			for(String tmpSubPlanOID:oldSubPlanLst){
				subPlanSet.remove(tmpSubPlanOID);
				//修改数据库订阅方案的记录
				SubPlanDAO dao = (SubPlanDAO)AppContext.getSpringContext().getBean("subPlanDAO");
				SubPlan subPlan = (SubPlan)dao.findById(SubPlan.class, tmpSubPlanOID);
				subPlan.setState(0);
			    dao.update(subPlan);
			    
			    ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_DELETE,"subPlan",tmpSubPlanOID,tmpSubPlanOID);
				RegionalRC.syncChange(changeInfo);	
			}
			
			ChangeInfo changeInfo1 = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,"device2SubPlan",deviceID,newSubPlanLst);
			RegionalRC.syncChange(changeInfo1);			
		}
		return results;	
	}
	
	
}
		
		
		