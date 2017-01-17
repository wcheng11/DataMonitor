package edu.thss.monitor.base.redis;

import java.util.Map;
import java.util.Set;

/**
 * Redis数据访问对象
 * @author huyang
 */
public interface IRedisDAO {

	/**
	 * 加载数据表
	 * @param tableIndex - 表索引
	 * @param dataType - 数据类型
	 * @param tableData - 表数据 
	 */
	public void loadTable(int tableIndex,String dataType,Object tableData);
	
	/**
	 * 清空数据表
	 * @param tableIndex - 表索引
	 */
	public void clearTable(int tableIndex);
	
	/**
	 * 获取Map型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - Map名称
	 * @return Map表中的数据
	 */
	public Map<String,String> getMapData(int tableIndex,String dataKey);
	
	/**
	 * 获取Set型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - Set名称
	 * @return Set中的数据
	 */
	public Set<String> getSetData(int tableIndex,String dataKey);
	
	/**
	 * 获取字符串型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - 字符串名
	 * @return 字符串值
	 */
	public String getStringData(int tableIndex,String dataKey);
	
	
	/**
	 * 获取对象型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - 对象名
	 * @return 对象型数据
	 */
	@SuppressWarnings("unchecked")
	public Object getObjectData(int tableIndex,String dataKey,Class clz);
	
	/**
	 * 添加Map型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - Map名称
	 * @param dataValue - Map中的键值对 
	 */
	public void addData(int tableIndex,String dataKey,Map<String,String> dataValue);
	
	/**
	 * 添加Set型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - Set名称
	 * @param dataValue - Set元素值
	 */
	public void addData(int tableIndex,String dataKey,Set<String> dataValue);
	
	/**
	 * 添加String数据
	 * @param tableIndex - 表索引
	 * @param dataKey - 字符串名称
	 * @param dataValue - 字符串值  
	 */
	public void addData(int tableIndex,String dataKey,String dataValue);
	
	/**
	 * 添加Object型数据
	 * @param tableIndex - 表索引
	 * @param dataKey - 对象名称
	 * @param dataValue - 对象值  
	 */
	public void addData(int tableIndex,String dataKey,Object dataValue);
	
	/**
	 * 删除数据
	 * @param tableIndex - 表索引
	 * @param dataKey - 数据名称（例如Map名称、Set名称）
	 */
	public void deleteData(int tableIndex,String dataKey);
	
	/**
	 * 更新Map型数据
	 * @param tableIndex - 表索引
	 * @param objKey - Map名称
	 * @param objValue - Map中的键值对
	 */
	public void updateData(int tableIndex,String objKey,Map<String,String> objValue);
	
	/**
	 * 更新Set型数据
	 * @param tableIndex - 表索引
	 * @param objKey - Set名称
	 * @param objValue - Set元素值 
	 */	
	public void updateData(int tableIndex,String objKey,Set<String> objValue);
	
	/**
	 * 更新String数据
	 * @param tableIndex - 表索引
	 * @param objKey - 字符串名称
	 * @param objValue - 字符串值 
	 */
	public void updateData(int tableIndex,String objKey,String objValue);
	
	/**
	 * 更新对象型数据
	 * @param tableIndex - 表索引
	 * @param objKey - 对象名
	 * @param objValue - 对象值
	 */
	public void updateData(int tableIndex,String objKey,Object objValue);
	
//	public void updateData(int tableIndex,String dataType, String objKey,Object objValue);
	
}
