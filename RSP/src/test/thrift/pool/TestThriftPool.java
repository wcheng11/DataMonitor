package test.thrift.pool;


/**
 * 测试Thrift连接池
 * @author yangtao
 */
public class TestThriftPool {

	/**
	 * 测试使用连接池
	 */
	public static void testUsePool(){
		for(int i=0;i<100;i++){
			Thread t = new Thread(new ThriftPoolTestRunnable(i));
			t.start();
		}
	}
	
	/**
	 * 测试未使用连接池
	 */
	public static void testNotUsePool(){
		for(int i=0;i<100;i++){
			Thread t = new Thread(new ThriftNotUsePoolTestRunnable(i));
			t.start();
		}
	}
	
	public static void main(String[] args){
		//测试使用连接池
//		testUsePool();
		//测试未使用连接池
		testNotUsePool();
	}
}
