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
	
	public void recordTime(TimeLog timeLog) {
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

}
