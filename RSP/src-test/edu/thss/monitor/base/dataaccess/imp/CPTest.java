package edu.thss.monitor.base.dataaccess.imp;

import java.sql.*;
import java.util.*;

//import org.junit.Before;
//import static org.junit.Assert.*;
//import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.thss.monitor.base.dataaccess.imp.ConnectPool.Connect;

public class CPTest {
	protected static ApplicationContext context = 
		new ClassPathXmlApplicationContext(
				new String[]{"META-INF/beans-base.xml"}
		);

	public static void main(String[] args) {
		ConnectPool cp = (ConnectPool)context.getBean("connectPool");
		
		for(int i=1;i<=12;i++){
			MThread mThread = new MThread(cp,new Integer(i).toString());
			new Thread(mThread).start();
		}
		try{
//			cp.clear();
			System.out.println("count:"+cp.getPool().size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
class MThread implements Runnable {
	private ConnectPool cp;
	private String name;

	public MThread(ConnectPool cp,String name){
		this.cp = cp;
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i=1;i<=100;i++){
			try{
				Connection con = cp.getCon();
				
				//Random rd = new Random();
				Thread.sleep(100);
				System.out.println("user:"+name+" times:"+i+" count:"+cp.getPool().size());
				
				cp.release(con);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
