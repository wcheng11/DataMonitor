package edu.thss.monitor.base.resource;

import edu.thss.monitor.pub.exception.RSPException;

public interface IResource {

	/**
	 * 添加资源项
	 * @param key - 资源项key
	 * @param value - 资源项值
	 */
	public void addResourceItem(Object key, Object value);
	
	/**
	 * 获得资源(地方资源容器同步时传递，内存资源提供数据、Redis资源提供资源信息)
	 * @return
	 */
	public Object getResource();
	
	/**
	 * 获得资源项
	 * @param key - 资源项key
	 * @return
	 */
	public Object getResourceItem(Object key);
	
	/**
	 * 删除资源项
	 * @param key - 资源项key
	 */
	public void deleteResourceItem(Object key);
	
	/**
	 * 更新资源项
	 * @param key - 资源项key
	 * @param value - 资源项值
	 */
	public void updateResourceItem(Object key, Object value);
	
	/**
	 * 根据资源信息加载
	 */
	public void load() throws RSPException;

	/**
	 * 根据对象获得资源key
	 * @throws RSPException 
	 */
	Object getResourceKey(Object obj);
	
}
