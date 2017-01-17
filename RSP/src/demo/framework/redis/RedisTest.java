package demo.framework.redis;
import org.junit.Test;

import redis.clients.jedis.Jedis;


public class RedisTest {

	@Test
	public void testRedis() {
		// TODO Auto-generated method stub
		
		Jedis jedis = new Jedis("166.111.81.232",6379);
		jedis.set("li2", "lihubin2");
		System.out.println(jedis.get("li2"));
	}

}
