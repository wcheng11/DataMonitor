package edu.thss.monitor.rsp.service.store.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;
import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;

public class SanyDataDAO extends LaUDBaseDAO implements ICompanyDataDAO{

	private String columnfamily = null;

	private String KEY_SPACE = "keySpace";
	
	private String COLUMN_FAMILY = "columnFamily";
	
	public void setColumnfamily(String string) {
		this.columnfamily = string;		
	}
	
	private String keyspace = null;
	
	public void setKeyspace(String string) {
		this.keyspace = string;		
	}
	
	public SanyDataDAO(){}
			
	public SanyDataDAO(String param){
		JSONObject jsObj = JSONObject.fromObject(param);
		//columnfamily = jsObj.getString(COLUMN_FAMILY);
		keyspace = jsObj.getString(KEY_SPACE);
		if(connectPool==null)
			connectPool = (ConnectPool)AppContext.getSpringContext().getBean("connectPool");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkStatusData> getWorkStatusData(
			ParsedDataPacket parsedDataPacket) {
		
		List<WorkStatusData> workStatusDatas = new ArrayList<WorkStatusData>();
		
		//获取解析后报文中的设备
		Device device = parsedDataPacket.getDevice();
//		
//		if(device == null){
//			LogRecord.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//		}else {
//			LogRecord.info("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD: " + device.getDeviceID());
//		}
		
		
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

	public String spliceColumnFamily(WorkStatusData workStatusData) {
		
		StringBuffer sb = new StringBuffer("cf_gk_");
		sb.append(workStatusData.getWorkStatus());
		return sb.toString();
		
	}

	public String spliceRowKey(WorkStatusData workStatusData) {
		
		return workStatusData.getDevice().getDeviceID();
		
	}
	
	public String spliceColumnName(WorkStatusData workStatusData) {

		long time = workStatusData.getTimestamp().getTime();
		String columnName = Long.toString(time);
		return columnName;
		
	}
	
	@Override
	public void save(WorkStatusData workStatusData) throws RSPException {
		
		String useKeyspace = "use " + keyspace + ";";
		this.execute(useKeyspace);
		String rowKey = this.spliceRowKey(workStatusData);
		String columnName = this.spliceColumnName(workStatusData);
		String columnFamily = this.spliceColumnFamily(workStatusData);
		String lasql = "insert into " + columnFamily + "(equip,'" + columnName +
				"') values('" + rowKey + "','" + workStatusData.getDataValue() + "')";
		try {
			execute(lasql);
		} catch (RSPException e) {
			throw new RSPException("往LaUD数据库中插入一条数据异常！",e);
		}
		
	}

	@Override
	public void batchSave(List<WorkStatusData> workStatusDataslist) throws RSPException {
		
		String useKeyspace = "use " + keyspace + ";";
		this.execute(useKeyspace);
		
		//根据List<WorkStatusData>生成lasqlList 
		List<String> lasqlList = new ArrayList<String>();
		for (WorkStatusData workStatusData : workStatusDataslist) {
			String rowKey = this.spliceRowKey(workStatusData);
			String columnName = this.spliceColumnName(workStatusData);	
			String columnFamily = this.spliceColumnFamily(workStatusData);
			String lasql = "insert into " + columnFamily + "(equip,'" + columnName +
					"') values('" + rowKey + "','" + workStatusData.getDataValue() + "')";
			lasqlList.add(lasql);
		}
		
		try {
			batchExecute(lasqlList);
		} catch (RSPException e) {
			throw new RSPException("往LaUD数据库中批量插入数据异常！",e);
		}
		
	}


}
