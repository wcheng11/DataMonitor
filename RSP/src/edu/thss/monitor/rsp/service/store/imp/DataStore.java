package edu.thss.monitor.rsp.service.store.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;
import edu.thss.monitor.rsp.service.store.IDataStore;

public class DataStore implements IDataStore{
	
	//企业数据存储DAO
	private ICompanyDataDAO companyDataDAO = null;
	
	//100个解析后的数据包进行一次批量存储
	public int storeNum = 50;
	public int parsedDataPacketNum = storeNum;
	//批量存储工况数据的临时列表
	private List<WorkStatusData> batchStoreWorkStatuList = new  ArrayList<WorkStatusData>();
	
	//上次执行存储的时间
	private long lastExecuteTime;
	
	private Thread periodSaver;
	
	public DataStore(){
		initSavePeriod();
	}
	
	@Override
	public void setCompanyDataDAO(ICompanyDataDAO companyDataDAO) {
		this.companyDataDAO = companyDataDAO;
		periodSaver.start();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setConfig(String param) throws Exception {
//		JSONObject jo = JSONObject.fromObject(param);
//		String clzName = jo.getString(DAO_CLASS_NAME);
//		Class clz = Class.forName(clzName);
//		Constructor constructor = clz.getConstructor(String.class); //构造函数参数列表的class类型
//		companyDataDAO = (ICompanyDataDAO)constructor.newInstance(param);		
	}

	@Override
	public void saveData(ParsedDataPacket parsedDataPacket) throws RSPException{
		
		List<WorkStatusData> workStatusDatas = new  ArrayList<WorkStatusData>();
		
		try {
			//3秒批量存一次数据
			if (parsedDataPacketNum > 0 && new Date().getTime()-lastExecuteTime < 3000) {
				workStatusDatas = companyDataDAO.getWorkStatusData(parsedDataPacket);
				synchronized(batchStoreWorkStatuList){
					batchStoreWorkStatuList.addAll(workStatusDatas);
				}
				parsedDataPacketNum--;
			} else {
				workStatusDatas = companyDataDAO.getWorkStatusData(parsedDataPacket);
				synchronized(batchStoreWorkStatuList){
					batchStoreWorkStatuList.addAll(workStatusDatas);
				}
				LogRecord.debug(LogConstant.LOG_FLAG_STORE+"开始存储工况数据");
				boolean result = save();
				
				LogRecord.debug(LogConstant.LOG_FLAG_STORE+"存储工况数据结束:存储"+(result?"成功":"失败"));
				batchStoreWorkStatuList.clear();
				parsedDataPacketNum = storeNum;
			}
			
		} catch (RSPException e) {
			throw new RSPException(LogConstant.LOG_FLAG_STORE + "数据存储发生异常 ",e);
		}
	}
	
	public void saveDataPeriod() throws RSPException {
		try {
			if (!save()){
				System.out.println(LogConstant.LOG_FLAG_STORE+"无可存储工况");
			}
			
		} catch (RSPException e) {
			throw new RSPException(LogConstant.LOG_FLAG_STORE + "数据存储发生异常 ",e);
		}		
	}

	private boolean save() throws RSPException{
		synchronized(batchStoreWorkStatuList){
			if (batchStoreWorkStatuList.size() != 0) {
				companyDataDAO.batchSave(batchStoreWorkStatuList);
				batchStoreWorkStatuList.clear();
				lastExecuteTime = new Date().getTime();
				return true;
			}else{
				lastExecuteTime = new Date().getTime();
				return false;
			}
		}
		
	}
	
	private void initSavePeriod(){
		final DataStore self = this;
		periodSaver = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						self.saveDataPeriod();
//						System.out.println(LogConstant.LOG_FLAG_STORE+"定时保存开始");					
					} catch (RSPException e) {
						// TODO Auto-generated catch block
//						LogRecord.info(LogConstant.LOG_FLAG_STORE+"周期性存储工况数据产生异常");
						e.printStackTrace();
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		periodSaver.setName("PeriodSaver");
		
	}
	
	@Override
	protected void finalize()throws Throwable{
		periodSaver.interrupt();
		periodSaver.join();
		LogRecord.info(LogConstant.LOG_FLAG_STORE+"定时保存已关闭");
	}
	
}
