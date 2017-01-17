package edu.thss.monitor.base.resource.sync;

import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 资源同步客户端代理接口(运行在资源服务器端)
 * @author yangtao
 */
public interface ISyncClientProxy {

	/**
	 * 得到客户端信息(本地)
	 * @return
	 */
	public SyncNodeInfo getClientInfo();
	
	/**
	 * 设置客户端信息(本地)
	 * @return
	 */
	public void setClientInfo(SyncNodeInfo clientInfo);
	
	/**
	 * 服务器更改同步(RPC发送)
	 * @param changeInfo - 更改信息
	 * @throws RSPException 
	 */
	public void serverChangeSync(ChangeInfo changeInfo) throws RSPException;
	
	/**
	 * 客户端更改同步(RPC接收)
	 * @param changeInfo - 更改信息
	 */
	public void clientChangeSync(ChangeInfo changeInfo);
	
	/**
	 * 获得资源(RPC接收)
	 * @param resourceType
	 * @return
	 */
	public IResource getResource(String resourceType);
	
}
