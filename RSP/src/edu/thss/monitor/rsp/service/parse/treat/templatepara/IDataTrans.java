package edu.thss.monitor.rsp.service.parse.treat.templatepara;

import edu.thss.monitor.pub.exception.RSPException;

/**
 * 数据转换接口
 * @author zhuangxy
 * 2013-1-17
 */
public interface IDataTrans {

	/**
	 * 数据转换
	 * @param value 转换前的值
	 * @return 转换后的值
	 * @throws RSPException 
	 */
	public String trans(String value) throws RSPException;

}
