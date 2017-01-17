package edu.logRecorder.entity;

public class TimeLog {

	String id;
	
	String process;
	
	Long startTime;
	
	Long endTime;

	public TimeLog(String id, String process, Long startTime, Long endTime) {
		super();
		this.id = id;
		this.process = process;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
}
