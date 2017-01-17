package demo.framework.storm.test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import demo.framework.storm.bolt.CheckCustomTypeBolt;
import demo.framework.storm.spout.CustomObject;
import demo.framework.storm.spout.CustomObjectSpout;

/**
 * 序列化简单示例：tuple传送自定义类的对象
 */
public class SeriliazationTest {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		
		TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("customTypeSpout", new CustomObjectSpout(), 10);        
        builder.setBolt("checkCustomType", new CheckCustomTypeBolt(), 3)
                .shuffleGrouping("customTypeSpout");
        Config conf = new Config();
        conf.registerSerialization(CustomObject.class);//自定义类型注册序列化
        conf.setDebug(true);
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("serili", conf, builder.createTopology());
            Utils.sleep(20000);//运行20s后停止
            cluster.killTopology("serili");
            cluster.shutdown();    
        }
        
	}

}
