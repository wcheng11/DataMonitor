package edu.thss.monitor.base.resource.bean;

import java.util.Map;

/**
 * 资源载体
 * 说明：用于中央资源容器与地方资源容器的资源传输，Redis资源传输资源信息(resourceInfo)，内存资源传输资源Map
 * @author yangtao
 */
public class ResourceCarrier {

	private Integer type;
	
	public static final Integer TYPE_MEMORY = 1;

	public static final Integer TYPE_REDIS = 2;
	
	private ResourceInfo resourceInfo;
	
	private Map<Object,Object> map;
	
	public ResourceCarrier(){}
	
	public ResourceCarrier(ResourceInfo resourceInfo){
		this.type = TYPE_REDIS;
		this.resourceInfo = resourceInfo;
	}
	
	public ResourceCarrier(Map<Object,Object> map){
		this.type = TYPE_MEMORY;
		this.map = map;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ResourceInfo getResourceInfo() {
		return resourceInfo;
	}

	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}

	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}
	
	
}
