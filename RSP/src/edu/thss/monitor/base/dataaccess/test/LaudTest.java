package edu.thss.monitor.base.dataaccess.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO;
import edu.thss.monitor.pub.exception.RSPException;

public class LaudTest {

	@Test
	public void test() {
		/*
		String[] configLocations = new String[] {   
        "META-INF/beans-base.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
		ConnectPool pool = (ConnectPool)ctx.getBean("connectPool");
		Connection con = null;
		try {
			con = pool.getCon();
		} catch (RSPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Statement statement = null;
		try {
			statement = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		String[] configLocations = new String[] {   
        "META-INF/beans-base.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
		LaUDBaseDAO laudDAO = (LaUDBaseDAO)ctx.getBean("laUDBaseDAO");
		String creatKeyspace = "create keyspace laudTest with strategy_class ='org.apache.cassandra.locator.SimpleStrategy' " +
								"and strategy_options:replication_factor=1;";
		String dropKeyspace = "DROP KEYSPACE laudTest;"; 
		String useKeyspace = "use laudTest;";
		String creatColumnFamily = "create columnfamily user1 (id varchar primary key,name varchar,address varchar,age int);";
		String dropColumnFamily = "DROP COLUMNFAMILY user1;";
		String insertSql0 = "insert into user1(id,name,address,age) values('1','许鹏','xxx',22);";
		String insertSql1 = "insert into user1(id,name,age) values(4,'许鹏4',22)";
		String insertSql2 = "insert into user1(id,name,age) values(5,'许鹏5',22)";
		//String query = "select id,name,name1,age from user1";  
		ResultSet rSet = null;
		try {
			//laudDAO.execute(dropKeyspace);
			//laudDAO.execute(creatKeyspace);
			laudDAO.execute(useKeyspace);
			laudDAO.execute(dropColumnFamily);
			laudDAO.execute(creatColumnFamily);
			laudDAO.execute(insertSql0);
			//laudDAO.execute(insertSql1);
			//laudDAO.execute(insertSql2);
		} catch (RSPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		//查询数据
		ConnectPool pool = (ConnectPool)ctx.getBean("connectPool");
		Connection con = null;
		Statement statement = null;
 
		try {
			con = pool.getCon();
			statement = con.createStatement();
			rSet = statement.executeQuery("select id,name,address,age from user1");
			statement.close();
			pool.release(con);
			while(rSet.next()){
				System.out.println(rSet.getObject(1)+","+(rSet.getObject(2))+","+(rSet.getObject(3))+","+(rSet.getObject(4)));
			}
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
	}

}
