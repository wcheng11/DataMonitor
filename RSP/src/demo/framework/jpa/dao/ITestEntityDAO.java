package demo.framework.jpa.dao;

import java.util.List;

import demo.framework.jpa.entity.TestEntity;


/**
 * 测试实体（TestEntity）DAO
 * @author yangtao
 */
public interface ITestEntityDAO {

	/**
	 * 持久化TestEntity对象
	 * @param testEntity
	 * @return
	 */
	public TestEntity saveTestEntity(TestEntity testEntity);
	
	/**
	 * 根据ID查找TestEntity对象
	 * @param testEntity
	 * @return
	 */
	public TestEntity findById(String id);
	
	/**
	 * 范围查找对象列表
	 * @param testEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findList(Class class1,int firstResult,int maxResults);
}
