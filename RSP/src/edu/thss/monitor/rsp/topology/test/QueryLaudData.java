package edu.thss.monitor.rsp.topology.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO;
import edu.thss.monitor.pub.exception.RSPException;

public class QueryLaudData {

//	@Test
//	public void testOracleQuery(){
//		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_RSP");
//		EntityManager entityManager = emf.createEntityManager();
//		String sqlString = "select * from plt_tsm_r_tem2tempara t";
//		Query query = entityManager.createNativeQuery(sqlString);
//		List result = query.getResultList();
//		
//	}
	
	/**
	 * 删除数据
	 */
	public void deleteData(){
		String useKeyspace = "use laudTest;";
		String creatColumnFamily = "create columnfamily sany (id varchar primary key);";
		String dropColumnFamily = "drop columnfamily sany;";
		String[] configLocations = new String[] {"META-INF/beans-base.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
		LaUDBaseDAO laudDAO = (LaUDBaseDAO)ctx.getBean("laUDBaseDAO");
		try {
			laudDAO.execute(useKeyspace);
			laudDAO.execute(dropColumnFamily);
			laudDAO.execute(creatColumnFamily);
		} catch (RSPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询数据
	 */
	public void testQuery() {
		
		String useKeyspace = "use laudTest;";
		
		ResultSet rSet = null;
		String[] configLocations = new String[] {"META-INF/beans-base.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
		
		ConnectPool pool = (ConnectPool)ctx.getBean("connectPool");
		Connection con = null;
		Statement statement = null;
 
		try {
			con = pool.getCon();
			statement = con.createStatement();
			statement.execute(useKeyspace);
//			rSet = statement.executeQuery("select * from sany where id = bc0001bc130");
			rSet = statement.executeQuery("select * from sany");
			statement.close();
			pool.release(con);
			System.out.println("总共有数据：" + rSet.getMetaData().getColumnCount() + "条");
			while(rSet.next()){
				StringBuffer sb = new StringBuffer();
				for(int i=1;i<=rSet.getMetaData().getColumnCount();i++){
					sb.append(rSet.getMetaData().getColumnName(i)).append(":").append(rSet.getObject(i)).append(";");
//					System.out.println(sb.toString());
				}
				System.out.println(sb.toString());
				System.out.println("总共有数据：" + rSet.getMetaData().getColumnCount() + "条");
			}
			
			
//			con = pool.getCon();
//			statement = con.createStatement();
//			rSet = statement.executeQuery("select * from sany where id = " + "1111144444");
//			statement.close();
//			pool.release(con);
//			System.out.println(rSet.getMetaData().getColumnCount());
//			while(rSet.next()){
//				StringBuffer sb = new StringBuffer();
//				for(int i=1;i<=rSet.getMetaData().getColumnCount();i++){
//					sb.append(rSet.getMetaData().getColumnName(i)).append(":").append(rSet.getObject(i)).append(";");
//				}
//				System.out.println(sb.toString());
//			}			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}

		
	}
	
	@Test
	public void testMain(){
//		this.deleteData();
		this.testQuery();
	}

}
