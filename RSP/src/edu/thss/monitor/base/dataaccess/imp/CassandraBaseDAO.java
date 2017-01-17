package edu.thss.monitor.base.dataaccess.imp;

import com.datastax.driver.core.*;

public class CassandraBaseDAO {

	protected Cluster cluster;

	protected Session session;
	
	protected void connect(String[] contactPoints, int port) {

        cluster = Cluster.builder()
                .addContactPoints(contactPoints).withPort(port)
                .build();

        System.out.printf("Connected to cluster: %s%n", cluster.getMetadata().getClusterName());

        session = cluster.connect();
	}
	
	protected void close(){
		session.close();
		cluster.close();
	}
	
}
