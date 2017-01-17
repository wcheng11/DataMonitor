/**
 * @package edu.thss.monitor.rsp.topology.test
 */
package edu.thss.monitor.rsp.topology.test;

import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.store.IDataStore;
import edu.thss.monitor.rsp.service.store.imp.DataStore;

/**
 * @author Administrator
 * @time 2017年1月12日
 */
public class TestKmxDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IDataStore dataStore = (IDataStore)AppContext.getSpringContext().getBean("dataStore");
		
		System.out.println(dataStore.toString());
		
	}

}
