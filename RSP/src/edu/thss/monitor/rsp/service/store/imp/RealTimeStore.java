package edu.thss.monitor.rsp.service.store.imp;

import java.util.ArrayList;
import java.util.List;

import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;
import edu.thss.monitor.rsp.service.store.IDataStore;

public class RealTimeStore implements IDataStore {

	// 企业数据存储DAO
	private ICompanyDataDAO companyDataDAO = null;

	@Override
	public void setCompanyDataDAO(ICompanyDataDAO companyDataDAO) {
		this.companyDataDAO = companyDataDAO;
	}

	@Override
	public void setConfig(String param) throws Exception {
		// JSONObject jo = JSONObject.fromObject(param);
		// String clzName = jo.getString(DAO_CLASS_NAME);
		// Class clz = Class.forName(clzName);
		// Constructor constructor = clz.getConstructor(String.class);
		// //构造函数参数列表的class类型
		// companyDataDAO = (ICompanyDataDAO)constructor.newInstance(param);
	}

	@Override
	public void saveData(ParsedDataPacket parsedDataPacket) throws RSPException {
		List<WorkStatusData> workStatusDatas = new ArrayList<WorkStatusData>();
		workStatusDatas = companyDataDAO.getWorkStatusData(parsedDataPacket);
		companyDataDAO.batchSave(workStatusDatas);
	}

}
