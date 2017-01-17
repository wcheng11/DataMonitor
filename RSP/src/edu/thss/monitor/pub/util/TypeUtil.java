package edu.thss.monitor.pub.util;
/**
 * 类型工具类
 * @author yangtao
 */
public class TypeUtil {

	/**
	 * 将字节数组转换成16进制字符串
	 * @param b - 字节数组
	 * @return 16进制字符串
	 */
	public static String getBytesString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
}
