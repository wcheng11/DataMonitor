package edu.thss.monitor.base.resource.imp;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.LogFactory;

import edu.thss.monitor.base.dataaccess.IBaseDAO;
import edu.thss.monitor.base.redis.IRedisDAO;
import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.bean.ResourceConstant;
import edu.thss.monitor.base.resource.bean.ResourceInfo;
import edu.thss.monitor.base.resource.util.KryoUtil;
import edu.thss.monitor.base.resource.util.ResourceUtil;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;
/**
 * Redis资源类
 * @author yangtao
 */
@SuppressWarnings("unchecked")
public class RedisResource implements IResource {

//	protected static Log logger = LogFactory.getLog(RedisResource.class);
	
	/**
	 * 当前资源的Redis表索引
	 */
	private Integer tableIndex;
	
	/**
	 * 当前资源信息
	 */
	private ResourceInfo resourceInfo;
	
	private IRedisDAO redisDAO;

	public RedisResource(){}
	
	public RedisResource(int tableIndex){
		this.tableIndex = tableIndex;
		redisDAO = (IRedisDAO)AppContext.getSpringContext().getBean("redisDAO");
	}
	
	public RedisResource(ResourceInfo resourceInfo){
		this.resourceInfo = resourceInfo;
		this.tableIndex = this.resourceInfo.getTableIndex();
		redisDAO = (IRedisDAO)AppContext.getSpringContext().getBean("redisDAO");
	}

	@Override
	public void load() throws RSPException {
		//清空资源
		redisDAO.clearTable(tableIndex);
		//从数据库读取资源并加载到redis中(逐次加载)
		System.out.print("从数据库读取资源并加载到redis中(逐次加载)");
		IBaseDAO baseDAO = (IBaseDAO)AppContext.getSpringContext().getBean("baseDAO");
		try {
			/*
			 * 全部加载
			 */
//			List<Object> rstLst = null;
//			if(resourceInfo.getJpql()!=null){
//				rstLst = baseDAO.findList(resourceInfo.getJpql(),  new Object[]{});
//			}else if(resourceInfo.getSql()!=null){
//				rstLst = baseDAO.findListByNativeSql(resourceInfo.getSql());
//			}
//			//查询结果不为空则加载数据到Redis
//			if(rstLst!=null&&rstLst.size()>0){
//				for(Object obj:rstLst){
//					KryoUtil.replacePersistentBag(obj); //替换PersistentBag
//				}
//				if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_MAP)){
//					if(resourceInfo.getJpql()!=null){
//						loadMapDataFromEntity(rstLst);
//					}else if(resourceInfo.getSql()!=null){
//						loadMapDataFromObjArray(rstLst);
//					}
//				}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_STRING)){ //String类型可能从JPQL或SQL加载
//					if(resourceInfo.getJpql()!=null){
//						loadStringDataFromEntity(rstLst);
//					}else if(resourceInfo.getSql()!=null){
//						loadStringDataFromObjArray(rstLst);
//					}
//				}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_SET)){//Set类型可能从JPQL或SQL加载
//					if(resourceInfo.getJpql()!=null){
//						loadSetDataFromEntity(rstLst);
//					}else if(resourceInfo.getSql()!=null){
//						loadSetDataFromObjArray(rstLst);
//					}
//				}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_OBJECT)){
//					loadObjectData(rstLst);
//				}else{
//					throw new RSPException("redis资源只有四种数据类型：string | map | set | object, 没有"+resourceInfo.getDataType());
//				}
//			}
//			//加载完毕(取出条数大于单次取得数量)则跳出循环
//			LogFactory.getLog(MemoryResource.class).info("[Redis资源:"+resourceInfo.getResourceType()+"]加载资源项数量："+(rstLst==null?0:rstLst.size()));

			/*
			 * 分批加载
			 */
			int index=0;
			while(true){
//				LogFactory.getLog(MemoryResource.class).info(resourceInfo.getJpql());
//				LogFactory.getLog(MemoryResource.class).info(index+" ~ "+resourceInfo.getOnceLoadNum());
				List<Object> rstLst = null;
				if(resourceInfo.getJpql()!=null){
					rstLst = baseDAO.findList(resourceInfo.getJpql(),  new Object[]{},index,resourceInfo.getOnceLoadNum());
				}else if(resourceInfo.getSql()!=null){
					rstLst = baseDAO.findListByNativeSql(resourceInfo.getSql(),index,resourceInfo.getOnceLoadNum());
				}
				//查询结果不为空则加载数据到Redis
				if(rstLst!=null&&rstLst.size()>0){
					//替换PersistentBag，解决资源对象传输后懒加载异常的问题
					for(Object obj:rstLst){
						KryoUtil.replacePersistentBag(obj); //
					}
					//将查询结果集合转换为资源数据Map
					Map<String,Object> dataMap = ResourceUtil.getData(rstLst, resourceInfo);
					
					//进行后处理
//					if(resourceInfo.getReprocessClass()!=null){
//						IReprocess repro = (IReprocess) Class.forName(resourceInfo.getReprocessClass()).newInstance();
//						repro.reprocess(dataMap);
//					}
					//遍历并加入Redis
					if(dataMap!=null&&dataMap.size()>0){
						redisDAO.loadTable(tableIndex,resourceInfo.getDataType(),dataMap);
						System.out.print("资源信息："+resourceInfo.getValueAttr()+resourceInfo.getKeyAttr());
					}
						
				}
				//加载完毕(取出条数大于单次取得数量)则跳出循环
				if(rstLst==null||rstLst.size()<resourceInfo.getOnceLoadNum()){
					LogFactory.getLog(RedisResource.class).info("[Redis资源:"+resourceInfo.getResourceType()+"]加载资源项数量："+(index+(rstLst==null?0:rstLst.size())));
					System.out.print("[Redis资源:"+resourceInfo.getResourceType()+"]加载资源项数量：");
					break;
				}else{
					index += resourceInfo.getOnceLoadNum();
				}
			}
		} catch (Exception e) {
			System.out.print("加载[Redis资源:"+resourceInfo.getResourceType()+"]时发生异常");
			throw new RSPException("加载[Redis资源:"+resourceInfo.getResourceType()+"]时发生异常",e);
		}
	}
	
