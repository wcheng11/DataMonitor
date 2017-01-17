package edu.thss.monitor.base.resource.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.thss.monitor.base.resource.bean.ResourceConstant;
import edu.thss.monitor.base.resource.bean.ResourceInfo;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.util.TypeUtil;
/**
 * 资源工具类，封装了资源的通用操作
 * @author yangtao
 */
@SuppressWarnings("unchecked")
public class ResourceUtil {
	
	/**
	 * 获得资源数据
	 * @param rstLst
	 * @param resourceInfo
	 * @return
	 * @throws RSPException
	 */
	public static Map<String,Object> getData(List<Object> rstLst,ResourceInfo resourceInfo) throws RSPException{
		Map<String,Object> dataMap = null;
		if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_MAP)){
			if(resourceInfo.getJpql()!=null){
				dataMap = ResourceUtil.getMapDataFromEntity(rstLst,resourceInfo);
			}else if(resourceInfo.getSql()!=null){
				dataMap = ResourceUtil.getMapDataFromObjArray(rstLst,resourceInfo);
			}
		}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_STRING)){ //String类型可能从JPQL或SQL加载
			if(resourceInfo.getJpql()!=null){
				dataMap = ResourceUtil.getStringDataFromEntity(rstLst,resourceInfo);
			}else if(resourceInfo.getSql()!=null){
				dataMap = ResourceUtil.getStringDataFromObjArray(rstLst,resourceInfo);
			}
		}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_SET)){//Set类型可能从JPQL或SQL加载
			if(resourceInfo.getJpql()!=null){
				dataMap = ResourceUtil.getSetDataFromEntity(rstLst,resourceInfo);
			}else if(resourceInfo.getSql()!=null){
				dataMap = ResourceUtil.getSetDataFromObjArray(rstLst,resourceInfo);
			}
		}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_OBJECT)){
			dataMap = ResourceUtil.getObjectData(rstLst,resourceInfo);
		}else{
			throw new RSPException("资源只有四种数据类型：string | map | set | object, 没有"+resourceInfo.getDataType());
		}
		return dataMap;
	}

	/**
	 * 提取Object类型数据（保存对象的序列化字符串）
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getObjectData(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
 			String getKeyMethodName = "get"+resourceInfo.getKeyAttr().substring(0,1).toUpperCase()+ resourceInfo.getKeyAttr().substring(1);
			Method getKeyMethod = resourceInfo.getClassObj().getMethod(getKeyMethodName, new Class[]{});
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(Object obj:dataLst){
				Object key = getKeyMethod.invoke(obj,new Object[]{});
				String keyStr = String.valueOf(key);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])key);
				}
				dataMap.put(keyStr, obj);
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("加载资源时发生异常",e);
		}
	}
	
	/**
	 * 提取String类型数据
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getStringDataFromEntity(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
 			String getKeyMethodName = "get"+resourceInfo.getKeyAttr().substring(0,1).toUpperCase()+ resourceInfo.getKeyAttr().substring(1);
			Method getKeyMethod = resourceInfo.getClassObj().getMethod(getKeyMethodName, new Class[]{});
			String getValueMethodName = "get"+resourceInfo.getValueAttr().substring(0,1).toUpperCase()+ resourceInfo.getValueAttr().substring(1);
			Method getValueMethod = resourceInfo.getClassObj().getMethod(getValueMethodName, new Class[]{});
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(Object obj:dataLst){
				Object key = getKeyMethod.invoke(obj,new Object[]{});
				Object value = getValueMethod.invoke(obj, new Object[]{});
				String keyStr = String.valueOf(key);
				String valueStr = String.valueOf(value);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])key);
				}
				if(valueStr.startsWith("[B")){
					valueStr = TypeUtil.getBytesString((byte[])value);
				}
				dataMap.put(keyStr,valueStr);
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("提取资源时发生异常",e);
		}
	}
	
	/**
	 * 从对象数组提取String类型数据
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getStringDataFromObjArray(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			Integer keyIndex = Integer.parseInt(resourceInfo.getKeyAttr());
			Integer valueIndex = Integer.parseInt(resourceInfo.getValueAttr());
			for(Object obj:dataLst){
				Object key = ((Object[])obj)[keyIndex];
				Object value = ((Object[])obj)[valueIndex];
				String keyStr = String.valueOf(key);
				String valueStr = String.valueOf(value);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])key);
				}
				if(valueStr.startsWith("[B")){
					valueStr = TypeUtil.getBytesString((byte[])value);
				}
				dataMap.put(keyStr,valueStr);
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("提取资源时发生异常",e);
		}
	}
	
	/**
	 * 提取Set类型数据
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getSetDataFromEntity(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
 			String getKeyMethodName = "get"+resourceInfo.getKeyAttr().substring(0,1).toUpperCase()+ resourceInfo.getKeyAttr().substring(1);
			Method getKeyMethod = resourceInfo.getClassObj().getMethod(getKeyMethodName, new Class[]{});
			String getValueMethodName = "get"+resourceInfo.getValueAttr().substring(0,1).toUpperCase()+ resourceInfo.getValueAttr().substring(1);
			Method getValueMethod = resourceInfo.getClassObj().getMethod(getValueMethodName, new Class[]{});
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(Object obj:dataLst){
				Object key = getKeyMethod.invoke(obj,new Object[]{});
				Object value = getValueMethod.invoke(obj,new Object[]{});
				String keyStr = String.valueOf(key);
				String valueStr = String.valueOf(value);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])key);
				}
				if(valueStr.startsWith("[B")){
					valueStr = TypeUtil.getBytesString((byte[])value);
				}
				Set<String> oneDataSet = (Set<String>)dataMap.get(keyStr);
				if(oneDataSet==null)
					oneDataSet = new HashSet<String>();
				oneDataSet.add(valueStr);
				dataMap.put(keyStr,oneDataSet);
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("提取资源时发生异常",e);
		}
	}
	
	/**
	 * 提取Set类型数据
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getSetDataFromObjArray(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			Integer keyIndex = Integer.parseInt(resourceInfo.getKeyAttr());
			Integer valueIndex = Integer.parseInt(resourceInfo.getValueAttr());
			for(Object obj:dataLst){
				Object key = ((Object[])obj)[keyIndex];
				Object value = ((Object[])obj)[valueIndex];
				String keyStr = String.valueOf(key);
				String valueStr = String.valueOf(value);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])key);
				}
				if(valueStr.startsWith("[B")){
					valueStr = TypeUtil.getBytesString((byte[])value);
				}
				Set<String> oneDataSet = (Set<String>)dataMap.get(keyStr);
				if(oneDataSet==null)
					oneDataSet = new HashSet<String>();
				oneDataSet.add(valueStr);
				dataMap.put(keyStr,oneDataSet);
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("提取资源时发生异常",e);
		}
	}
	
	/**
	 * 提取Map类型数据
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getMapDataFromEntity(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
 			String getKeyMethodName = "get"+resourceInfo.getKeyAttr().substring(0,1).toUpperCase()+ resourceInfo.getKeyAttr().substring(1);
			Method getKeyMethod = resourceInfo.getClassObj().getMethod(getKeyMethodName, new Class[]{});
			String[] valueAttrs = resourceInfo.getValueAttr().split(",");
			List<Method> getValueMethodLst = new ArrayList<Method>();
			for(String valueAttr : valueAttrs){
				String getValueMethodName = "get"+valueAttr.substring(0,1).toUpperCase()+ valueAttr.substring(1);
				Method getValueMethod = resourceInfo.getClassObj().getMethod(getValueMethodName, new Class[]{});
				getValueMethodLst.add(getValueMethod);
			}
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(Object obj:dataLst){
				Object key = getKeyMethod.invoke(obj,new Object[]{});
				String keyStr = String.valueOf(key);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])key);
				}
				Map<String,String> oneDataMap = new HashMap<String,String>();
				for(int i=0;i<getValueMethodLst.size();i++){
					Object value = getValueMethodLst.get(i).invoke(obj,new Object[]{});
					String valueStr = String.valueOf(value);
					if(valueStr.startsWith("[B")){
						valueStr = TypeUtil.getBytesString((byte[])value);
					}
					oneDataMap.put(valueAttrs[i], valueStr);
				}
				dataMap.put(keyStr, oneDataMap);
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("提取资源时发生异常",e);
		}
	}
	
	/**
	 * 提取Map类型数据
	 * @return
	 * @throws RSPException 
	 */
	private static Map<String,Object> getMapDataFromObjArray(List<Object> dataLst,ResourceInfo resourceInfo) throws RSPException{
		try {
			Integer keyIndex = Integer.parseInt(resourceInfo.getKeyAttr());
			String tmp = resourceInfo.getValueAttr();
			String[] tmpArray = tmp.split(":");
			Integer itemKeyIndex = Integer.parseInt(tmpArray[0]);
			Integer itemValueIndex = Integer.parseInt(tmpArray[1]);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(Object obj:dataLst){
				Object[] objArray = (Object[])obj;
				String keyStr = String.valueOf(objArray[keyIndex]);
				String itemKeyStr = String.valueOf(objArray[itemKeyIndex]);
				String itemValueStr = String.valueOf(objArray[itemValueIndex]);
				if(keyStr.startsWith("[B")){
					keyStr = TypeUtil.getBytesString((byte[])objArray[keyIndex]);
				}
				if(itemKeyStr.startsWith("[B")){
					itemKeyStr = TypeUtil.getBytesString((byte[])objArray[itemKeyIndex]);
				}
				if(itemValueStr.startsWith("[B")){
					itemValueStr = TypeUtil.getBytesString((byte[])objArray[itemValueIndex]);
				}
				if(objArray[keyIndex]!=null&&objArray[itemKeyIndex]!=null){//前两个key都不为空时才存入Map
					Map<String,String> oneDataMap = (Map<String,String>)dataMap.get(keyStr);
					if(oneDataMap==null){
						oneDataMap = new HashMap<String,String>();
					}
					oneDataMap.put(itemKeyStr,itemValueStr);
					dataMap.put(keyStr, oneDataMap);
				}
			}
			return dataMap;
		} catch (Exception e) {
			throw new RSPException("提取资源时发生异常",e);
		}
	}
	
}
