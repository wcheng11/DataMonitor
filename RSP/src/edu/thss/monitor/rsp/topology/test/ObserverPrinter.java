package edu.thss.monitor.rsp.topology.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;

public class ObserverPrinter {

	private static Log logger = LogFactory.getLog("ObserverPrinter");
	
	private static int flushSeconds = 5;
	
	/**
	 * 上次计数Map
	 */
	private static Map<String,Integer> lastTotalNumMap = new HashMap<String,Integer>();
	
	private static Map<String,Integer> lastNumMap = new HashMap<String,Integer>();

	//获得某个component处理实时通量
	private static int getNum2(String compName){
		Integer lastTotalNum = lastTotalNumMap.get(compName);
		int nowTotalNum = getTotalNum(compName);
		int num = 0;
		if(lastTotalNum!=null){
			num =  (nowTotalNum-lastTotalNum)/flushSeconds;
		}
		lastTotalNumMap.put(compName, nowTotalNum);
		Integer lastNum = lastNumMap.get(compName);
		int tmp = num;
		if(lastNum!=null&&lastNum>0&&num!=0){
			num = (int)(lastNum*0.5+num*0.5);
		}
		lastNumMap.put(compName, num);
//		System.out.println(lastNum+"====="+tmp+"===="+num);
		return num;
	}
	
	//获得某个component处理实时通量
//	private static int getNum(String compName){
//		int num = 0;
//		int flagNum = 0;
//		for(int i=0;i<MAX_TASK_NUM;i++){
//			if(dataMap.get(compName+"_"+i)!=null){
//				num += Integer.parseInt(dataMap.get(compName+"_"+i));
////				break;
//			}else{
//				if(flagNum++>10)
//					break;
//			}
//		}
//		return num;
//	}
	
	//获得某个component已处理总数量
	private static String getNumByArray(String[] array){
		StringBuffer sb = new StringBuffer();
		for(String node:array)
			sb.append(node).append("=").append(dataMap.get(node)).append(",");
		return sb.toString();
	}
	
	//获得某个component已处理总数量
	private static int getTotalNum(String compName){
		int totalNum = 0;
		int flagNum = 0;
		for(int i=0;i<MAX_TASK_NUM;i++){
			if(dataMap.get(compName+"_"+i+"_t")!=null){
				totalNum += Integer.parseInt(dataMap.get(compName+"_"+i+"_t"));
			}else{
				if(flagNum++>10)
					break;
			}
		}
		return totalNum;
	}
	
	/**
	 * 获得component节点的实时处理数量（各task实时处理数量之和）与总处理数量（各task总处理数量之和）
	 */
	private static int MAX_TASK_NUM = 200;
	private static String getInfoByCompName(String compName){
		StringBuffer sb = new StringBuffer();
		sb.append(compName).append("=").append(getNum2(compName))
			.append(", ").append(compName).append("_all=")
			.append(getTotalNum(compName)).append(", ");
		return sb.toString();
	}
//	private static String getInfoByCompName(String compName){
//		StringBuffer sb = new StringBuffer();
//		sb.append(compName).append("=").append(getNum(compName))
//			.append(", ");
//		return sb.toString();
//	}
	
	private static Map<String, String> dataMap = null;
	
	private static String[] boltNameArray = {"1_pRecogBolt","2_parseBolt","3_transBolt",
		"4_dRecogBolt","5_1_subscribeJudgeBolt","5_2_dataStoreBolt","5_3_ipBolt","5_1_1_pushBolt"};
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//常量
		int tableIndex = 14;
		String keyName = "compInfo";
		//建立连接
		Jedis jedis = new Jedis("192.168.10.35",6379);
		//选择数据库
		jedis.select(tableIndex);
		jedis.del(keyName);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hhmmss");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String filename = "E://testlog/" + df.format(d) + ".csv";
		File f = new File(filename);
		FileWriter fw = new FileWriter(f);	
		
