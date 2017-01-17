package demo.framework.redisPerformanceTest;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisGetTest {
	String stringOne = "";
	String stringTen = "";
	String stringOneHundred = "";
	String stringOneThousand = "";
	String stringTenThousand = "";
	byte[] byte1Key = ("byte1").getBytes();
	byte[] byte10Key = ("byte10").getBytes();
	byte[] byte100Key = ("byte100").getBytes();
	byte[] byte1000Key = ("byte1000").getBytes();
	byte[] byte10000Key = ("byte10000").getBytes();
	byte[] byte1Value = new byte[1];
	byte[] byte10Value = new byte[10];
	byte[] byte100Value = new byte[100];
	byte[] byte1000Value = new byte[1000];
	byte[] byte10000Value = new byte[10000];
@Test
public void setStringsAndBytesInRedis(){
	
	stringOne = "a";
	stringTen = "aaaaaaaaaa";
	for(int i =0;i<100;i++)
		stringOneHundred+="a";
	for(int i =0;i<1000;i++)
		stringOneThousand+="a";
	for(int i =0;i<10000;i++)
		stringTenThousand+="a";
	byte1Value = stringOne.getBytes();
	byte10Value = stringTen.getBytes();
	byte100Value = stringOneHundred.getBytes();
	byte1000Value = stringOneThousand.getBytes();
	byte10000Value = stringTenThousand.getBytes();
	//System.out.println(stringTenThousand.getBytes().length);
	Jedis jedis = new Jedis("192.168.10.35", 6379);
	jedis.select(10);
	jedis.set("string1", stringOne);
	jedis.set("string10", stringTen);
	jedis.set("string100", stringOneHundred);
	jedis.set("string1000", stringOneThousand);
	jedis.set("string10000", stringTenThousand);
	jedis.set(byte1Key,byte1Value);
	jedis.set(byte10Key,byte10Value);
	jedis.set(byte100Key,byte100Value);
	jedis.set(byte1000Key,byte1000Value);
	jedis.set(byte10000Key,byte10000Value);
	//System.out.println(jedis.get("string1"));
	//System.out.println(jedis.get("string10"));
	//System.out.println(jedis.get("string100"));
	//System.out.println(jedis.get("string1000"));
	//System.out.println(jedis.get("string10000"));//Eclipse不显示，redis客户端显示
	jedis.quit();
}
@Test
public void getStringsAndBytesInRedis(){
	for(int k=0;k<10;k++){
	Long dateBegin,dateEnd;
	
	Jedis jedis = new Jedis("192.168.10.35", 6379);
	jedis.select(10);
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get("string1");
	dateEnd = System.currentTimeMillis();
	System.out.println("TestString1==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get("string10");
	dateEnd = System.currentTimeMillis();
	System.out.println("TestString10==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get("string100");
	dateEnd = System.currentTimeMillis();
	System.out.println("TestString100==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get("string1000");
	dateEnd = System.currentTimeMillis();
	System.out.println("TestString1000==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get("string10000");
	dateEnd = System.currentTimeMillis();
	System.out.println("TestString10000==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get(byte1Key);
	dateEnd = System.currentTimeMillis();
	System.out.println("Testbyte1Key==="+(dateEnd - dateBegin));
		
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get(byte10Key);
	dateEnd = System.currentTimeMillis();
	System.out.println("Testbyte10Key==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get(byte100Key);
	dateEnd = System.currentTimeMillis();
	System.out.println("Testbyte100Key==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get(byte1000Key);
	dateEnd = System.currentTimeMillis();
	System.out.println("Testbyte1000Key==="+(dateEnd - dateBegin));
	
	dateBegin = System.currentTimeMillis();
	for(int i =0;i<10000;i++)
		jedis.get(byte10000Key);
	dateEnd = System.currentTimeMillis();
	System.out.println("Testbyte10000Key==="+(dateEnd - dateBegin));
	
	jedis.quit();
}
}
}
