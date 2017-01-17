package edu.thss.monitor.rsp.topology.observe;
/**
 * 可观察接口
 * @author yangtao
 */
public interface IObservable {

	/**
	 * 执行正常计数
	 */
	public void count();
	
	/**
	 * 执行正常计数
	 */
	public void count(int num);
	
	/**
	 * 获得实时正常计数
	 */
	public Long getCount();
	
	/**
	 * 获得总正常计数
	 */
	public Long getTotalCount();
	
	/**
	 * 执行异常计数
	 */
	public void failCount();
	
	/**
	 * 执行异常计数
	 */
	public void failCount(int num);
	
	/**
	 * 获得实时异常计数
	 */
	public Long getFailCount();
	
	/**
	 * 获得总异常计数
	 */
	public Long getTotalFailCount();
	
	/**
	 * 获得工况统的实时计数
	 */
	public Long getWsCount();

	/**
	 * 获得工况的总计数
	 */
	public Long getTotalWSCount();
	
	
	/**
	 * 设置状态(是否观察)
	 */
	public void setState(boolean isObserved);
	
	/**
	 * 清除计数
	 */
	public void clearCount();
	
}
