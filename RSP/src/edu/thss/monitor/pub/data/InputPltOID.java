package edu.thss.monitor.pub.data;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import edu.thss.monitor.base.dataaccess.imp.BaseDAO;
import edu.thss.monitor.pub.entity.relate.Temp2Treat;
import edu.thss.monitor.pub.sys.AppContext;

public class InputPltOID {

	protected static ApplicationContext context = AppContext.getSpringContext();
	BaseDAO dao = (BaseDAO) context.getBean("baseDAO");
	
	
	
//	List result = query.getResultList();
	
	
	@Test
	public void inputPltOID(){
		
		String s1;
		String s2;
/*		List<Object> pro2TempList = dao.findAll(Pro2Temp.class);
		for (Object o : pro2TempList) {
			
			Pro2Temp p2t = (Pro2Temp) o;
			s1 = UUID.randomUUID().toString(); 
		    s2 = s1.substring(0,8)+s1.substring(9,13)+s1.substring(14,18)+s1.substring(19,23)+s1.substring(24); 
			p2t.setOid(s2.toUpperCase());
			System.out.println(p2t.getOid());
			dao.update(p2t);
		}*/
		
/*//		Map attrMap = new HashMap();
//		attrMap.put("oid", "null");
//		protocol = (Protocol) dao.findByAttr(Protocol.class, attrMap).get(0);
//		List<Object> t2tpList = dao.findByAttr(Tem2TemPara.class, attrMap);
//		List<Object> t2tpList = dao.findListByNativeSql(nativeSql)
//		List result = query.getResultList();
		List<Object> t2tpList = dao.findAll(CopyOfTem2TemPara.class);
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_RSP");
//		EntityManager entityManager = emf.createEntityManager();
//		String sqlString = "select * from plt_tsm_r_tem2tempara";
//		Query query = entityManager.createNativeQuery(sqlString);
//		List<Object> t2tpList = query.getResultList();
		
		System.out.println("===========" + t2tpList.size());
		for (Object o : t2tpList) {
			
			CopyOfTem2TemPara r = (CopyOfTem2TemPara) o;
			s1 = UUID.randomUUID().toString(); 
		    s2 = s1.substring(0,8)+s1.substring(9,13)+s1.substring(14,18)+s1.substring(19,23)+s1.substring(24); 
			r.setOid(s2.toUpperCase());
			System.out.println(r.getOid());
//			System.out.println(r.getTemplate().getTemplateID() + r.getTemplatePara().getParameterID());
//			System.out.println(r.getTemplate().getOid());
//			System.out.println(r.getTemplatePara().getOid());
			dao.update(r);
		}*/
		
		for (int i = 0; i < 10; i++) {
			
			s1 = UUID.randomUUID().toString(); 
		    s2 = s1.substring(0,8)+s1.substring(9,13)+s1.substring(14,18)+s1.substring(19,23)+s1.substring(24); 
			System.out.println(s2.toUpperCase());
		}
		
/*		List<Object> pro2TempList = dao.findAll(Temp2Treat.class);
		for (Object o : pro2TempList) {
			
			Temp2Treat p2t = (Temp2Treat) o;
			s1 = UUID.randomUUID().toString(); 
		    s2 = s1.substring(0,8)+s1.substring(9,13)+s1.substring(14,18)+s1.substring(19,23)+s1.substring(24); 
//			p2t.setOid(s2.toUpperCase());
//			System.out.println(p2t.getOid());
//			dao.update(p2t);
		}*/
	}
}
