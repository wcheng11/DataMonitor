package demo.framework.jpa;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import demo.framework.jpa.dao.ITestEntityDAO;
import demo.framework.jpa.dao.TestEntityDAO;
import demo.framework.jpa.entity.TestEntity;

public class MainFuncOprJPADemo {

	private static ITestEntityDAO teDao = new TestEntityDAO();

	@Test
	public void testInsertMultiData(){
		for(int i=0;i<100;i++){
			TestEntity te = new TestEntity();
			te.setName("name"+i);
			te.setValue("value"+i);
			teDao.saveTestEntity(te);
		}
	}
	
	//测试查询一定范围的数据列表
	public void testQueryPart(){
		List lst = teDao.findList(TestEntity.class, 5, 25);
		for(TestEntity te:(List<TestEntity>)lst){
			System.out.println(te.getName());
		}
	}
	
	//测试插入数据
	public void testInsertOpr() { 
		TestEntity te = new TestEntity();
		te.setName("name");
		te.setValue("value");
		TestEntity inTe = teDao.saveTestEntity(te);
//		System.out.println(inTe.getId());
		Assert.assertTrue(inTe.getId().length()==32);//断言已入库并生成uuid主键
		TestEntity outTe = teDao.findById(inTe.getId());
		Assert.assertNotNull(outTe);
    }
	
}