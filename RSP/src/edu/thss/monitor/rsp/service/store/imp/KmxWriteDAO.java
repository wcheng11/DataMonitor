/**
 * @package edu.thss.monitor.rsp.service.store.imp
 */
package edu.thss.monitor.rsp.service.store.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sagittarius.bean.bulk.BulkStringData;
import com.sagittarius.bean.common.TimePartition;
import com.sagittarius.write.Writer;

import edu.thss.monitor.base.dataaccess.imp.KMXBaseDAO;
import edu.thss.monitor.base.timerecord.TimeLogger;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;
import oracle.net.aso.s;

/**
 * @author Administrator
 * @time 2017年1月6日
 */
public class KmxWriteDAO extends KMXBaseDAO implements ICompanyDataDAO{

	Writer writer;
	
	public void init(){
		super.init();
		writer = getWriter();
	}
	/* (non-Javadoc)
	 * @see edu.thss.monitor.rsp.service.store.ICompanyDataDAO#getWorkStatusData(edu.thss.monitor.pub.entity.service.ParsedDataPacket)
	 */
	@Override
	public List<WorkStatusData> getWorkStatusData(ParsedDataPacket parsedDataPacket) {
		// TODO Auto-generated method stub
		List<WorkStatusData> workStatusDatas = new ArrayList<WorkStatusData>();
		
		//获取解析后报文中的设备
		Device device = parsedDataPacket.getDevice();
		
		//获取解析后报文中的时间戳
		Date timeStamp = new Date(parsedDataPacket.getTimestamp());
		
		//获取解析后报文中的流水号
		Map<String, String> baseInfoList = parsedDataPacket.getBaseInfoMap();
		String serialNo = baseInfoList.get("serialNo");
		
		//根据parsedDataPacket生成WorkStatusData的集合
		Iterator iterator = parsedDataPacket.getWorkStatusMap().entrySet().iterator();
		while (iterator.hasNext()) {
			WorkStatusData workStatusData = new WorkStatusData();
			
			workStatusData.setDevice(device);
			workStatusData.setTimestamp(timeStamp);
			workStatusData.setSerialNo(serialNo);
			
			Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
			workStatusData.setWorkStatus(entry.getKey());
			workStatusData.setDataValue(entry.getValue());
			workStatusDatas.add(workStatusData);
		}
		
		return workStatusDatas;
	}

	/* (non-Javadoc)
	 * @see edu.thss.monitor.rsp.service.store.ICompanyDataDAO#save(edu.thss.monitor.pub.entity.service.WorkStatusData)
	 */ 
	@Override
	public void save(WorkStatusData workStatusData) throws RSPException {
		// TODO Auto-generated method stub
		if(workStatusData.getWorkStatus().equals("800028")){
			System.out.println("--KMX--SAVE--800028---"+workStatusData.getDataValue()+"---");
			TimeLogger.log(workStatusData.getDataValue());
		}
		writer.insert(workStatusData.getDevice().getDeviceID(), workStatusData.getWorkStatus(), workStatusData.getTimestamp().getTime(), new Date().getTime(), TimePartition.DAY, workStatusData.getDataValue());
	}

	/* (non-Javadoc)
	 * @see edu.thss.monitor.rsp.service.store.ICompanyDataDAO#batchSave(java.util.List)
	 */
	@Override
	public void batchSave(List<WorkStatusData> workStatusDataslist) throws RSPException {
		// TODO Auto-generated method stub
		Date start = new Date();
		BulkStringData datas = new BulkStringData();
		for(WorkStatusData workStatusData:workStatusDataslist){
//			if(workStatusData.getWorkStatus().equals("800028")){
//				TimeLogger.log(workStatusData.getDataValue());
//			}
			datas.addData(workStatusData.getDevice().getDeviceID(), workStatusData.getWorkStatus(), workStatusData.getTimestamp().getTime(), new Date().getTime(), TimePartition.DAY, workStatusData.getDataValue());
			
		}
		writer.bulkInsert(datas, 3);
		Date end = new Date();
		double time = (double)(end.getTime() - start.getTime());
		time = time/workStatusDataslist.size();
		TimeLogger.log(new Date().getTime() + "--" + time + "--" + workStatusDataslist.size());
	}
}
