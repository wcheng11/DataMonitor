package edu.thss.monitor.rsp.topology.observe;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.thss.monitor.base.timerecord.TimeLogger;
import edu.thss.monitor.rsp.topology.ComponentId;

public abstract class TimeRecordBolt extends ObservableBolt{
	
	Date start;
	
	Date end;
	
	String id;

	//开始计时
	protected String registerTime(String times, String process){
		start = new Date();
		times = times + process + ":" + start.getTime() + "-";
		return times;
	}
	
	//结束计时
	protected void end(){
		end = new Date();
	}
	//记录每个过程的开始和结束时间
	protected void recordTime(String id, String process){
		end();
		if(!id.equals("0")){
			this.id = id;
		}
		TimeLogger.recordTime(id, process, start.getTime(), end.getTime());
	}
	
	//记录所有需要的时间点
	protected void flushAllTimes(String times){
		times = times + ComponentId.END + ":" + end.getTime() + "-";
		TimeLogger.recordTimes(id, times);
	}
	
	//增加时间点
	protected String addTime(String times, String process){
		times = times + process + ":" + new Date().getTime() + "-";
		return times;
	}
}
