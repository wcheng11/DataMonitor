package edu.thss.monitor.rsp.topology;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
//import edu.thss.monitor.rsp.service.receive.util.FtpConfig;
import edu.thss.monitor.rsp.service.store.imp.SanyDataDAO;
import edu.thss.monitor.rsp.topology.bolt.DRecogBolt;
import edu.thss.monitor.rsp.topology.bolt.PRecogBolt;
import edu.thss.monitor.rsp.topology.bolt.ParseBolt;
import edu.thss.monitor.rsp.topology.bolt.PushBolt;
import edu.thss.monitor.rsp.topology.bolt.SubscribeJudgeBolt;
import edu.thss.monitor.rsp.topology.bolt.TransBolt;
import edu.thss.monitor.rsp.topology.spout.OnlineSpout;

/**
 * 监测平台拓扑类
 */
public class MonitorTopology {

	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		//解析拓扑结构字符串(JSON格式)
		TopologyBuilder builder = null;
		int workerNum;
		if(args!=null && args.length > 1) {//动态配置
			String topologyStructure = null;
//			topologyStructure = "{components:[" 
//				 +"{type:'spout',name:'0_onlineSpout1',className:'edu.thss.monitor.rsp.topology.spout.OnlineSpout',parallel:'1',param:'5050'},"
//				 +"{type:'spout',name:'0_onlineSpout2',className:'edu.thss.monitor.rsp.topology.spout.OnlineSpout',parallel:'1',param:'5051'},"
//				 +"{type:'spout',name:'0_onlineSpout3',className:'edu.thss.monitor.rsp.topology.spout.OnlineSpout',parallel:'1',param:'5052'},"
//				 +"{type:'spout',name:'0_onlineSpout4',className:'edu.thss.monitor.rsp.topology.spout.OnlineSpout',parallel:'1',param:'5053'},"
//				 +"{type:'bolt',name:'1_pRecogBolt',className:'edu.thss.monitor.rsp.topology.bolt.PRecogBolt',parallel:'2',follow:[{target:'0_onlineSpout1',groupType:'shuffle'},{target:'0_onlineSpout2',groupType:'shuffle'},{target:'0_onlineSpout3',groupType:'shuffle'},{target:'0_onlineSpout4',groupType:'shuffle'}]},"
//				 +"{type:'bolt',name:'2_parseBolt',className:'edu.thss.monitor.rsp.topology.bolt.ParseBolt',parallel:'5',follow:[{target:'1_pRecogBolt',groupType:'shuffle'}]},"
//				 +"{type:'bolt',name:'3_transBolt',className:'edu.thss.monitor.rsp.topology.bolt.TransBolt',parallel:'2',follow:[{target:'2_parseBolt',groupType:'shuffle'}]},"
//				 +"{type:'bolt',name:'4_dRecogBolt',className:'edu.thss.monitor.rsp.topology.bolt.DRecogBolt',parallel:'2',follow:[{target:'3_transBolt',groupType:'shuffle'}]},"
//				 +"{type:'bolt',name:'5.1_subscribeJudgeBolt',className:'edu.thss.monitor.rsp.topology.bolt.SubscribeJudgeBolt',parallel:'2',follow:[{target:'4_dRecogBolt',groupType:'shuffle'}]},"
//				 +"{type:'bolt',name:'5.1.1_pushBolt',className:'edu.thss.monitor.rsp.topology.bolt.PushBolt',parallel:'2',follow:[{target:'5.1_subscribeJudgeBolt',groupType:'shuffle'}]},"
//				 +"{type:'bolt',name:'5.2_dataStoreBolt',className:'edu.thss.monitor.rsp.topology.bolt.DataStoreBolt',parallel:'2',follow:[{target:'4_dRecogBolt',groupType:'shuffle'}]," +
//				 		"param:'{daoClassName:\"edu.thss.monitor.rsp.service.store.imp.SanyDataDAO\",keySpace:\"workStatusData\",columnFamily:\"sany\"}'},"
//				 +"{type:'bolt',name:'5.3_ipBolt',className:'edu.thss.monitor.rsp.topology.bolt.IPBolt',parallel:'2',follow:[{target:'4_dRecogBolt',groupType:'shuffle'}]}"
//				 +"],workerNum:'2'"
//			  +"}";
			topologyStructure = args[1];
			System.out.println("拓扑结构字符串为："+topologyStructure);
			JSONObject tsObj = JSONObject.fromObject(topologyStructure);
				//拓扑节点配置数组
			JSONArray componentArray = tsObj.getJSONArray(TopologyConstant.COMPONENTS);
			//利用JSON字串生成拓扑构建器


			builder = TopologyUtil.generateTopologyBuilder(componentArray);
			workerNum = tsObj.getInt("workerNum");//拓扑工作进程数
		}else{//本地配置
			//直接生成拓扑构建器
			workerNum = 6;
			builder = new TopologyBuilder();
			
			//设置在线接收Spout
			OnlineSpout onlineSpout1 = new OnlineSpout("5054");
//			OnlineSpout onlineSpout2 = new OnlineSpout("8832");
//			OnlineSpout onlineSpout3 = new OnlineSpout("8833");
//			OnlineSpout onlineSpout4 = new OnlineSpout("8834");
			builder.setSpout("0_onlineSpout1", onlineSpout1, 1);
//			builder.setSpout("0_onlineSpout2", onlineSpout2, 1);
//			builder.setSpout("0_onlineSpout3", onlineSpout3, 1);
//			builder.setSpout("0_onlineSpout4", onlineSpout4, 1);
			
			//设置离线接收Spout
//			FtpConfig ftpConfig = new FtpConfig("166.111.80.208",21,"monitor","mro2011");
//	        builder.setSpout("offlineSpout", new OfflineSpout(ftpConfig), 1);
			
			//设置协议识别Bolt
	        builder.setBolt("1_pRecogBolt", new PRecogBolt(), 2)
	                .shuffleGrouping("0_onlineSpout1");
	        
	        //设置解析Bolt
	        builder.setBolt("2_parseBolt", new ParseBolt(), 10)
	        		.shuffleGrouping("1_pRecogBolt");
	        
	        //设置数据转换Bolt
	        builder.setBolt("3_transBolt", new TransBolt(), 2)
	        		.shuffleGrouping("2_parseBolt");
	        
	        //设置设备识别Bolt
	        builder.setBolt("4_dRecogBolt", new DRecogBolt(), 2)
	        		.shuffleGrouping("3_transBolt");
	        
	        //设置订阅Bolt
	        builder.setBolt("5.1_subscribeJudgeBolt", new SubscribeJudgeBolt(), 2)
	        		.shuffleGrouping("4_dRecogBolt");
	        
	        //设置数据推送Bolt
	        builder.setBolt("5.1.1_pushBolt", new PushBolt(), 2)
					.shuffleGrouping("5.1_subscribeJudgeBolt");
	        
//	        //设置数据存储Bolt
//	        builder.setBolt("5.2_dataStoreBolt", new DataStoreBolt("{daoClassName:\"edu.thss.monitor.rsp.service.store.imp.SanyDataDAO\",keySpace:\"laudTest\",columnFamily:\"sany\"}"), 2)
//					.shuffleGrouping("4_dRecogBolt");
	        
//	        //设置IP地址维护Bolt
//	        builder.setBolt("5.3_ipBolt", new IPBolt(), 2)
//	        		.shuffleGrouping("4_dRecogBolt");
	        
//	        //设置同步Bolt
//	        builder.setBolt("5.4_syncBolt", new SyncBolt(), 2)
//	        		.shuffleGrouping("4_dRecogBolt");
	        
		}
		
        //配置拓扑，自定义类型注册序列化
        Config conf = new Config();
        conf.registerSerialization(RawDataPacket.class);
        conf.registerSerialization(Protocol.class);
        conf.registerSerialization(ParsedDataPacket.class);
        conf.registerSerialization(JudgeResult.class);
//        conf.registerSerialization(FtpConfig.class);
        conf.registerSerialization(SanyDataDAO.class);
        conf.setDebug(true);
		
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(workerNum);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("serili", conf, builder.createTopology());
            Utils.sleep(1000*60*600);//运行600分钟后停止
            cluster.killTopology("serili");
            cluster.shutdown();
        }
	}

}
