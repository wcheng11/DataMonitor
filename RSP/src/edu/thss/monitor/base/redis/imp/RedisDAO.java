package edu.thss.monitor.base.redis.imp;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import redis.clients.jedis.Jedis;
import edu.thss.monitor.base.redis.IRedisDAO;
import edu.thss.monitor.base.resource.bean.ResourceConstant;
import edu.thss.monitor.base.resource.util.KryoUtil;
/**
 * RedisDAO实现类
 * @author huyang
 */
public class RedisDAO implements IRedisDAO {

	private RedisConnPool redisConnPool;
	
//	private Jedis jedis;
	
	public void setRedisConnPool(RedisConnPool redisConnPool) {
		this.redisConnPool = redisConnPool;
	}

	@Override
	public void addData(int tableIndex, String objKey,Map<String, String> objValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.hmset(objKey, objValue);
		redisConnPool.releaseConn(jedis);
	}
	
	@Override
	public void addData(int tableIndex,String dataKey,Set<String> dataValue){
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		for(String data:dataValue)
			jedis.sadd(dataKey, data);
		redisConnPool.releaseConn(jedis);
	}
	
	@Override
	public void addData(int tableIndex, String objKey,String objValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.set(objKey, objValue);
		redisConnPool.releaseConn(jedis);
	}

	@Override
	public void addData(int tableIndex, String dataKey, Object dataValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		byte[] serilizeByte = KryoUtil.serialize2byte(dataValue);
		jedis.set(dataKey.getBytes(),serilizeByte);
		redisConnPool.releaseConn(jedis);
	}
	
	@Override
	public void clearTable(int tableIndex) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.flushDB();
		redisConnPool.releaseConn(jedis);
	}

	@Override
	public void deleteData(int tableIndex, String dataKey) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.del(dataKey);
		redisConnPool.releaseConn(jedis);
	}
	
	@Override
	public Map<String,String> getMapData(int tableIndex, String dataKey) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		Map<String, String> rst = jedis.hgetAll(dataKey);
		redisConnPool.releaseConn(jedis);
		if(rst.isEmpty()==true)
		return null;
		else
			return rst;
	}
	
	@Override
	public String getStringData(int tableIndex, String dataKey) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		String rst = jedis.get(dataKey);
		redisConnPool.releaseConn(jedis);
		return rst;
	}
	
	@Override
	public Set<String> getSetData(int tableIndex, String dataKey) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		Set<String> rst = jedis.smembers(dataKey);
		redisConnPool.releaseConn(jedis);
		if(rst.isEmpty()==true)
			return null;
		else
		return rst;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getObjectData(int tableIndex, String dataKey,Class clz) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		byte[] rst = jedis.get(dataKey.getBytes());
		redisConnPool.releaseConn(jedis);
		if(rst!=null)
			return KryoUtil.deserialize(rst, clz);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadTable(int tableIndex,String dataType,Object tableData) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		Iterator it = ((Map<String,Object>)tableData).entrySet().iterator();
		if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_MAP)){
			while(it.hasNext()){
				Entry<String, Map<String, String>> entry = (Entry<String, Map<String, String>>)it.next();
				jedis.hmset(entry.getKey(),entry.getValue());
			}
		}else if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_SET)){
			while(it.hasNext()){
				Entry<String, Set<String>> entry = (Entry<String, Set<String>>)it.next();
				Iterator subIt = entry.getValue().iterator();
				while(subIt.hasNext()){
					jedis.sadd(entry.getKey(),(String)subIt.next());
				}
			}
		}else if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_STRING)){
			while(it.hasNext()){
				Entry<String, String> entry = (Entry<String, String>)it.next();
				jedis.set(entry.getKey(),entry.getValue());
			}
		}else if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_OBJECT)){
			while(it.hasNext()){
				Entry<String, Object> entry = (Entry<String, Object>)it.next();
				byte[] serilizeByte = KryoUtil.serialize2byte(entry.getValue());
				jedis.set(entry.getKey().getBytes(),serilizeByte);
			}
		}
		redisConnPool.releaseConn(jedis);
	}

	@Override
	public void updateData(int tableIndex, String objKey,Map<String, String> objValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.del(objKey);
		jedis.hmset(objKey, (Map<String,String>)objValue);
		redisConnPool.releaseConn(jedis);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateData(int tableIndex, String objKey, Set<String> objValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.del(objKey);
		Iterator it = objValue.iterator();
		while(it.hasNext()){
			jedis.sadd(objKey,(String)it.next());
		}
		redisConnPool.releaseConn(jedis);
	}

	@Override
	public void updateData(int tableIndex, String objKey, String objValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.del(objKey);
		jedis.set(objKey,objValue);
		redisConnPool.releaseConn(jedis);
	}

	@Override
	public void updateData(int tableIndex, String objKey, Object objValue) {
		Jedis jedis = redisConnPool.getConn();
		jedis.select(tableIndex);
		jedis.del(objKey);
		byte[] serilizeByte = KryoUtil.serialize2byte(objValue);
		jedis.set(objKey.getBytes(),serilizeByte);
		redisConnPool.releaseConn(jedis);
	}


//	@Override
//	public void updateData(int tableIndex,String dataType, String objKey,Object objValue) {
//		jedis = redisConnPool.getConn();
//		jedis.select(tableIndex);
//		jedis.del(objKey);
//		if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_MAP)){
//			jedis.hmset(objKey, (Map<String,String>)objValue);
//		}else if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_SET)){
//			Iterator it = ((Set<String>)objValue).iterator();
//			while(it.hasNext()){
//				jedis.sadd(objKey,(String)it.next());
//			}
//		}else if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_STRING)){
//			jedis.set(objKey,(String)objValue);
//		}else if(dataType.equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_OBJECT)){
//			byte[] serilizeByte = KryoUtil.serialize2byte(objValue);
//			jedis.set(objKey.getBytes(),serilizeByte);
//		}
//		redisConnPool.releaseConn(jedis);
//	}

}
