package edu.thss.monitor.base.resource;

import java.util.HashMap;
import java.util.Map;

import edu.thss.monitor.base.communication.thrift.listener.ProcessListener;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.bean.ResourceInfo;
import edu.thss.monitor.base.resource.imp.RedisResource;
import edu.thss.monitor.base.resource.sync.ISyncServerProxy;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 地方资源容器
 * @author yangtao
 */
public class RegionalRC{

	private static ISyncServerProxy syncServerProxy;
	
	protected RegionalRC(){}
	
	private static boolean hasRegistered = false;
	
	/**
	 * 设置资源同步服务器信息
	 * @param serverInfo - 同步服务器信息
	 */
	public static void setServerInfo(SyncNodeInfo serverInfo) {
		RegionalRC.syncServerProxy.setServerInfo(serverInfo);
	}

	public void setSyncServerProxy(ISyncServerProxy syncServerProxy) {
		RegionalRC.syncServerProxy = syncServerProxy;
	}

	/**
	 * 资源Map (key-资源类型,value-资源)
	 */
	protected static Map<String,IResource> resourceMap = new HashMap<String,IResource>();
	
	/**
	 * 注册,该方法只执行一次
	 * @throws RSPException 
	 */
	public static void register() throws RSPException{
		System.out.println("........register");
		if(!hasRegistered){
			LogRecord.info("地方资源容器发送注册信息...[中央资源服务器:"+syncServerProxy.getServerInfo().getIp()
					+":"+syncServerProxy.getServerInfo().getPort()+"]");
			//获得分配的端口号
			String port = syncServerProxy.register();
			LogRecord.info("地方资源容器注册后获得分配的端口号:"+port);
			//启动地方资源容器的同步服务线程
			Thread thread = new Thread(new ProcessListener(Integer.parseInt(port)));
			thread.start();
			LogRecord.info("地方资源容器启动同步服务线程");
			hasRegistered = true;
		}
	}
	
	/**
	 * 获取资源
	 * @param resourceType - 资源类型
	 * @return 资源
	 */
	public static IResource getResource(String resourceType){
		return resourceMap.get(resourceType);
	}
	
	/**
	 * 获取资源项
	 * @param resourceType - 资源类型
	 * @return 资源
	 */
	public static Object getResourceItem(String resourceType,String resourceKey){
		IResource resource = resourceMap.get(resourceType);
		
//	    System.out.print("s输出便利key");  
//		for(Object key : resourceMap.keySet()){   //只能遍历key  
//		    System.out.print("resourceMapKey = "+key);  
//		} 
//	    
//		for(Object value : resourceMap.values()){   //只能遍历key  
//		    System.out.print("resourceMapKey = "+value);  
//		}  		
				
		if(resource!=null){
		  //System.out.print("resourceMapKey = "+resource.getResource().toString()); 
			return resource.getResourceItem(resourceKey);
		}
		return null;
	}
	
	/**
	 * 加载资源（需要进行同步）
	 * @param resourceTypeArray - 需要加载的资源类型数组
	 * @throws RSPException 
	 */
	public synchronized static void loadResource(String[] resourceTypeArray) throws RSPException{
//		if(!hasRegistered){ //若未注册则首先进行注册
//			register();
//		}
		for(String resourceType: resourceTypeArray){
			if(resourceMap.get(resourceType)==null){
				LogRecord.info("地方资源容器加载资源:"+resourceType);
				IResource resource = syncServerProxy.getResource(resourceType);
				LogRecord.info("地方资源容器资源"+resourceType+"加载完毕。");
				if(resource instanceof RedisResource){
					//由于ResourceInfo对象传递过来classObj中变成了ResourceCarrier，此处设置为null
					((ResourceInfo)resource.getResource()).setClassObj(null);
				}
				resourceMap.put(resourceType, resource);
			}
		}
	}
	
	/**
	 * 同步更改（更改本地资源数据，同时将更改信息通知给中央资源容器）
	 * @param changeInfo - 更改信息
	 * @throws RSPException 
	 */
	public static void syncChange(ChangeInfo changeInfo) throws RSPException{
		//打印信息
		LogRecord.info("地方资源容器发送更改信息");
//		if(LogRecord.isDebugEnabled()){
			LogRecord.info(changeInfo.toString());
//		}
		//根据更改信息更新地方资源容器
		IResource resource = resourceMap.get(changeInfo.getResourceType());
		if(resource!=null){
			if(changeInfo.getChangeType()==ChangeInfo.CHANGETYPE_ADD){//添加资源项
				resource.addResourceItem(changeInfo.getResourceKey(), changeInfo.getResourceItem());
			}else if(changeInfo.getChangeType()==ChangeInfo.CHANGETYPE_DELETE){//删除资源项
				resource.deleteResourceItem(changeInfo.getResourceKey());
			}else if(changeInfo.getChangeType()==ChangeInfo.CHANGETYPE_UPDATE){//更新资源项
				resource.updateResourceItem(changeInfo.getResourceKey(), changeInfo.getResourceItem());
			}
			if(resource instanceof RedisResource){ //如果是Redis资源则不需要同步中央资源容器
				return;
			}
		}else{
			new RSPException("同步更改时发生异常,不存在资源："+changeInfo.getResourceType());
			return;
		}
		//将更改信息通知给中央资源容器
		syncServerProxy.clientChangeSync(changeInfo);
	}
	
	/**
	 * 接收更改（只更改本地资源数据）
	 * @param changeInfo - 更改信息
	 * @throws RSPException 
	 */
	public static void acceptChange(ChangeInfo changeInfo) throws RSPException{
		//打印信息
		LogRecord.info("地方资源容器接收更改信息");
//		if(LogRecord.isDebugEnabled()){
			LogRecord.info(changeInfo.toString());
//		}
		//根据更改信息更新地方资源容器
		IResource resource = resourceMap.get(changeInfo.getResourceType());
		if(resource!=null){
			if(changeInfo.getChangeType()==ChangeInfo.CHANGETYPE_ADD){//添加资源项
				resource.addResourceItem(changeInfo.getResourceKey(), changeInfo.getResourceItem());
			}else if(changeInfo.getChangeType()==ChangeInfo.CHANGETYPE_DELETE){//删除资源项
				resource.deleteResourceItem(changeInfo.getResourceKey());
			}else if(changeInfo.getChangeType()==ChangeInfo.CHANGETYPE_UPDATE){//更新资源项
				resource.updateResourceItem(changeInfo.getResourceKey(), changeInfo.getResourceItem());
			}
		}
	}
	
}
