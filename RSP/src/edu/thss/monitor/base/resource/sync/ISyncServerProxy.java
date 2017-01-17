package edu.thss.monitor.base.resource.sync;

import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.bean.ResourceCarrier;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 资源同步服务器端接口(运行在Storm处理节点)
 * @author yangtao
 */
public interface ISyncServerProxy {

	/**
	 * 设置资源服务器信息
	 * @param serverInfo
	 */
	public void setServerInfo(SyncNodeInfo serverInfo);
	
	/**
	 * 获得资源服务器信息
	 * @param serverInfo
	 */
	public SyncNodeInfo getServerInfo();
	
	/**
	 * 资源同步客户端注册(RCP发送)
	 * @return 分配的端口号
	 * @throws RSPException 
	 */
	public String register() throws RSPException;
	
	/**
	 * 服务器更改同步(RCP接收)
	 * @param changeInfo - 更改信息
	 * @throws RSPException 
	 */
	public void serverChangeSync(ChangeInfo changeInfo) throws RSPException;
	
	/**
	 * 客户端更改同步(RCP发送)
	 * @param changeInfo - 更改信息
	 * @throws RSPException 
	 */
	public void clientChangeSync(ChangeInfo changeInfo) throws RSPException;
	
	/**
	 * 获得资源(RCP发送)
	 * @param resourceType - 资源类型
	 * @return
	 * @throws RSPException 
	 */
	public IResource getResource(String resourceType) throws RSPException;
	
	/**
	 * 重新加载内存资源(RCP接收)
	 * @param resourceType - 资源类型
	 * @return
	 * @throws RSPException 
	 */
	public void reloadResource(String resourceType,ResourceCarrier carrier) throws RSPException;
	
}
