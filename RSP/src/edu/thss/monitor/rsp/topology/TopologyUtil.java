package edu.thss.monitor.rsp.topology;

import java.lang.reflect.Constructor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.SpoutDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.exception.RSPException;
/**
 * 拓扑工具类
 * @author yangtao
 */
public class TopologyUtil {

//	private static Log logger = LogFactory.getLog(TopologyUtil.class);
	
	/**
	 * 生成拓扑构建器
	 * @param topologyStructure - 拓扑结构字符串（json格式）
	 * @return 拓扑构建器
	 * @throws RSPException 
	 */
	@SuppressWarnings("unchecked")
	public static TopologyBuilder generateTopologyBuilder(JSONArray componentArray) throws RSPException{
		TopologyBuilder builder = new TopologyBuilder();
		LogRecord.info("拓扑开始生成...");
		try {
			// 设置拓扑结构
			for (int i = 0; i < componentArray.size(); i++) {// 遍历拓扑的每个component(spout/bolt)
				JSONObject obj = componentArray.getJSONObject(i);
				//生成节点实例
				Class clz = Class.forName(obj.getString(TopologyConstant.COMPONENT_CLASSNAME));
				Object component = null;
				if(obj.get(TopologyConstant.COMPONENT_PARAM)!=null){//存在参数则调用构造器
					Constructor constructor = clz.getConstructor(String.class); //构造函数参数列表的class类型
					component = constructor.newInstance(obj.getString(TopologyConstant.COMPONENT_PARAM));
				}else{
					component = clz.newInstance();
				}
				//将节点设置到拓扑构建器中
				if (obj.getString(TopologyConstant.COMPONENT_TYPE).equals(TopologyConstant.COMPONENT_TYPE_SPOUT)) {
					SpoutDeclarer spoutDec = builder.setSpout(obj.getString("name"), (IRichSpout) component, obj.getInt(TopologyConstant.COMPONENT_PARALLEL));
					if(obj.get(TopologyConstant.COMPONENT_TASKNUM)!=null){
						spoutDec.setNumTasks(obj.getInt(TopologyConstant.COMPONENT_TASKNUM));
					}
					if(obj.get(TopologyConstant.COMPONENT_MAX_PENDING)!=null){
						spoutDec.setMaxSpoutPending(obj.getInt(TopologyConstant.COMPONENT_MAX_PENDING));
					}
				}else if(obj.getString(TopologyConstant.COMPONENT_TYPE).equals(TopologyConstant.COMPONENT_TYPE_BOLT)) {
					BoltDeclarer bd = builder.setBolt(obj.getString(TopologyConstant.COMPONENT_NAME), (IRichBolt) component, obj.getInt(TopologyConstant.COMPONENT_PARALLEL));
					if(obj.get(TopologyConstant.COMPONENT_TASKNUM)!=null){
						bd.setNumTasks(obj.getInt(TopologyConstant.COMPONENT_TASKNUM));
					}
					if(obj.get(TopologyConstant.COMPONENT_MAX_PENDING)!=null){
						bd.setMaxSpoutPending(obj.getInt(TopologyConstant.COMPONENT_MAX_PENDING));
					}
					JSONArray followArray = obj.getJSONArray(TopologyConstant.COMPONENT_FOLLOW);
					for(int j=0;j<followArray.size();j++){
						String groupType = followArray.getJSONObject(j).getString(TopologyConstant.COMPONENT_FOLLOW_GROUPTYPE);
						String target = followArray.getJSONObject(j).getString(TopologyConstant.COMPONENT_FOLLOW_TARGET);
						String streamId = followArray.getJSONObject(j).optString(TopologyConstant.COMPONENT_FOLLOW_STREAMID);
						if(groupType.equals("shuffle")){
							if(streamId==null||streamId.isEmpty())
								bd.shuffleGrouping(target);
							else
								bd.shuffleGrouping(target, streamId);
						}else if(groupType.equals("localOrShuffle")){
							if(streamId==null||streamId.isEmpty())
								bd.localOrShuffleGrouping(target);
							else
								bd.localOrShuffleGrouping(target, streamId);
						}else if(groupType.equals("all")){
							if(streamId==null||streamId.isEmpty())
								bd.allGrouping(target);
							else
								bd.allGrouping(target, streamId);
						}else if(groupType.equals("global")){
							if(streamId==null||streamId.isEmpty())
								bd.globalGrouping(target);
							else
								bd.globalGrouping(target, streamId);
						}else if(groupType.equals("fieldShuffle")){
							String fieldName = followArray.getJSONObject(j).getString(TopologyConstant.COMPONENT_FOLLOW_FIELD);
							if(streamId==null||streamId.isEmpty())
								bd.fieldsGrouping(target, new Fields(fieldName));
							else
								bd.fieldsGrouping(target, streamId, new Fields(fieldName));
						}else{
							throw new RSPException("不支持的分组类型："+groupType);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RSPException("生成拓扑构建器时发生异常,请检查拓扑结构字符串!",e);
		}
		return builder;
	}
	
}
