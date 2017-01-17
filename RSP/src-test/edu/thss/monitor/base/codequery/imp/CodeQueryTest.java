/**
 * 
 */
package edu.thss.monitor.base.codequery.imp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.RollbackException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.thss.monitor.base.codequery.ICodeQuery;
import edu.thss.monitor.pub.dao.ICodeDAO;
import edu.thss.monitor.pub.entity.Code;
import edu.thss.monitor.pub.sys.AppContext;

/**
 * @author fx
 *
 */
public class CodeQueryTest {
	static ICodeDAO dao;
	static ICodeQuery query;
	
	static List<Object> codes;
	
	static Code code9997; static Code code9898; static Code code9799;
	
	static List<Integer> subcodes1;
	static List<Integer> subcodes2;
	static List<Integer> subcodes3;
	
	static List<String> names1;
	static List<String> names2;
	static List<String> names3;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = (ICodeDAO)AppContext.getSpringContext().getBean("codeDAO");
		
		codes = new ArrayList<Object>();
		for (int i = 1; i < 4; i++)
			for (int j = 1; j < 7 - i; j++)
				codes.add(initCode(
						new Code(), i, j, "测试代号"+i+"-"+j));


		code9997 = initCode(new Code(), 99, 97, "测试代号99-97");
		code9898 = initCode(new Code(), 98, 98, "测试代号98-98");
		code9799 = initCode(new Code(), 97, 99, "测试代号97-99");
		codes.add(code9997); codes.add(code9898); codes.add(code9799);
		dao.saveBatch(codes);
		
		query = new CodeQuery();
		
		subcodes1 = Arrays.asList(new Integer[]{1,2,3,4,5});
		subcodes2 = Arrays.asList(new Integer[]{1,2,3,4});
		subcodes3 = Arrays.asList(new Integer[]{1,2,3});
		
		names1 = Arrays.asList(
				new String[]{"测试代号1-1", "测试代号1-2", "测试代号1-3", "测试代号1-4", "测试代号1-5"});
		names2 = Arrays.asList(
				new String[]{"测试代号2-1", "测试代号2-2", "测试代号2-3", "测试代号2-4"});
		names3 = Arrays.asList(
				new String[]{"测试代号3-1", "测试代号3-2", "测试代号3-3"});
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (Object obj : codes) {
			Code code = (Code)obj;
			dao.delete(code.getClass(), code.getOid());
		}
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.codequery.imp.CodeQuery#CodeQuery()}.
	 */
	@Test
	public final void testCodeQuery() {
	}

	/**
	 * 测试保存主码或子码为负的代号，
	 * 在mro中创建代号对象时要检查主码或子码是否为负。
	 */
	@Test
	public final void testInsertCodeNeg() {
		Code codeN10N10 = initCode(new Code(), -10, -10, "测试代号-10-10");
		Code codeN10P10 = initCode(new Code(), -10, 10, "测试代号-10+10");
		Code codeP10N10 = initCode(new Code(), 10, -10, "测试代号-10-10");
		dao.save(codeN10N10);
		dao.save(codeN10P10);
		dao.save(codeP10N10);
		dao.delete(codeN10N10.getClass(), codeN10N10.getOid());
		dao.delete(codeN10P10.getClass(), codeN10P10.getOid());
		dao.delete(codeP10N10.getClass(), codeP10N10.getOid());
	}
	
	/**
	 * 测试保存主码或子码超过两位数的代号，
	 * 预计抛出RollbackException，代号保存失败，
	 * 在mro中创建代号对象时要检查主码或子码是否超过两位数。
	 */
	@Test(expected=RollbackException.class)
	public final void testInsertCodeBig() {
		Code code110200 = initCode(new Code(), 110, 200, "测试代号110-200");
		dao.save(code110200);
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.codequery.imp.CodeQuery#getCodesByMajorCode(int)}.
	 */
	@Test
	public final void testGetCodesByMajorCode() {
		List<Code> codesActl9997 = query.getCodesByMajorCode(99);
		List<Code> codesActl9898 = query.getCodesByMajorCode(98);
		List<Code> codesActl9799 = query.getCodesByMajorCode(97);
		
		assertEquals(code9997.getOid().toUpperCase(),
				codesActl9997.get(0).getOid());
		assertEquals(code9898.getOid().toUpperCase(),
				codesActl9898.get(0).getOid());
		assertEquals(code9799.getOid().toUpperCase(),
				codesActl9799.get(0).getOid());
	}
	
	/**
	 * Test method for {@link edu.thss.monitor.base.codequery.imp.CodeQuery#getSubCodesByCode(int)}.
	 */
	@Test
	public final void testGetSubCodesByCode() {
		List<Integer> subcodesActl1 = query.getSubCodesByCode(1);
		List<Integer> subcodesActl2 = query.getSubCodesByCode(2);
		List<Integer> subcodesActl3 = query.getSubCodesByCode(3);
		Collections.sort(subcodesActl1);
		Collections.sort(subcodesActl2);
		Collections.sort(subcodesActl3);
		assertEquals(subcodes1, subcodesActl1);
		assertEquals(subcodes2, subcodesActl2);
		assertEquals(subcodes3, subcodesActl3);
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.codequery.imp.CodeQuery#getDescriptionsByMajorCode(int)}.
	 */
	@Test
	public final void testGetDescriptionsByMajorCode() {
		List<String> namesActl1 = query.getDescriptionsByMajorCode(1);
		List<String> namesActl2 = query.getDescriptionsByMajorCode(2);
		List<String> namesActl3 = query.getDescriptionsByMajorCode(3);
		Collections.sort(namesActl1);
		Collections.sort(namesActl2);
		Collections.sort(namesActl3);
		assertEquals(names1, namesActl1);
		assertEquals(names2, namesActl2);
		assertEquals(names3, namesActl3);
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.codequery.imp.CodeQuery#getDescriptionsByCodes(int, int)}.
	 */
	@Test
	public final void testGetDescriptionsByCodes() {
		List<String> namesActl2 = query.getDescriptionsByCodes(1, 2);
		List<String> namesActl4 = query.getDescriptionsByCodes(1, 4);
		List<String> namesAct33 = query.getDescriptionsByCodes(3, 3);
		Collections.sort(namesActl2);
		Collections.sort(namesActl4);
		Collections.sort(namesAct33);
		assertEquals(names1.get(1), namesActl2.get(0));
		assertEquals(names1.get(3), namesActl4.get(0));
		assertEquals(names3.get(2), namesAct33.get(0));
	}
	
	
	/**
	 * 初始化一个代号对象
	 * @param code 代号对象
	 * @param majorCode 代号主码
	 * @param subcode 代号子码
	 * @param name 代号名称
	 * @return 初始化后的代号对象
	 */
	public final static Code initCode(Code code, int majorCode, int subcode, String name) {
		code.setMajorCode(majorCode);
		code.setSubcode(subcode);
		code.setName(name);
		return code;
	}
	
}
