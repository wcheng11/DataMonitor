import java.util.ArrayList;

import ty.pub.BeanUtil;

public class TestKryo {

	public static void main(String args[]){
		test t = new test(1, 2);
		ArrayList<test> lists = new ArrayList<test>();
		lists.add(t);
		TestDao test = new TestDao();
		test.setOne(1);
		test.setTests(lists);
		byte[] bs = BeanUtil.toByteArray(test);
		TestDao result = BeanUtil.toObject(bs, TestDao.class);
		System.out.println(result.getTests().get(0).b);
		System.out.println(bs.length);
	}
	
}
