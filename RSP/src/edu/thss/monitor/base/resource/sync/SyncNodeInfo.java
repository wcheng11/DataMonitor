package edu.thss.monitor.base.resource.sync;
/**
 * 同步节点信息
 * @author yangtao
 */
public class SyncNodeInfo {

	public SyncNodeInfo(){}
	
	public SyncNodeInfo(String ip,Integer port){
		this.ip = ip;
		this.port = port;
	}
	
	private String ip;
	
	private Integer port;
	
	private String filterMask;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getFilterMask() {
		return filterMask;
	}
	
	public void setFilterMask(String filterMask) {
		this.filterMask = filterMask;
	}
	
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String toString(){
		return this.ip+":"+this.port;
	}
	
}
