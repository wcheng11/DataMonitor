package edu.thss.monitor.rsp.service.receive.imp;

import java.util.HashSet;
import java.util.Set;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.util.MachineEnvUtil;
import edu.thss.monitor.rsp.service.receive.IOnlineReceiver;

public class OnlineReceiver implements IOnlineReceiver {

	private static Set<Integer> portSet = new HashSet<Integer>();
	
	private int port;
	
	public void setPort(int port) {
		this.port = port;
	}
	
//	public OnlineReceiver(int port){
//		OnlineReceiver.port = port;
//	}
	
	@Override
	public void startUDPListenThread() {
		synchronized(portSet){
			if(!portSet.contains(port)){
				Thread udpServerThread = new Thread(new UDPServer(port));
				udpServerThread.start();
				LogRecord.info(LogConstant.LOG_FLAG_RECEIVE+"启动UDP接收服务器，开始监听...(IP:"+MachineEnvUtil.getMachineIPNoException()+",端口号:"+port+")");
				portSet.add(port);
			}
		}
	}

}
