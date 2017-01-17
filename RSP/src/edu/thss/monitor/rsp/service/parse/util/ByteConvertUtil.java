package edu.thss.monitor.rsp.service.parse.util;

import org.junit.Test;

/**
 * 字节数据相关转方法
 * @author zhuangxy
 * 2013-1-16
 */
public class ByteConvertUtil {
	
	/**
	 * 将字节数组转化为十六进制字符串
	 * @param bytes 需要转化的字节数组
	 * @return 转化后的字符串，转化后长度为转化数组的长度的2倍（首位为0的会补0）
	 */
	public static String bytesToHexString(byte[] bytes){
		int unitLength = 2;
		
		StringBuffer result = new StringBuffer();
		StringBuffer sb = new StringBuffer(); //中间结果
		for (byte b : bytes) {
			sb = sb.append(Integer.toHexString(b & 0xff ));
			if(sb.length() < unitLength){ //位数不够，补零
				sb.insert(0, "0");
			}
			result.append(sb);
			sb.setLength(0);
		}
		
		return result.toString();
	}
	
	/**
	 * 将字节数组转化为二进制字符串
	 * @param bytes 需要转化的字节数组
	 * @return 转化后的字符串，转化后的字符串，转化后长度为转化数组的长度的8倍（首位为0的会补0）
	 */
	public static String bytesToBinaryString(byte[] bytes){
		
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(ByteConvertUtil.byteToBitString(b));
		}
		
		return result.toString();
	}
	
	/**
	 * 将一个byte型数据转为bit的String
	 * @param b 带转化的byte数据
	 * @return 转化后结果（8位）
	 */
	public static String byteToBitString(byte b) {
	    return "" +
	      (byte)((b >> 7) & 0x1) +
	        (byte)((b >> 6) & 0x1) +
	          (byte)((b >> 5) & 0x1) +
	            (byte)((b >> 4) & 0x1) +
	              (byte)((b >> 3) & 0x1) +
	                (byte)((b >> 2) & 0x1) +
	                  (byte)((b >> 1) & 0x1) +
	                    (byte)((b >> 0) & 0x1);
	  }
	
	/**
	 * 获取一个byte字符的某几位数据
	 * @param b 需要截取数位的byte数据
	 * @param beginIndex 开始位数
	 * @param endIndex 结束位数
	 * @return 截取的数据段
	 */
	public static String getByteBitString(byte b, int beginIndex, int endIndex) {
		
		String str = ByteConvertUtil.byteToBitString(b);
		String result = str.substring(beginIndex, endIndex);
		
		return result;
	    
	 }
	
	public static byte[] binaryStringToBytes(String str){
		int size = str.length()/8;
		byte[] result = new byte[size];
		
		int index = 0;
		while(index < str.length()){
			
			String sub = str.substring(index, index + 8);

			result[index / 8] =  (byte) Integer.parseInt(sub, 2);  
			index += 8;
			
		}
		
		return result;
	}

	@Test
	public void testTransBytesToHexString(){ 
		byte[] data = new byte[2];
		data[0] = 0x30;
		data[1] = 0x30;
//		System.out.println(ByteConvertUtil.bytesToHexString(data));
//		System.out.println(ByteConvertUtil.bytesToBinaryString(data));
//		System.out.println(ByteConvertUtil.byteToBitString(data[0]));
//		System.out.println(ByteConvertUtil.getByteBitString(data[0], 0, 3));
		
		byte[] bs = ByteConvertUtil.binaryStringToBytes("10000000");
		System.out.println(bs[0]);
		System.out.println(ByteConvertUtil.bytesToHexString(bs));
//		System.out.println(bs[1]);
		
		
		
	}
	
}
