package demo.framework.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import demo.framework.jpa.entity.TestEntity;

public class TestEntityDAO implements ITestEntityDAO{

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_RSP"); 
	
	@Override
	public TestEntity saveTestEntity(TestEntity testEntity) {
		EntityManager em = emf.createEntityManager(); 
		em.getTransaction().begin(); 
		em.persist(testEntity); 
		em.getTransaction().commit(); 
		em.close();
		return testEntity; 
	}

	@Override
	public TestEntity findById(String id) {
		EntityManager em = emf.createEntityManager(); 
		TestEntity te = em.find(TestEntity.class, id);
		em.close();
		return te; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List findList(Class class1,int firstResult,int maxResults){
		EntityManager em = emf.createEntityManager();
		String qlString = new String("SELECT t FROM " + class1.getName() + " t");
		Query query = em.createQuery(qlString);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

}
