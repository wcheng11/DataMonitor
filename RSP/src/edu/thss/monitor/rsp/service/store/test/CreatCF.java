package edu.thss.monitor.rsp.service.store.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.cassandra.thrift.Cassandra.system_add_column_family_args;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreatCF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			Connection con = DriverManager.getConnection("jdbc:cassandra://192.168.10.201:9170?version=2");
			Statement laudStatement=con.createStatement();
			laudStatement.execute("create keyspace  laudTest with  strategy_class ='org.apache.cassandra.locator.SimpleStrategy' and strategy_options:replication_factor=1;");
			laudStatement.execute("use laudTest");
			laudStatement.execute("create columnfamily sany (id varchar primary key,name varchar);");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("it is ok!");
		
	}

}
