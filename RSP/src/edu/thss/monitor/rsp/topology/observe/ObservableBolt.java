package edu.thss.monitor.rsp.topology.observe;

import backtype.storm.topology.base.BaseRichBolt;

/**
 * 可观察Bolt
 * @author yangtao
 */
@SuppressWarnings("serial")
public abstract class ObservableBolt extends BaseRichBolt implements IObservable{

	/**
	 * 处理完毕的tuple实时计数
	 */
	private Long count = 0L;
	
	/**
	 * 处理完毕的tuple总计数
	 */
	private Long totalCount = 0L;
	
	/**
	 * 处理异常的tuple实时计数
	 */
	private Long failCount = 0L;
	
	/**
	 * 处理异常的tuple总计数
	 */
	private Long totalFailCount = 0L;
	
	/**
	 * 处理的实时工况数
	 */
	private Long wsCount = 0L;
	
	/**
	 * 处理的总工况数
	 */
	private Long totalWSCount = 0L;
	
	//是否被观察
	private boolean isObserved = false;
	
	/**
	 * 设置状态(是否观察)
	 */
	public void setState(boolean isObserved){
		this.isObserved = isObserved;
	}
	
	/**
	 * 清除计数
	 */
	public void clearCount(){
		synchronized (count) {
			count = 0L;
			failCount = 0L;
			wsCount = 0L;
		}
	}
	
	/**
	 * 执行计数
	 */
	public void count(){
		if(isObserved)
			count++;
		totalCount++;
	}
	
	/**
	 * 执行计数
	 */
	public void count(int num){
		if(isObserved)
			count++;
			wsCount += num;
		totalCount++;
		totalWSCount += num;
	}
	
	/**
	 * 执行异常计数
	 */
	public void failCount(){
		if(isObserved)
			failCount++;
		totalFailCount++;
	}
	
	/**
	 * 执行异常计数
	 */
	public void failCount(int num){
		if(isObserved)
			failCount+=num;
		totalFailCount+=num;
	}
	
	/**
	 * 获得处理完成的实时计数
	 */
	public Long getCount(){
		return count;
	}
	
	/**
	 * 获得处理完成的总计数
	 */
	public Long getTotalCount(){
		return totalCount;
	}

	/**
	 * 获得处理异常的实时计数
	 */
	public Long getFailCount() {
		return failCount;
	}

	/**
	 * 获得处理异常的总计数
	 */
	public Long getTotalFailCount() {
		return totalFailCount;
	}

	/**
	 * 获得工况统的实时计数
	 */
	public Long getWsCount() {
		return wsCount;
	}

	/**
	 * 获得工况的总计数
	 */
	public Long getTotalWSCount() {
		return totalWSCount;
	}
	
	
	
	
}
