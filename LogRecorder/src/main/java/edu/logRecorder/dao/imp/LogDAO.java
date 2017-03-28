package edu.logRecorder.dao.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import edu.logRecorder.dao.ILogDAO;
import edu.logRecorder.entity.TimeLog;

public class LogDAO implements ILogDAO {

	Connection conn;
	
	public LogDAO(){
		try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(ClassNotFoundException e1){
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }
		String url="jdbc:mysql://localhost:3306/Log";    //JDBC的URL    
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        
        try {
            conn = DriverManager.getConnection(url,"root","123456");
            
            System.out.print("成功连接到数据库！");
            
        } catch (SQLException e){
            e.printStackTrace();
        }
	}
	
	public synchronized void recordTimeLog(TimeLog timeLog) {
		// TODO Auto-generated method stub
		try {
            PreparedStatement stmt = conn.prepareStatement("insert into TimeLog (id, process, start, end) values (?,?,?,?)");
            stmt.setString(1, timeLog.getId());
            stmt.setString(2, timeLog.getProcess());
            stmt.setLong(3, timeLog.getStartTime());
            stmt.setLong(4, timeLog.getEndTime());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
	}
	
	protected void finalize()throws Throwable{
		conn.close();
	}

	public synchronized void recordTime(String[] times) {
		// TODO Auto-generated method stub
		try {
            PreparedStatement stmt = conn.prepareStatement("insert into Times (s1, s2, s3, s4, s5, s6, s7, s8, s9, s10 ,s11, s12) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            for(int i = 1;i<=12;i++){
            	stmt.setLong(i,-1);
            }
            for(String each:times){
            	String args[] = each.split(":");
            	stmt.setLong(Integer.parseInt(args[0]), Long.parseLong(args[1]));
            }
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
	}

	public synchronized void log(String log) {
		// TODO Auto-generated method stub
		try {
            PreparedStatement stmt = conn.prepareStatement("insert into Log (log) values (?)");
            stmt.setString(1, log);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
	}

}
