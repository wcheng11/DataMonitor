package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class DoubleDivide10 implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		// TODO Auto-generated method stub
		double result = Double.parseDouble(value);
		result /= 10.0;
		return String.format("%.3f",result);
		//return String.valueOf(result);
	}
	
//	public static void main( String args[]) {
//		DoubleDivide10 test = new DoubleDivide10();
//		try {
//			System.out.println(test.trans("12666.3453463"));
//		} catch (RSPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
