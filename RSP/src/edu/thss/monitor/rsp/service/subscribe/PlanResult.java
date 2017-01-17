package edu.thss.monitor.rsp.service.subscribe;

import java.util.Date;
import java.util.List;

import edu.thss.monitor.pub.entity.Device;

public class PlanResult {
	
	private String oid;	

	private Integer pushType;
	
	private String pushParam;
	
	private Date beginTime;
	
	private Date endTime;
	
	private List<String> workStatusList;
	
	public List<String> getWorkStatusList() {
		return workStatusList;
	}

	public void setWorkStatusList(List<String> workStatusList) {
		this.workStatusList = workStatusList;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public String getPushParam() {
		return pushParam;
	}

	public void setPushParam(String pushParam) {
		this.pushParam = pushParam;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    /**
     * 判断方案结果是否时间有效
     * @param curTime 当前时间
     * @return        
     */
	public boolean IsValid(Date curTime){
		if(curTime.compareTo(this.getBeginTime()) >=0 
				             && curTime.compareTo(this.getEndTime())<=0){
			return true;
		}
		return false;		
	}
	
	/**
	 * 判断方案结果是否过期
	 * @param curTime 当前时间
	 * @return
	 */
	public boolean IsOutOfDate(Date curTime){
		if(curTime.compareTo(this.getEndTime())>0){
			return true;
		}
		return false;
	}
	

}