		fw.append("time,Spout speed,store speed,p_recog queue,parse queue,trans queue,d_recog queue,store queue,subscribe queue,push queue");
		fw.append("\r\n");
		fw.flush();
		
		
		while(true){
			//读取Map类型数据
			dataMap = jedis.hgetAll(keyName);
//			System.out.print("onlineSpout1="+getNum(dataMap,"onlineSpout1",50)+",onlineSpout1_all="+getTotalNum(dataMap,"onlineSpout1",50)+",");
//			System.out.print("onlineSpout2="+getNum(dataMap,"onlineSpout2",50)+",onlineSpout2_all="+getTotalNum(dataMap,"onlineSpout2",50)+",");
//			System.out.print("onlineSpout3="+getNum(dataMap,"onlineSpout3",50)+",onlineSpout3_all="+getTotalNum(dataMap,"onlineSpout3",50)+",");
			String[] observeNodes = {"time","receiveQueueSize1_0"};
			StringBuffer sb = new StringBuffer();
			sb
//				.append(getNumByArray(observeNodes))
				.append(getInfoByCompName("onlineSpout1"))
				//Bolt: 1_pRecogBolt
				.append("[ ").append(getInfoByCompName(boltNameArray[0]))
				.append(boltNameArray[0]).append("_QueueSize=")
				.append((getTotalNum("onlineSpout1")+getTotalNum("onlineSpout2")+getTotalNum("onlineSpout3")-getTotalNum(boltNameArray[0])))
				.append(" ]  ")
//				//Bolt: 2_parseBolt
//				.append("[ ").append(getInfoByCompName(boltNameArray[1]))
//				.append(boltNameArray[1]).append("_QueueSize=")
//				.append(getTotalNum(boltNameArray[0])-getTotalNum(boltNameArray[1]))
//				.append(" ]  ")
//				//Bolt: 3_transBolt
//				.append("[ ").append(getInfoByCompName(boltNameArray[2]))
//				.append(boltNameArray[2]).append("_QueueSize=")
//				.append(getTotalNum(boltNameArray[1])-getTotalNum(boltNameArray[2]))
//				.append(" ]  ")
				//Bolt: 4_dRecogBolt
				.append("[ ").append(getInfoByCompName(boltNameArray[3]))
				.append(boltNameArray[3]).append("_QueueSize=")
				.append(getTotalNum(boltNameArray[2])-getTotalNum(boltNameArray[3]))
				.append(" ]  ")
//				//Bolt: 5.1_subscribeJudgeBolt
//				.append("[ ").append(getInfoByCompName(boltNameArray[4]))
//				.append(boltNameArray[4]).append("_QueueSize=")
//				.append(getTotalNum(boltNameArray[3])-getTotalNum(boltNameArray[4]))
//				.append(" ]  ")
//				//Bolt: 5.1.1_pushBolt
//				.append("[ ").append(getInfoByCompName(boltNameArray[7]))
//				.append(boltNameArray[7]).append("_QueueSize=")
//				.append(getTotalNum(boltNameArray[4])-getTotalNum(boltNameArray[7]))
//				.append(" ]  ")
				//Bolt: 5_2_dataStoreBolt
				.append("[ ").append(getInfoByCompName(boltNameArray[5]))
				.append(boltNameArray[5]).append("_QueueSize=")
				.append(getTotalNum(boltNameArray[3])-getTotalNum(boltNameArray[5]))  //这里应该是3-5
				.append(" ]  ")
				//Bolt: 5_3_ipBolt
//				.append("[ ").append(getInfoByCompName(boltNameArray[6]))
//				.append(boltNameArray[6]).append("_QueueSize=")
//				.append(getTotalNum(boltNameArray[3])-getTotalNum(boltNameArray[6]))
//				.append(" ]  ")
//				//End
				.append("");
			logger.info(sb.toString());
			
//			private static String[] boltNameArray = {"1_pRecogBolt","2_parseBolt","3_transBolt",
//				"4_dRecogBolt","5_1_subscribeJudgeBolt","5_2_dataStoreBolt","5_3_ipBolt","5_1_1_pushBolt"};
			StringBuffer sb2 = new StringBuffer();
			System.out.println(df2.format(new Date()));
			sb2
				.append(df2.format(new Date()))     //时间
				.append(",").append(getNum2("onlineSpout1"))   //实时接收包的速度
				.append(",").append(getNum2(boltNameArray[5]))   //实时解析工况速度
				.append(",").append(getTotalNum("onlineSpout1")+getTotalNum("onlineSpout2")+getTotalNum("onlineSpout3")-getTotalNum(boltNameArray[0]))  //协议识别队列长度
				.append(",").append(getTotalNum(boltNameArray[0])-getTotalNum(boltNameArray[1]))   //解析队列长度
				.append(",").append(getTotalNum(boltNameArray[1])-getTotalNum(boltNameArray[2]))   //数据转换队列长度
				.append(",").append(getTotalNum(boltNameArray[2])-getTotalNum(boltNameArray[3]))   //设别识别队列长度
				.append(",").append(getTotalNum(boltNameArray[3])-getTotalNum(boltNameArray[5]))   //数据存储队列长度
				.append(",").append(getTotalNum(boltNameArray[3])-getTotalNum(boltNameArray[4]))   //订阅判断队列长度
				.append(",").append(getTotalNum(boltNameArray[4])-getTotalNum(boltNameArray[7]))   //数据推送队列长度
			;
//			System.out.println(sb2.toString());
			fw.append(sb2.toString());
			fw.append("\r\n");
			fw.flush();
			try {
				Thread.sleep(flushSeconds*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
