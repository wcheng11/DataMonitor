package edu.thss.monitor.rsp.service.store;

import java.util.List;

import edu.thss.monitor.base.dataaccess.ILaUDBaseDAO;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 企业数据访问DAO
 * @author lihubin
 *
 */
public interface ICompanyDataDAO{
	
	/**
	 * 根据数据解析模块解析后的ParsedDataPacket，生成待存储的工况数据列表
	 * @param parsedDataPacket 解析后的工况数据包
	 * @return List<WorkStatusData> 工况数据列表
	 */
	public List<WorkStatusData> getWorkStatusData(ParsedDataPacket parsedDataPacket);

	
	/**
	 * 存储一条工况数据
	 * @param workStatusData 工况数据
	 * @throws RSPException LaUD数据库执行LaSql语句异常
	 */
	public void save(WorkStatusData workStatusData) throws RSPException;
	
	/**
	 * 批量存储工况数据
	 * @param workStatusDataslist
	 * @throws RSPException LaUD数据库执行LaSql语句异常
	 */
	public void batchSave(List<WorkStatusData> workStatusDataslist) throws RSPException;

}
