package edu.thss.monitor.base.resource.sync.imp;

import java.net.SocketException;
import java.nio.ByteBuffer;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import edu.thss.monitor.base.communication.thrift.autogen.ThriftResource;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.bean.ResourceCarrier;
import edu.thss.monitor.base.resource.imp.MemoryResource;
import edu.thss.monitor.base.resource.imp.RedisResource;
import edu.thss.monitor.base.resource.sync.ISyncServerProxy;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.base.resource.util.KryoUtil;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.util.MachineEnvUtil;

public class SyncServerProxy implements ISyncServerProxy{

//	private Log logger = LogFactory.getLog(SyncServerProxy.class);
	
	/**
	 * 同步服务器信息
	 */
	private SyncNodeInfo serverInfo;

	/**
	 * 同步客户端信息
	 */
	private SyncNodeInfo clientInfo;
	
	@Override
	public void setServerInfo(SyncNodeInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	@Override
	public void clientChangeSync(ChangeInfo changeInfo) throws RSPException {
		LogRecord.info("地方资源容器发送更改信息(客户端:"+clientInfo.toString()+")");
		if(LogRecord.isDebugEnabled()){
			LogRecord.debug(changeInfo.toString());
		}
		changeInfo.setChangeSponsor(clientInfo.toString());
		ByteBuffer param = KryoUtil.serialize(changeInfo);
		try {
			TTransport transport = new TSocket(serverInfo.getIp(), serverInfo.getPort());
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftResource.Client client = new ThriftResource.Client(protocol);
			transport.open();
			client.thriftReceiveChange(param);
			transport.close();
		}catch (TException e) {
			throw new RSPException("地方资源容器发送更改信息时发生异常!",e);
		}
	}

	@Override
	public IResource getResource(String resourceType) throws RSPException {
		//thrift
		ByteBuffer rst = null;
		try {
			TTransport transport = new TSocket(serverInfo.getIp(), serverInfo.getPort());
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftResource.Client client = new ThriftResource.Client(protocol);
			transport.open();
			rst = client.thriftGetResource(resourceType,clientInfo.toString());
			transport.close();
		}catch (TException e) {
			if (e instanceof TApplicationException 
                    && ((TApplicationException) e).getType() ==   
                                 TApplicationException.MISSING_RESULT) { 
                //处理空值异常
				return null;
            }else
            	throw new RSPException("地方资源容器获取资源时发生异常!",e);
		}
		ResourceCarrier carrier = KryoUtil.deserialize(rst, ResourceCarrier.class);
		if(carrier.getType()==ResourceCarrier.TYPE_MEMORY){
			return new MemoryResource(carrier.getMap());
		}else if(carrier.getType()==ResourceCarrier.TYPE_REDIS){
			return new RedisResource(carrier.getResourceInfo());
		}else{
			return null;
		}
	}

	@Override
	public void serverChangeSync(ChangeInfo changeInfo) throws RSPException {
		LogRecord.info("地方资源容器接收更改信息"+changeInfo);
		RegionalRC.acceptChange(changeInfo); //地方资源容器接收更改
	}

	@Override
	public String register() throws RSPException {
		ByteBuffer rst = null;
		String ip = null;
		try {
			ip = MachineEnvUtil.getMachineIPByFilter(serverInfo.getFilterMask());//.getMachineIP();!!!注意改filter
//			ip = MachineEnvUtil.getMachineIP();
		} catch (SocketException e) {
			throw new RSPException("获取本机IP发生异常!",e);
		}
		try {
			TTransport transport = new TSocket(serverInfo.getIp(), serverInfo.getPort());
			TProtocol protocol = new TCompactProtocol(transport);
			ThriftResource.Client client = new ThriftResource.Client(protocol);
			transport.open();
			rst = client.thriftRegister(ByteBuffer.wrap(ip.getBytes()));
			transport.close();
		}catch (TException e) {
			throw new RSPException("地方资源容器获取资源发生异常!",e);
		}
		String port = new String(rst.array());
		clientInfo = new SyncNodeInfo(ip,Integer.parseInt(port)); //设置客户端信息
		LogRecord.info("地方资源容器注册成功，分配的端口号为"+port);
		return port;
	}

	@Override
	public SyncNodeInfo getServerInfo() {
		return serverInfo;
	}

	@Override
	public void reloadResource(String resourceType, ResourceCarrier carrier)
			throws RSPException {
		if(carrier.getType()==ResourceCarrier.TYPE_MEMORY){
			MemoryResource resource = (MemoryResource)RegionalRC.getResource(resourceType);
			if(resource!=null){
				resource.updateResource(carrier.getMap());
				LogRecord.info("地方资源容器重新加载资源:"+resourceType);
			}
		}
	}
	
}
