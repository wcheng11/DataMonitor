/**
 * @package test.kmx
 */
package test.kmx;

import java.util.Date;

import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.service.WorkStatusData;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.rsp.service.store.ICompanyDataDAO;

/**
 * @author Administrator
 * @time 2017年1月16日
 */
public class KMXTest implements Runnable {

	int start;
	
	int end;
	
	ICompanyDataDAO dao;
	
	/**
	 * 
	 */
	public KMXTest(int start, int end, ICompanyDataDAO dao) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
		this.dao = dao;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=start;i<=end;i++){
			WorkStatusData workStatusData = new WorkStatusData();
			Device device = new Device();
			device.setDeviceID("7FB31530");
			workStatusData.setDevice(device);
			workStatusData.setWorkStatus("800028");
			workStatusData.setDataValue(""+i);
			workStatusData.setTimestamp(new Date());
			try {
				dao.save(workStatusData);
			} catch (RSPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) throws RSPException {
		Thread thread1 = new Thread(new KMXTest(1, 20, (ICompanyDataDAO) AppContext.getSpringContext().getBean("kmxWriteDAO")));
		Thread thread2 = new Thread(new KMXTest(21, 40, (ICompanyDataDAO) AppContext.getSpringContext().getBean("kmxWriteDAO")));
		Thread thread3 = new Thread(new KMXTest(41, 60, (ICompanyDataDAO) AppContext.getSpringContext().getBean("kmxWriteDAO")));
		Thread thread4 = new Thread(new KMXTest(61, 80, (ICompanyDataDAO) AppContext.getSpringContext().getBean("kmxWriteDAO")));
		Thread thread5 = new Thread(new KMXTest(81, 100, (ICompanyDataDAO) AppContext.getSpringContext().getBean("kmxWriteDAO")));
		Thread thread6 = new Thread(new KMXTest(101, 120, (ICompanyDataDAO) AppContext.getSpringContext().getBean("kmxWriteDAO")));
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
	}

	
}
