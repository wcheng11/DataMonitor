package edu.thss.monitor.rsp.service.store.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;
import edu.thss.monitor.rsp.service.store.IDataStore;
import edu.thss.monitor.rsp.service.store.imp.DataStore;
import edu.thss.monitor.rsp.service.store.imp.SanyDataDAO;

public class StoreTest {

//	@Test
//	public void test() {
//		String string = "aaaa";
//		List<String> list = new ArrayList<String>();
//		list.add(string);
//		string = "bbbb";
//		list.add(string);
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			String string1 = (String) iterator.next();
//			System.out.println(string1);
//		}
//		
//		Long long1 = new Long(123);
//		Long long2 = new Long(123);
//		long1 = long1*2 + long2;
//		String string2 = long1.toString();
//		System.out.println(string2);
//		
//	}
	
	@Test
	public void test(){
		
		
		
		String useKeyspace = "use laudTest;";
		String creatColumnFamily = "create columnfamily sany (id varchar primary key,name varchar);";
		String dropColumnFamily = "drop columnfamily sany;";
		
		String[] configLocations = new String[] {"META-INF/beans-base.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
//		LaUDBaseDAO laudDAO = (LaUDBaseDAO)ctx.getBean("laUDBaseDAO");
//		
//		ICompanyDataDAO dataDAO = (SanyDataDAO)ctx.getBean("sanyDataDAO");
//		dataDAO.setColumnfamily("sany");
//		IDataStore store = new DataStore();
//		store.setCompanyDataDAO(dataDAO);
		
		IDataStore store = (DataStore)ctx.getBean("dataStore");
		
		
//		try {
//			laudDAO.execute(useKeyspace);
//			//laudDAO.execute(dropColumnFamily);
//			//laudDAO.execute(creatColumnFamily);
//		} catch (RSPException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		
		ParsedDataPacket packet = new ParsedDataPacket();
		
		Device device = new Device();
		device.setDeviceID("11111");
		packet.setDevice(device);
		
		Map<String, String> baseInfoList = new HashMap<String, String>();
		baseInfoList.put("serialNo", "22222");
		packet.setBaseInfoMap(baseInfoList);
		
		Date timestamp = new Date();
		packet.setTimestamp(timestamp.getTime());
		
		Map<String, String> workStatusList = new HashMap<String, String>();
		workStatusList.put("33333", "33");
		workStatusList.put("44444", "44");
		packet.setWorkStatusMap(workStatusList);
		
		try {
			store.saveData(packet);
		} catch (RSPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet rSet = null;
		
		//查询数据
		
		ConnectPool pool = (ConnectPool)ctx.getBean("connectPool");
		Connection con = null;
		Statement statement = null;
 
		try {
			con = pool.getCon();
			statement = con.createStatement();
			rSet = statement.executeQuery("select * from sany");
			statement.close();
			pool.release(con);
			System.out.println(rSet.getMetaData().getColumnCount());
			while(rSet.next()){
				StringBuffer sb = new StringBuffer();
				for(int i=1;i<=rSet.getMetaData().getColumnCount();i++){
					sb.append(rSet.getMetaData().getColumnName(i)).append(":").append(rSet.getObject(i)).append(";");
				}
				System.out.println(sb.toString());
			}
			
			con = pool.getCon();
			statement = con.createStatement();
			rSet = statement.executeQuery("select * from sany");
			statement.close();
			pool.release(con);
			System.out.println(rSet.getMetaData().getColumnCount());
			while(rSet.next()){
				StringBuffer sb = new StringBuffer();
				for(int i=1;i<=rSet.getMetaData().getColumnCount();i++){
					sb.append(rSet.getMetaData().getColumnName(i)).append(":").append(rSet.getObject(i)).append(";");
				}
				System.out.println(sb.toString());
			}
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}

	}
	

}
