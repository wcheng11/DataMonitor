package edu.thss.monitor.base.dataaccess;

import java.util.List;
import java.util.Map;

/**
 * 用来对关系型数据库进行访问的基本DAO
 * @author lihubin
 *
 */
public interface IBaseDAO {
	
	/**
	 * 在关系型数据库中持久化一个对象
	 * @param object 任意类的对象
	 * @return Object
	 */
	public Object save(Object object);

	/**
	 * 在关系数据库中批量持久化多个对象
	 * @param list 任意类的对象列表
	 */
	public void saveBatch(List<Object> list);
	
	/**
	 * 根据类和对象ID查找对象
	 * @param class1 查找类
	 * @param id 对象ID
	 * @return Object
	 */
	public Object findById(Class class1, Object id);
	
	/**
	 * 根据指定的属性值返回查询结果
	 * @param class1 查找类
	 * @param attrMap Map<属性名,值>
	 * @return List<Object>
	 */
	public List<Object> findByAttr(Class class1,Map<String,String> attrMap);
	
	/**
	 * 更新关系型数据库中一个持久化对象
	 * @param object 任意类的对象
	 * @return Object
	 */
	public Object update(Object object);
	
	/**
	 * 删除关系型数据库中一个持久化对象
	 * @param class1 对象的类名
	 * @param oid 对象的oid
	 */
	public void delete(Class class1, Object oid);
	
	/**
	 * 查询某一类的所有对象
	 * @param class1 查找类
	 * @return List<Object> 查找类的所有对象，作为一个list返回
	 */
	public List<Object> findAll(Class class1);
	
	/**
	 * 根据查询类及对象范围得到对象列表
	 * @param class1  查找类
	 * @param firstResult 结果开始序号
	 * @param  maxResults 结果数量
	 * @return List<Object> 参数类的所有对象，作为一个list返回
	 */
	public List<Object> findAll(Class class1,int firstResult,int maxResults);
	
	/**
	 * 根据查询语句得到对象列表
	 * @param queryString 查询语句
	 * @param queryParams 查询参数
	 * @return List<Object> 满足条件的所有对象，作为一个list返回
	 */
	public List<Object> findList(String queryString, Object[] queryParams);
	
	/**
	 * 根据查询语句和结果范围，得到满足条件的对象
	 * @param queryString 查询语句
	 * @param queryParams 查询参数
	 * @param firstResult 结果开始序号
	 * @param  maxResults 结果数量
	 * @return List<Object> 满足条件的所有对象，作为一个list返回
	 */
	public List<Object> findList(String queryString, Object[] queryParams, int firstResult, int maxResults);

	/**
	 * 根据原生SQL查询语句得到对象列表
	 * @param nativeSql - 原生SQL语句
	 * @return
	 */
	List<Object> findListByNativeSql(String nativeSql);
	
	/**
	 * 根据原生SQL查询语句得到对象列表
	 * @param nativeSql - 原生SQL语句
	 * @param firstResult 结果开始序号
	 * @param maxResults 结果数量
	 * @return
	 */
	List<Object> findListByNativeSql(String nativeSql, int firstResult,int maxResults);
	
}
