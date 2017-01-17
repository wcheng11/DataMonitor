package edu.thss.monitor.base.resource.bean;
/**
 * 资源更改信息
 * @author yangtao
 */
public class ChangeInfo {
	
	public static final int CHANGETYPE_ADD = 1;

	public static final int CHANGETYPE_DELETE = 2;

	public static final int CHANGETYPE_UPDATE = 3;

	/**
	 * 更改类型：1-添加;2-删除;3-更新
	 */
	private Integer changeType;
	
	/**
	 * 更改资源类型
	 */
	private String resourceType;
	
	/**
	 * 更改资源key
	 */
	private String resourceKey;
	
	/**
	 * 更改资源对象
	 */
	private Object resourceItem;
	
	/**
	 * 更改发起者（记录发起更改的同步客户端信息）
	 */
	private String changeSponsor;

	public String getChangeSponsor() {
		return changeSponsor;
	}

	public void setChangeSponsor(String changeSponsor) {
		this.changeSponsor = changeSponsor;
	}

	public ChangeInfo(){};
	
	public ChangeInfo(Integer type,String resourceType,String resourceKey,Object resourceItem){
		this.changeType = type;
		this.resourceType = resourceType;
		this.resourceKey = resourceKey;
		this.resourceItem = resourceItem;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public Object getResourceItem() {
		return resourceItem;
	}

	public void setResourceItem(Object resourceItem) {
		this.resourceItem = resourceItem;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer("更改对象(更改类型:");
		switch(changeType){
			case CHANGETYPE_ADD:
				sb.append("增加");
				break;
			case CHANGETYPE_DELETE:
				sb.append("删除");
				break;
			case CHANGETYPE_UPDATE:
				sb.append("更新");
				break;
		}
		sb.append(",资源类型:").append(resourceType)
			.append(",资源Key:").append(resourceKey)
			.append(",资源对象").append(resourceItem).append(")");
		return sb.toString();
	}
	
}
