package edu.thss.monitor.rsp.topology;
/**
 * 拓扑常量（JSON结构化字串相关常量）
 * @author yangtao
 */
public class TopologyConstant {

	/**
	 * 节点类型
	 */
	public static final String COMPONENTS = "components";
	
	/**
	 * 节点类型
	 */
	public static final String COMPONENT_TYPE = "type";
	public static final String COMPONENT_TYPE_SPOUT = "spout";
	public static final String COMPONENT_TYPE_BOLT = "bolt";

	/**
	 * 节点名
	 */
	public static final String COMPONENT_NAME = "name";
	
	/**
	 * 节点类名
	 */
	public static final String COMPONENT_CLASSNAME = "className";

	/**
	 * 节点并行度（线程数）
	 */
	public static final String COMPONENT_PARALLEL = "parallel";
	
	/**
	 * 节点线程数（task数）
	 */
	public static final String COMPONENT_TASKNUM = "taskNum";

	/**
	 * follow
	 */
	public static final String COMPONENT_FOLLOW = "follow";
	
	/**
	 * 分组类型
	 */
	public static final String COMPONENT_FOLLOW_GROUPTYPE = "groupType";

	/**
	 * 目标节点
	 */
	public static final String COMPONENT_FOLLOW_TARGET = "target";
	
	/**
	 * follow的流ID
	 */
	public static final String COMPONENT_FOLLOW_STREAMID = "streamId";
	
	/**
	 * fields分组的field名
	 */
	public static final String COMPONENT_FOLLOW_FIELD = "field";
	
	/**
	 * 节点参数
	 */
	public static final String COMPONENT_PARAM = "param";
	
	/**
	 * Component最大挂起数（ 挂起数 = 无回应tuple数 - 失败的tuple数 ）
	 */
	public static final String COMPONENT_MAX_PENDING = "maxPending";
	
}
