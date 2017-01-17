package edu.thss.monitor.base.dataaccess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RedisTest {

	@Test
	public void test() {
//		Client client = new Client("166.111.80.207",6379);
		Jedis jedis = new Jedis("166.111.80.207",6379);
//		Transaction transaction = new Transaction(client);
		String string = jedis.lpop("UserIDDeviceIDWorkStatusID");
//		transaction.lpop("UserIDDeviceIDWorkStatusID").toString();
		System.out.println(string);
	}

}
