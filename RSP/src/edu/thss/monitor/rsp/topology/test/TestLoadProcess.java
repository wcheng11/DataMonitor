package edu.thss.monitor.rsp.topology.test;
/**
 * 测试Spring加载过程
 * @author Student
 */
public class TestLoadProcess {

	private String printStr;
	
	public void setPrintStr(String printStr) {
		this.printStr = printStr;
	}

	public void print(){
		System.out.println(printStr);
	}
}
