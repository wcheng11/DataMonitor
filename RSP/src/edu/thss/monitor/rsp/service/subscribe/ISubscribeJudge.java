package edu.thss.monitor.rsp.service.subscribe;

import java.text.ParseException;
import java.util.List;

import edu.thss.monitor.pub.entity.service.JudgeResult;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;

public interface ISubscribeJudge {
	
//	/**
//	 * 判断订阅方案是否有效，订阅方案来自数据库
//	 * @param deviceID 设备ID
//	 * @return 是否有效
//	 */
//	public boolean judgeDeviceIsSubscribedFromDatabase(String deviceID);
	
//	/**
//	 * 判断订阅方案是否有效，订阅方案来自资源容器
//	 * @param deviceID 设备ID
//	 * @return 是否有效
//	 */
//	public boolean judgeDeviceIsSubscribedFormResource(String deviceID);
	/**
	 * 获取订阅方案列表，并根据订阅判断结果，更新订阅方案资源容器和数据库
	 * @param pdu 解析后的数据包对象
	 * @return    数据包对应的订阅方案列表
	 * @throws ClassCastException 类型转换异常
	 * @throws RSPException       字符串数据异常	 
	 * @throws ParseException     字符数组转换异常
	 */
	public List<JudgeResult> getSubscribeJudge(ParsedDataPacket pdu)throws ClassCastException,RSPException, ParseException ;

}
