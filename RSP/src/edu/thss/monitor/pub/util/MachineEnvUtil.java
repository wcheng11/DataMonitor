package edu.thss.monitor.pub.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;

/**
 * 机器环境工具类
 * 
 * @author yangtao
 */
public class MachineEnvUtil {

//	public static String localIp;
	
	/**
	 * 获得本机IP
	 * @return
	 * @throws SocketException
	 */
	public static String getMachineIP() throws SocketException {
		String[] ipArray = getIPv4();
		//测试打印
		StringBuffer sb = new StringBuffer("getMachineIP()::::ip过滤前ip数组为：");
		for(String tmp:ipArray){
			sb.append("[").append(tmp).append("],");
		}
		System.out.println(sb.substring(0, sb.length()-1));
		
		for(String ip:ipArray){
			if(!ip.equals("127.0.0.1")){
				return ip;
			}
		}
		return null;
	}
	
	/**
	 * 获得本机IP，消除异常并不作处理（一般用于打印信息）
	 * @return
	 */
	public static String getMachineIPNoException(){
		try {
			return getMachineIP();
		} catch (SocketException e) {
			//异常不作处理
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据前缀过滤条件获得本机IP
	 * @return
	 * @throws SocketException
	 */
	public static String getMachineIPByFilter(String frontFilter) throws SocketException {
		String[] ipArray = getIPv4();
		//测试打印
		StringBuffer sb = new StringBuffer("ip过滤字符串("+frontFilter+"),备选的ip数组为：");
		for(String tmp:ipArray){
			sb.append("[").append(tmp).append("],");
		}
		System.out.println(sb.substring(0, sb.length()-1));
		
		for(String ip:ipArray){
			if(ip.startsWith(frontFilter)){
				return ip;
			}
		}
		return null;
	}
	
	/**
	 * 获得本机IPv4的所有ip数组
	 * @return
	 * @throws SocketException
	 */
	@SuppressWarnings("unchecked")
	public static String[] getIPv4() throws SocketException{
		List<String> tmp = new ArrayList<String>();
		Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
					.nextElement();
			Enumeration addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address && !ip.equals("127.0.0.1")) {
					tmp.add(ip.getHostAddress());
					break;
				}
			}
		}
		String[] rst = new String[tmp.size()];
		tmp.toArray(rst);
		return rst;
	}
	
	/**
	 * 根据前缀过滤条件获得本机IP，消除异常并不作处理
	 * @return
	 */
	public static String getMachineIPByFilterNoExp(String frontFilter){
		try {
			return getMachineIPByFilter(frontFilter);
		} catch (SocketException e) {
			//异常不作处理
		}
		return null;
	}
	
	@Test
	public void test() throws SocketException{
		System.out.println("本机IP为："+MachineEnvUtil.getMachineIP());
		System.out.println("本机IP为："+MachineEnvUtil.getMachineIPByFilter("166.111.81"));
	}
}
