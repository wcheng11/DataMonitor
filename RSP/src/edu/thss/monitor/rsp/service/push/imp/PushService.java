package edu.thss.monitor.rsp.service.push.imp;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import edu.thss.monitor.base.communication.thrift.autogen.ThriftPlatform;
import edu.thss.monitor.base.communication.thrift.pool.IThriftConnectionPool;
import edu.thss.monitor.base.resource.util.KryoUtil;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.entity.service.LiteWSD;
import edu.thss.monitor.pub.entity.service.PushData;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.push.IPushService;
/**
 * 推送业务逻辑类
 * @author yangtao
 */
public class PushService implements IPushService {

//	/**
//	 * 推送类型
//	 */
//	public final static Integer PUSHTYPE_CXF = 1;
//	public final static Integer PUSHTYPE_THRIFT = 2;
	
//	/**
//	 * CXF客户端缓存
//	 */
//	private Map<String,Client> cxfClientMap = new HashMap<String,Client>();
	
//	/**
//	 * Thrfit客户端缓存
//	 */
//	private Map<String,ThriftClient> thriftClientMap = new HashMap<String,ThriftClient>();
	
	//平台服务器Thrift连接池
	private static IThriftConnectionPool platformThriftConnPool;
	public void setPlatformThriftConnPool( //spring注入
			IThriftConnectionPool platformThriftConnPool) {
		PushService.platformThriftConnPool = platformThriftConnPool;
	}
	
	@Override
	public void push(JudgeResult jr) throws RSPException {
		//根据订阅判断结果组装推送数据对象
		String deviceId = null;
		if(jr.getWorkStatusList()==null||jr.getWorkStatusList().size()==0){
			return;
		}else{
			deviceId = jr.getWorkStatusList().get(0).getDevice().getDeviceID();
		}
		String subscribeId = jr.getId();
		List<LiteWSD> dataLst = new ArrayList<LiteWSD>();
		for(WorkStatusData wsd:jr.getWorkStatusList()){
			LiteWSD lwsd = new LiteWSD(wsd.getWorkStatus(),wsd.getDataValue(),wsd.getTimestamp().getTime(),wsd.getSerialNo());
			dataLst.add(lwsd);
		}
		PushData pd = new PushData(subscribeId,deviceId,dataLst);
		//根据推送类型进行推送
//		Integer pushType = jr.getPushType();
//		String pushParam = jr.getPushParam();
//        JSONObject pushParamJson = JSONObject.fromObject(pushParam);
//		if(pushType==PUSHTYPE_CXF){
//			try {
//				String wsdlUrl = (String)pushParamJson.get("wsdlUrl");
//				String methodName = (String)pushParamJson.get("method");
//				Client client = cxfClientMap.get(wsdlUrl);
//				if(client==null){
//					//不存在client缓存则新建并加入缓存
//					JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
//			        client = clientFactory.createClient(wsdlUrl); 
//			        cxfClientMap.put(wsdlUrl, client);
//				}
//				client.invoke(methodName, pd);
//			} catch (Exception e) {
//				throw new RSPException(LogConstant.LOG_FLAG_PUSH+"cxf推送数据发生异常!",e);
//			}
//		}else 
//		if(pushType==PUSHTYPE_THRIFT){
			ByteBuffer param = KryoUtil.serialize(pd);
			//发送
			try {
				TTransport transport = platformThriftConnPool.getConnection();
				TProtocol protocol = new TCompactProtocol(transport);
				ThriftPlatform.Client client = new ThriftPlatform.Client(protocol);
				client.thriftReceivePushData(param);
				platformThriftConnPool.releaseConn((TSocket)transport);
			} catch (TException e) {
				e.printStackTrace();
				throw new RSPException(LogConstant.LOG_FLAG_PUSH+"数据推送发生异常!",e);
			}
//		}else{
//			throw new RSPException(LogConstant.LOG_FLAG_PUSH+"不存在推送类型"+pushType+"!");
//		}
	}

//	/**
//	 * Thrift客户端对象
//	 * @author yangtao
//	 */
//	public class ThriftClient{
//		private TTransport transport;
//		private ThriftPlatform.Client client;
//		public ThriftClient(TTransport transport,ThriftPlatform.Client client){
//			this.transport = transport;
//			this.client = client;
//		}
//		public TTransport getTransport() {
//			return transport;
//		}
//		public void setTransport(TTransport transport) {
//			this.transport = transport;
//		}
//		public ThriftPlatform.Client getClient() {
//			return client;
//		}
//		public void setClient(ThriftPlatform.Client client) {
//			this.client = client;
//		}
//	}
}
