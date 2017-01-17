package edu.thss.monitor.rsp.service.store;

import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;


/**
 * 用来存储解析后的工况数据
 * @author lihubin
 *
 */
public interface IDataStore {
	
	/**
	 * 给DataStore的companyDataDAO属性赋值
	 * @param companyDataDAO 企业数据访问DAO
	 */
	public void setCompanyDataDAO(ICompanyDataDAO companyDataDAO);
	
	/**
	 * 缓冲区满足一定数量后，保存解析后的工况数据
	 * @param parsedDataPacket 解析后的数据包
	 * @throws RSPException
	 */
	public void saveData(ParsedDataPacket parsedDataPacket) throws RSPException;

	/**
	 * 周期性的保存工况数据到数据库
	 */
	public void saveDataPeriod() throws RSPException;
	
	/**
	 * 设置配置参数
	 * @param param
	 * @throws Exception
	 */
	public void setConfig(String param) throws Exception;
	
}
