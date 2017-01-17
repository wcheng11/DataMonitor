package edu.thss.monitor.base.resource.bean;


/**
 * 资源信息
 * @author yangtao
 */
@SuppressWarnings("unchecked")
public class ResourceInfo {

	/**
	 * 资源类型
	 */
	private String resourceType;
	
	/**
	 * 表索引（只适用于redis）
	 */
	private Integer tableIndex;

	/**
	 * 数据类型（只适用于redis）
	 */
	private String dataType;

	/**
	 * 存储位置
	 */
	private String storageLocation;
	
	/**
	 * 单次加载条数
	 */
	private Integer onceLoadNum;

	/**
	 * 查询结果类名
	 */
	private String classStr;
	
	/**
	 * 查询结果类名
	 */
	private Class classObj;
	
	/**
	 * 后处理类名
	 */
	private String reprocessClass;
	
	public String getReprocessClass() {
		return reprocessClass;
	}

	public void setReprocessClass(String reprocessClass) {
		this.reprocessClass = reprocessClass;
	}

	public Class getClassObj() {
		if(classObj==null){
			try {
				classObj = Class.forName(classStr);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classObj;
	}

	public void setClassObj(Class classObj) {
		this.classObj = classObj;
	}

	/**
	 * JPQL查询语句
	 */
	private String jpql;

	/**
	 * SQL查询语句
	 */
	private String sql;
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 资源项key对应属性
	 */
	private String keyAttr;
	
	/**
	 * 资源项value对应属性（只适用于redis）
	 */
	private String valueAttr;
	
	public ResourceInfo(){}
	
	public ResourceInfo(String resourceType, String storageLocation,
			String classStr, String jpql, String keyAttr,
			String valueAttr) {
		this.resourceType = resourceType;
		this.storageLocation = storageLocation;
		this.classStr = classStr;
		this.jpql = jpql;
		this.keyAttr = keyAttr;
		this.valueAttr = valueAttr;
	}

	public Integer getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(Integer tableIndex) {
		this.tableIndex = tableIndex;
	}
	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getClassStr() {
		return classStr;
	}

	public void setClassStr(String classStr) {
		this.classStr = classStr;
	}

	public String getJpql() {
		return jpql;
	}

	public void setJpql(String jpql) {
		this.jpql = jpql;
	}

	public String getKeyAttr() {
		return keyAttr;
	}

	public void setKeyAttr(String keyAttr) {
		this.keyAttr = keyAttr;
	}

	public String getValueAttr() {
		return valueAttr;
	}

	public void setValueAttr(String valueAttr) {
		this.valueAttr = valueAttr;
	}

	public Integer getOnceLoadNum() {
		return onceLoadNum;
	}

	public void setOnceLoadNum(Integer onceLoadNum) {
		this.onceLoadNum = onceLoadNum;
	}

}
