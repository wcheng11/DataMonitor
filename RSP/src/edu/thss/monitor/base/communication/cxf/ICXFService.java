package edu.thss.monitor.base.communication.cxf;

import org.apache.cxf.endpoint.Client;

/**
 * CXF服务
 * @author yangtao
 */
public interface ICXFService {

	/**
	 * 动态调用CXF服务
	 * @param wsdlUrl - WSDL的URL
	 * @param funcName - 函数名
	 * @param params - 参数值
	 * @return
	 * @throws Exception
	 */
	public Object[] dynamicCall(String wsdlUrl, String funcName, Object... params) throws Exception;
	
	/**
	 * 返回CXF连接客户端
	 * @param wsdlUrl - WSDL的URL
	 * @return
	 * @throws Exception
	 */
	public Client getClient(String wsdlUrl);
	
}
