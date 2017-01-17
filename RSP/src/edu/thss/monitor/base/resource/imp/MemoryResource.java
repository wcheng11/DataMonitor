package edu.thss.monitor.base.resource.imp;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.LogFactory;
import edu.thss.monitor.base.dataaccess.IBaseDAO;
import edu.thss.monitor.base.resource.IResource;
import edu.thss.monitor.base.resource.bean.ResourceInfo;
import edu.thss.monitor.base.resource.util.KryoUtil;
import edu.thss.monitor.base.resource.util.ResourceUtil;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;

/**
 * 内存资源类
 * @author yangtao
 */
public class MemoryResource implements IResource{

//	protected static Log logger = LogFactory.getLog(MemoryResource.class);
	
	/**
	 * 资源对象
	 */
	private Map<Object,Object> resource = new HashMap<Object,Object>();
	
	/**
	 * 资源对象信息
	 */
	private ResourceInfo resourceInfo;
	
	public MemoryResource(){}
	
	public MemoryResource(Map<Object,Object> resource){
		this.resource = resource;
	}
	
	public MemoryResource(ResourceInfo resourceInfo){
		this.resourceInfo = resourceInfo;
	}
	
	/**
	 * 加载资源
	 * @throws RSPException 
	 */
	@SuppressWarnings("unchecked")
	public void load() throws RSPException{
		//清空资源
		resource.clear();
		//从数据库读取资源并加载到内存中
		IBaseDAO baseDAO = (IBaseDAO)AppContext.getSpringContext().getBean("baseDAO");
		try {
			/*
			 * 全部加载
			 */
//			List<Object> rstLst = baseDAO.findList(resourceInfo.getJpql(),new Object[]{});
//			String getKeyMethodName = "get"+resourceInfo.getKeyAttr().substring(0,1).toUpperCase()+ resourceInfo.getKeyAttr().substring(1);
//			Method method = resourceInfo.getClassObj().getMethod(getKeyMethodName, new Class[]{});
//			for(Object obj:rstLst){
//				Object key = method.invoke(obj,new Object[]{});
//				KryoUtil.replacePersistentBag(obj); //替换PersistentBag
//				resource.put(key, obj);
//			}
//			//加载完毕
//			LogFactory.getLog(MemoryResource.class).info("[内存资源:"+resourceInfo.getResourceType()+"]加载资源项数量："+(rstLst==null?0:rstLst.size()));
			/*
			 * 分批加载
			 */
			int index=0;
			while(true){
//				LogFactory.getLog(MemoryResource.class).info(resourceInfo.getJpql());
//				LogFactory.getLog(MemoryResource.class).info(index+" ~ "+resourceInfo.getOnceLoadNum());
//				List<Object> rstLst = baseDAO.findList(resourceInfo.getJpql(), new Object[]{},index,resourceInfo.getOnceLoadNum());
				List<Object> rstLst = null;
				if(resourceInfo.getJpql()!=null){
					rstLst = baseDAO.findList(resourceInfo.getJpql(),  new Object[]{},index,resourceInfo.getOnceLoadNum());
				}else if(resourceInfo.getSql()!=null){
					rstLst = baseDAO.findListByNativeSql(resourceInfo.getSql(),index,resourceInfo.getOnceLoadNum());
				}
				//查询结果不为空则加载数据到内存中的全局变量resource
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
					//遍历并加入内存中的全局变量resource
					Iterator it = dataMap.entrySet().iterator();
					while(it.hasNext()){
						Entry<String,Object> entry = (Entry<String,Object>)it.next();
						resource.put(entry.getKey(), entry.getValue());
						
					 System.out.println(entry.getKey() + entry.getValue());
					 
					}
				}
				//加载完毕(取出条数大于单次取得数量)则跳出循环
				if(rstLst==null||rstLst.size()<resourceInfo.getOnceLoadNum()){
					LogFactory.getLog(MemoryResource.class).info("[内存资源:"+resourceInfo.getResourceType()+"]加载资源项数量："+(index+(rstLst==null?0:rstLst.size())));
					break;
				}else{
					index += resourceInfo.getOnceLoadNum();
				}
			}
		} catch (Exception e) {
			throw new RSPException("[内存资源:"+resourceInfo.getResourceType()+"]时发生异常",e);
		}
	}
	
	/**
	 * 获得资源项
	 * @param key - 资源项key
	 * @return
	 */
	public Object getResourceItem(Object key){
		return resource.get(key);
	}
	
	/**
	 * 添加资源项
	 * @param key - 资源项key
	 * @param value - 资源项值
	 */
	public void addResourceItem(Object key, Object value){
		//影响内存资源
		resource.put(key, value);
	}
	
	
	/**
	 * 删除资源项
	 * @param key - 资源项key
	 */
	public void deleteResourceItem(Object key){
		//影响内存资源
		resource.remove(key);
	}
	
	/**
	 * 更新资源项
	 * @param key - 资源项key
	 * @param value - 资源项值
	 */
	public void updateResourceItem(Object key, Object value){
		//影响内存资源
		resource.put(key, value);
	}

	@Override
	public Object getResource() {
		return resource;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getResourceKey(Object obj) {
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

	public void updateResource(Map<Object, Object> value) {
		resource.clear();
		resource.putAll(value);
	}
	
}
