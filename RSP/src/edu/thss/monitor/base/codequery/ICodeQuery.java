package edu.thss.monitor.base.codequery;

import java.util.List;

import edu.thss.monitor.pub.entity.Code;

/**
 * 用于对代号进行查询
 * @author zhaokai
 *
 */
public interface ICodeQuery {
	
	/**
	 * 根据给定主码值返回代码对象
	 * @param majorCode 给定主码值
	 * @return 代码对象列表
	 */
	
	public List<Code> getCodesByMajorCode(int majorCode);
	/**
	 * 根据给定主码值返回子码
	 * @param majorCode  给定主码值
	 * @return 子码值列表
	 */
	public List<Integer> getSubCodesByCode(int majorCode);
	/**
	 * 根据给定主码值返回子码值
	 * @param majorCode  给定主码值
	 * @return 代码描述列表
	 */
	public List<String> getDescriptionsByMajorCode(int majorCode);
	/**
	 * 根据给定主码值和子码值返回代码描述
	 * @param majorCode 给定主码值
	 * @param subCode   给定子码值
	 * @return 代码描述列表
	 */
	public List<String> getDescriptionsByCodes(int majorCode, int subCode);
}
