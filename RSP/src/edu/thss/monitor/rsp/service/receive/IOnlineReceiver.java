package edu.thss.monitor.rsp.service.receive;

/**
 * 在线接收接口（UDP数据）
 * @author yangtao
 */
public interface IOnlineReceiver {
	
	/**
	 * 设置端口号
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * 启动UDP监听线程
	 * @param port - 监听端口号
	 */
	public void startUDPListenThread();
	
}
