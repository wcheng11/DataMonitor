package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 定时信息参数
 * @author wen
 *
 */
public class TimeParaOpt implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		
		String sub1 = value.substring(0,8);
		String sub2 = value.substring(8,16);
		String sub3 = value.substring(16,24);
		String sub4 = value.substring(24,32);	
		String sub5 = value.substring(32,40);		
		String sub6 = value.substring(40);	
				
//		System.out.print("sub6的值为"+sub6);		
		
		int digit1=Integer.valueOf(sub1,2);
		int digit2=Integer.valueOf(sub2,2);
		int digit3=Integer.valueOf(sub3,2);		
		int digit4=Integer.valueOf(sub4,2);		
		int digit5=Integer.valueOf(sub5,2);		
		int digit6=Integer.valueOf(sub6,2);
			
//		System.out.print(String.format("digit6的数值为：digit6：%02x",digit6));
		
		String hex1 = String.format("%02x",digit1);		
		String hex2 = String.format("%02x",digit2);
		String hex3 = String.format("%02x",digit3);		
		String hex4 = String.format("%02x",digit4);		
		String hex5 = String.format("%02x",digit5);		
		String hex6 = String.format("%02x",digit6);
		
        String str=""; 
        str=hex1+hex2+hex3+hex4+hex5+hex6;
        return str; 				
	}	
}