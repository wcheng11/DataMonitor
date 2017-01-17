package edu.thss.monitor.pub.entity.service;

import java.io.Serializable;
import java.util.List;

/**
 * 推送判断结果的封装对象
 * @author zhuangxy
 * 2012-12-30
 */
@SuppressWarnings("serial")
public class JudgeResult implements Serializable {
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public Integer getPushType() {
//		return pushType;
//	}
//
//	public void setPushType(Integer pushType) {
//		this.pushType = pushType;
//	}
//
//	public String getPushParam() {
//		return pushParam;
//	}
//
//	public void setPushParam(String pushParam) {
//		this.pushParam = pushParam;
//	}

//	private Integer pushType;
//	
//	private String pushParam;	
	
	
	
	/**
	 * 推送的工况数据
	 */
	private List<WorkStatusData> workStatusList;

	

	public List<WorkStatusData> getWorkStatusList() {
		return workStatusList;
	}

	public void setWorkStatusList(List<WorkStatusData> workStatusList) {
		this.workStatusList = workStatusList;
	}

}
