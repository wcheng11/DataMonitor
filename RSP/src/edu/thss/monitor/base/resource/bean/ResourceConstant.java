package edu.thss.monitor.base.resource.bean;
/**
 * 资源管理构件的常量
 * @author yangtao
 */
public class ResourceConstant {

	/**
	 * 资源配置文件中的资源结点名
	 */
	public final static String CONFIG_RESOURCE_NODENAME = "resource";

	/**
	 * 资源配置文件中资源单次加载数（数据库查询结果数）
	 */
	public final static String CONFIG_RESOURCECONFIG_ONCELOADNUM = "onceLoadNum";
	
	/**
	 * 资源配置文件中的资源结点属性名:资源类型
	 */
	public final static String CONFIG_RESOURCE_TYPE = "type";
	
	public final static String CONFIG_RESOURCE_TYPE_MEMORY = "memory";//内存资源类型
	public final static String CONFIG_RESOURCE_TYPE_REDIS = "redis";//Redis资源类型
	
	/**
	 * 资源配置文件中的资源结点属性名:数据类型(redis中存储的类型)
	 */
	public final static String CONFIG_RESOURCE_DATATYPE = "dataType";
	
	public static final String CONFIG_RESOURCE_DATATYPE_STRING = "string";//String数据类型
	public static final String CONFIG_RESOURCE_DATATYPE_MAP = "map";//Map<String,String>数据类型
	public static final String CONFIG_RESOURCE_DATATYPE_SET = "set";//Set<String>数据类型
	public static final String CONFIG_RESOURCE_DATATYPE_OBJECT = "object";//对象序列化字符串数据类型
	
	/**
	 * 资源配置文件中的资源结点属性名:表索引(只用于redis)
	 */
	public final static String CONFIG_RESOURCE_TABLEINDEX = "tableIndex";

	/**
	 * 资源配置文件中的资源结点属性名:存储位置
	 */
	public final static String CONFIG_RESOURCE_STORE = "storeLocation";
	
	/**
	 * 资源配置文件中的资源结点子结点名:查询结果类
	 */
	public final static String CONFIG_RESOURCE_CLASS = "class";
	
	/**
	 * 资源配置文件中的资源结点子结点名:JPQL查询语句
	 */
	public final static String CONFIG_RESOURCE_JPQL = "jpql";

	/**
	 * 资源配置文件中的资源结点子结点名:JPQL查询语句
	 */
	public final static String CONFIG_RESOURCE_SQL = "sql";
	
	/**
	 * 资源配置文件中的资源结点子结点名:资源项key对应属性
	 */
	public final static String CONFIG_RESOURCE_KEY = "keyAttr";
	
	/**
	 * 资源配置文件中的资源结点子结点名:资源项value对应属性（只适用于redis）
	 */
	public final static String CONFIG_RESOURCE_VALUE = "valueAttr";
	
	/**
	 * 资源配置文件中的资源结点子结点名:后处理类名
	 */
	public final static String CONFIG_RESOURCE_REPORCESS = "reprocess";
	
	/**
	 * 资源更改操作名称定义
	 */
	public final static String SERVLET_CHANGETYPE_ADD = "add";
	public final static String SERVLET_CHANGETYPE_DELETE = "delete";
	public final static String SERVLET_CHANGETYPE_UPDATE = "update";
	
}
