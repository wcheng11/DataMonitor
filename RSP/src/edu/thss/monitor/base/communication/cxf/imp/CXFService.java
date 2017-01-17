package edu.thss.monitor.base.communication.cxf.imp;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import edu.thss.monitor.base.communication.cxf.ICXFService;

/**
 * CXF调用Service
 * @author yangtao
 *
 */
public class CXFService implements ICXFService{

	/**
	 * 动态调用方法
	 * @param wsdlUrl - WSDL的URL
	 * @param funcName - 函数名
	 * @param params - 参数值
	 * @return
	 * @throws Exception
	 */
	public Object[] dynamicCall(String wsdlUrl, String funcName, Object... params) throws Exception{
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
        Client client = clientFactory.createClient(wsdlUrl);  
        return client.invoke(funcName, params);  
	}
	
	/**
	 * 返回CXF连接客户端
	 * @param wsdlUrl - WSDL的URL
	 * @return
	 * @throws Exception
	 */
	public Client getClient(String wsdlUrl){
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
        return clientFactory.createClient(wsdlUrl);  
	}
	
}
