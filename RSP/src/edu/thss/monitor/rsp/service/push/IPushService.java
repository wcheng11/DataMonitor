package edu.thss.monitor.rsp.service.push;

import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 数据推送业务接口类
 * @author yangtao
 */
public interface IPushService {

	/**
	 * 推送数据
	 * @param jr - 订阅判断结果
	 */
	public void push(JudgeResult jr) throws RSPException;
	
}
