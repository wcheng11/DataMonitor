package edu.thss.monitor.base.communication.thrift.service;

import java.nio.ByteBuffer;

import org.apache.thrift.TException;

import edu.thss.monitor.base.communication.thrift.autogen.ThriftProcess;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.bean.ResourceCarrier;
import edu.thss.monitor.base.resource.sync.ISyncServerProxy;
import edu.thss.monitor.base.resource.util.KryoUtil;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
/**
 * Storm处理节点的Thrift服务
 * @author yangtao
 */
public class ThriftProcessService implements ThriftProcess.Iface {

	private static ISyncServerProxy syncServerProxy;
	
	static{
		syncServerProxy = (ISyncServerProxy)AppContext.getSpringContext().getBean("syncServerProxy");
	}

	@Override
	public void thriftReceiveChange(ByteBuffer param) throws TException {
		//序列化字节转化为更改对象
		ChangeInfo changeInfo = KryoUtil.deserialize(param, ChangeInfo.class);
		//调用同步服务器端代理的服务器端同步方法
		try {
			syncServerProxy.serverChangeSync(changeInfo);
		} catch (RSPException e) {
			new RSPException("THRIFT服务(接收更改)发生异常",e);
		}
	}

	@Override
	public void thriftSetLogLevel(String level) throws TException {
		LogRecord.setLogLevel(level);
	}

	@Override
	public void thriftReloadResource(String resourceType, ByteBuffer param)
			throws TException {
		ResourceCarrier carrier = KryoUtil.deserialize(param, ResourceCarrier.class);
		try {
			syncServerProxy.reloadResource(resourceType, carrier);
		} catch (RSPException e) {
			new RSPException("THRIFT服务(重新加载资源)发生异常",e);
		}
	}

}