	@Override
	public Object getResourceItem(Object key) {
		if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_MAP)){
			return redisDAO.getMapData(tableIndex, (String)key);
		}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_STRING)){
			return redisDAO.getStringData(tableIndex, (String)key);
		}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_SET)){
			return redisDAO.getSetData(tableIndex, (String)key);
		}else if(resourceInfo.getDataType().equals(ResourceConstant.CONFIG_RESOURCE_DATATYPE_OBJECT)){
			return redisDAO.getObjectData(tableIndex, (String)key,resourceInfo.getClassObj());
		}
		return null;
	}
	
	@Override
	public void addResourceItem(Object key, Object value) {
		//影响Redis
		if(value instanceof Map){
			redisDAO.addData(tableIndex, (String)key,(Map<String,String>)value);
		}else if(value instanceof Set){
			redisDAO.addData(tableIndex, (String)key,(Set<String>)value);
		}else if(value instanceof String){
			redisDAO.addData(tableIndex, (String)key,(String)value);
		}else{
			redisDAO.addData(tableIndex, (String)key,value);
		}
	}
	
	@Override
	public void deleteResourceItem(Object key) {
		//影响Redis
		redisDAO.deleteData(tableIndex, (String)key);
	}

	@Override
	public void updateResourceItem(Object key, Object value) {
		//影响Redis
		if(value instanceof Map){
			redisDAO.updateData(tableIndex, (String)key,(Map<String,String>)value);
		}else if(value instanceof Set){
			redisDAO.updateData(tableIndex, (String)key,(Set<String>)value);
		}else if(value instanceof String){
			redisDAO.updateData(tableIndex, (String)key,(String)value);
		}else{
			redisDAO.updateData(tableIndex, (String)key,value);
		}
	}

	@Override
	public Object getResource() {
		return resourceInfo;
	}

	@Override
	public Object getResourceKey(Object obj){
		String getKeyMethodName = "get"+resourceInfo.getKeyAttr().substring(0,1).toUpperCase()+ resourceInfo.getKeyAttr().substring(1);
		try {
			Method method = resourceInfo.getClassObj().getMethod(getKeyMethodName, new Class[]{});
			Object key = method.invoke(obj,new Object[]{});
			return key;
		} catch (Exception e) {
			new RSPException("从对象获得资源项key时发生异常!",e);
			return null;
		}
	}
}
