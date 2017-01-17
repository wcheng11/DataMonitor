package demo.framework.storm_spring.test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import demo.framework.storm.spout.CustomObject;
import demo.framework.storm_spring.bolt.SpringIntegrateBolt;
import demo.framework.storm_spring.spout.RandomNameSpout;

/**
 * 序列化简单示例：tuple传送自定义类的对象
 */
public class SpringIntegrateTest {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		
		TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randomNameSpout", new RandomNameSpout(), 5);        
        builder.setBolt("springIntegrateBolt", new SpringIntegrateBolt(), 3)
                .shuffleGrouping("randomNameSpout");
        Config conf = new Config();
        conf.registerSerialization(CustomObject.class);//自定义类型注册序列化
        conf.setDebug(true);
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("storm_spring_test", conf, builder.createTopology());
            Utils.sleep(20000);//运行20s后停止
            cluster.killTopology("storm_spring_test");
            cluster.shutdown();    
        }
	}

}
