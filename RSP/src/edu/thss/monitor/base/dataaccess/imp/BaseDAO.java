package edu.thss.monitor.base.dataaccess.imp;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import edu.thss.monitor.base.dataaccess.IBaseDAO;

/**
 * BaseDAO的实现
 * @author lihubin
 *
 */
public class BaseDAO implements IBaseDAO{
	
	private EntityManagerFactory entityManagerFactory;   
	
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

    @Override
	public Object save(Object object) {
		EntityManager em = entityManagerFactory.createEntityManager(); 
		em.getTransaction().begin(); 
		em.persist(object); 
		em.getTransaction().commit();
		em.close();
		return object;
	}

	@Override
	public Object findById(Class class1, Object id) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Object object = em.find(class1, id);
		em.close();
		return object;
	}
	
	@Override
	public List<Object> findByAttr(Class class1,Map<String, String> attrMap) {
		EntityManager em = entityManagerFactory.createEntityManager();
		String qlString = new String("SELECT t FROM " + class1.getName() + " t WHERE");
		Set set = attrMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>)it.next();
			qlString = qlString + " t." + entry.getKey() + "=" + entry.getValue() + " AND";
			
		}
		qlString = qlString.substring(0, qlString.lastIndexOf("AND")-1);
		Query query = em.createQuery(qlString);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}
	
	@Override
	public Object update(Object object) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin(); 
		em.merge(object);
		em.getTransaction().commit();
		em.close();
		return object;
	}

	@Override
	public void delete(Class class1, Object oid) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(class1, oid));
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public List<Object> findAll(Class class1) {
		EntityManager em = entityManagerFactory.createEntityManager();
		String qlString = new String("SELECT t FROM " + class1.getName() + " t");
		Query query = em.createQuery(qlString);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Object> findAll(Class class1, int firstResult, int maxResults) {
		EntityManager em = entityManagerFactory.createEntityManager();
		String qlString = new String("SELECT t FROM " + class1.getName() + " t");
		Query query = em.createQuery(qlString);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Object> findList(String queryString, Object[] queryParams) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createQuery(queryString);
		if(queryParams!=null){
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i, queryParams[i]);
			}
		}
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Object> findList(String queryString, Object[] queryParams,
			int firstResult, int maxResults) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createQuery(queryString);
		for (int i = 0; i < queryParams.length; i++) {
			query.setParameter(i, queryParams[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}
	
	@Override
	public List<Object> findListByNativeSql(String nativeSql,int firstResult, int maxResults) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createNativeQuery(nativeSql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}
	
	@Override
	public List<Object> findListByNativeSql(String nativeSql) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createNativeQuery(nativeSql);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public void saveBatch(List<Object> list) {
		EntityManager em = entityManagerFactory.createEntityManager(); 
		em.getTransaction().begin(); 
		for (int i = 0; i < list.size(); i++) {
			em.persist(list.get(i));
		}
		em.getTransaction().commit();
		em.close();
	}

}
