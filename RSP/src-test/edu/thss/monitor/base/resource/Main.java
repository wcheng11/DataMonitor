package edu.thss.monitor.base.resource;

import java.util.Random;

import ch.qos.logback.classic.Logger;
import demo.framework.jpa.entity.TestEntity;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.base.resource.sync.SyncNodeInfo;
import edu.thss.monitor.pub.exception.RSPException;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		SyncNodeInfo info = new SyncNodeInfo("166.111.82.29", 7911);
		RegionalRC.setServerInfo(info);
		RegionalRC.register();
		Random random = new Random();
		Thread.sleep(random.nextInt(5000) + 20000);
		RegionalRC.loadResource(new String[]{"testRedisObject","testMemory"});
		TestEntity entity = new TestEntity();
		entity.setId(args[0]);
		entity.setName(args[0]);
		entity.setValue(args[0]);
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_ADD, "testMemory", String.valueOf(random.nextInt(1000)), entity);
		//info.setResourceType(ResourceConstant.CONFIG_RESOURCE_TYPE_REDIS);
		RegionalRC.syncChange(changeInfo);
		System.out.println("同步信息已发送：" + entity.toString());
		Thread.sleep(Long.MAX_VALUE);
	}

}
