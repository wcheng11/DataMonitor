package edu.thss.monitor.base.dataaccess.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import edu.thss.monitor.base.dataaccess.imp.BaseDAO;
import edu.thss.monitor.base.dataaccess.imp.ConnectPool;
import edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO;
import edu.thss.monitor.pub.entity.AppLog;
import edu.thss.monitor.pub.exception.RSPException;

@SuppressWarnings("unused")
public class JPATest {   
//    private static EntityManagerFactory entityManagerFactory;   
    private static ApplicationContext ctx;   
    
    @Before  
    public void beforeClass() {   
        String[] configLocations = new String[] {   
                "META-INF/beans-base.xml"};   
        ctx = new ClassPathXmlApplicationContext(configLocations);   
//        entityManagerFactory = ctx.getBean(EntityManagerFactory.class);   
}   
    
    @Test  
    public void testJpaTemplate() {   
//    final JpaTemplate jpaTemplate = new JpaTemplate(entityManagerFactory);   
//      final UserModel2 model = new UserModel2();   
//      model.setMyName("test1");  
    AppLog appLog = new AppLog();
//	testEntity.setId("li");
	appLog.setOperateDetail("tsinghua1");
	appLog.setOperateResult("lihubin1");

	BaseDAO baseDAO = (BaseDAO)ctx.getBean("baseDAO");
	baseDAO.save(appLog);
	
	System.out.println(appLog.getClass());
	System.out.println(appLog.getOid());
	
	appLog.setOperateDetail("tsinghua111");
	baseDAO.update(appLog);
	
	
	
	System.out.println(appLog.getClass());
	System.out.println(appLog.getOid());
	
	appLog = new AppLog();
//	testEntity.setId("li");
	appLog.setOperateDetail("tsinghua2");
	appLog.setOperateResult("lihubin2");

	baseDAO.save(appLog);
	
	System.out.println(appLog.getClass());
	System.out.println(appLog.getOid());
	
	
	Map<String, String> attrMap = new HashMap<String, String>();
	attrMap.put("operateDetail","'tsinghua2'");
	attrMap.put("operateResult","'tsinghua2'");
	
	List<Object> list = baseDAO.findByAttr(AppLog.class, attrMap);
	for (int i = 0; i < list.size(); i++) {
		System.out.println(((AppLog)list.get(i)).getOid());
	}
	
	
	ConnectPool cPool = (ConnectPool)ctx.getBean("connectPool");
	Connection cn1 = null;
	
	try {
		cn1 = cPool.getCon();
//		cPool.release(cn1);
		Connection cn2 = cPool.getCon();
		Connection cn3 = cPool.getCon();
		Connection cn4 = cPool.getCon();
		Connection cn5 = cPool.getCon();
		Connection cn6 = cPool.getCon();
		Connection cn7 = cPool.getCon();
		Connection cn8 = cPool.getCon();
		Connection cn9 = cPool.getCon();
		Connection cn10 = cPool.getCon();
		Connection cn11 = cPool.getCon();
		Connection cn12 = cPool.getCon();
		Connection cn13 = cPool.getCon();
		Connection cn14 = cPool.getCon();
		Connection cn15 = cPool.getCon();
		Connection cn16 = cPool.getCon();
		Connection cn17 = cPool.getCon();
		Connection cn18 = cPool.getCon();
		Connection cn19 = cPool.getCon();
		Connection cn20 = cPool.getCon();
		Connection cn21 = cPool.getCon();
	} catch (RSPException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	try {
		cPool.clear();
	} catch (RSPException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		cn1.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	AppLog te = appLog; 
	
	AppLog tr = (AppLog)baseDAO.findById(AppLog.class,te.getOid());
	
	System.out.println(tr.getOperateDetail());
	System.out.println(tr.getOperateResult());
	System.out.println(tr.getOid());
	
	tr.setOperateDetail("tsinghua1");
	tr.setOperateResult("lihubin2");
	te = (AppLog)baseDAO.update(tr);
	
	System.out.println(te.getOperateDetail());
	System.out.println(te.getOperateResult());
	System.out.println(te.getOid());
	
	baseDAO.delete(te.getClass(), te.getOid());

	
	
	list = baseDAO.findAll(AppLog.class,1,8);
	for (int i = 0; i < list.size(); i++) {
		System.out.println(((AppLog)list.get(i)).getOid());
	}


	
	String string = new String("SELECT t FROM AppLog t WHERE t.operateResult = ?0");
	Object[] object = new Object[1];
	String a = new String("lihubin");
	object[0] = a;
	list = baseDAO.findList(string,object,2,5);
	for (int i = 0; i < list.size(); i++) {
		System.out.println(((AppLog)list.get(i)).getOid());
	}
	
	
//	String lasql = "INSERT INTO user1(id,name,age) VALUES(1,'test',22)";
//	LaUDBaseDAO laDAO = (LaUDBaseDAO)ctx.getBean("laUDBaseDAO");
//	laDAO.execute(lasql);
	
//      PlatformTransactionManager txManager = ctx.getBean(PlatformTransactionManager.class);   
//      new TransactionTemplate(txManager).execute(   
//        new TransactionCallback<Void>() {   
//          @Override  
//          public Void doInTransaction(TransactionStatus status) {   
//            jpaTemplate.persist(testEntity);   
//            return null;   
//          }   
//      });   
      
      
      
      
      
//      Assert.assertEquals(1, count.intValue());   
    }   
}   