/**
 * @package edu.ty.kmx.dao
 */
package edu.thss.monitor.base.dataaccess.imp;

import com.datastax.driver.core.Cluster;
import com.sagittarius.core.SagittariusClient;
import com.sagittarius.read.Reader;
import com.sagittarius.write.Writer;

/**
 * @author Administrator
 * @time 2017年1月10日
 */
public class KMXBaseDAO {

	private KMXConnection connection; 
	
	protected SagittariusClient client;
	
	public void init(){
        Cluster cluster = connection.getCluster();
        client = new SagittariusClient(cluster);
        System.out.println("KMXBaseDAO");
	}
	
	protected Reader getReader(){
		return null;
	}
	
	protected Writer getWriter(){
		return client.getWriter();
	}
	
	public void setConnection(KMXConnection connection){
		this.connection = connection;
	}

	public KMXConnection getConnection() {
		return connection;
	}
	
}
