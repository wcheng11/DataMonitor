package edu.thss.monitor.rsp.service.parse.process;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.ParaTreatClass;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.relate.TPara2Treat;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 数据转换执行类
 * @author zhuangxy
 * 2013-1-17
 */
public class TempValueTransformer {
	

	/**
	 * 数据转化接口
	 */
	private IDataTrans dataTranser;

	/**
	 * 转换后的数据
	 */
	private ParsedDataPacket transData;

	/**
	 * 基础信息Map
	 */
	private Map<String, String> baseInfoMap;
	
	/**
	 * 工况数据Map
	 */
	private Map<String, String> workStatusMap;

	public TempValueTransformer() {
		transData = new ParsedDataPacket();
	}
	
	/**
	 * 进行数据转换
	 * @param parsedData 转换前的数据
	 * @return 转换后的数据
	 * @throws RSPException
	 */
	public ParsedDataPacket transformTempValue(ParsedDataPacket parsedData)
			throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_TRANS + "正在进行数据转换...");
//		System.out.print(LogConstant.LOG_FLAG_TRANS + "正在进行数据转换...");	
		
		
		this.baseInfoMap = parsedData.getBaseInfoMap();
//		System.out.print("基本信息参数获取");
		this.workStatusMap = parsedData.getWorkStatusMap();
//		System.out.print("工况信息参数获取");
		
		//数据转化
		this.baseInfoMap = this.processTrans(baseInfoMap);
		this.workStatusMap = this.processTrans(workStatusMap);

		//数据封装
		this.transData.setBaseInfoMap(baseInfoMap);
		this.transData.setWorkStatusMap(workStatusMap);
		this.transData.setCommonKey(parsedData.getCommonKey());
		this.transData.setUniqueKey(parsedData.getUniqueKey());
		
//		System.out.print("数据转换完成..."+transData.getCommonKey().getParameterName());	
		
		return transData;

	}

	/**
	 * 对一个Map中的模板参数数据进行数据转换
	 * @param map 数据转换前的Map
	 * @return 数据转换后的Map
	 * @throws RSPException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> processTrans(Map<String, String> map) throws RSPException {
		
		List<TPara2Treat> treatList;
		TemplatePara para;
		
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			
//			System.out.print("数据转换的Key为："+key + "数据转换的value为："+value);

			//获取模板参数
			para = (TemplatePara) RegionalRC.getResourceItem(
					"templatePara", key);
			
			if (para == null) {
//				System.out.print(LogConstant.LOG_FLAG_TRANS+"数据转换错误，模板参数不存在");
				throw new RSPException(LogConstant.LOG_FLAG_TRANS+"数据转换错误，模板参数不存在");				
			}
			System.out.print("获取到的模板参数为::"+para.getParameterName()+para.getParameterID());
			
			//获取参数处理方案
			treatList = para.gettPara2TreatList();
			
//			if (treatList.size() >1) {
//				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//			}
			if (treatList == null || treatList.size() == 0) { //无处理方案
				LogRecord.debug(LogConstant.LOG_FLAG_TRANS+"参数" + para.getParameterID()+
						"无数据转换方案");
//				System.out.print(LogConstant.LOG_FLAG_TRANS+"参数" + para.getParameterID()+
//						"无数据转换方案");
				continue;
			}
			
			//执行处理方案
			for (TPara2Treat tPara2Treat : treatList) {
				
				ParaTreatClass ptc = tPara2Treat.getParaTreatClass();
//				System.out.print("获取的模板参数处理方案"+ptc.getTreatName()+"处理方案"+ptc.getClassName());
				
				try {
					LogRecord.debug(LogConstant.LOG_FLAG_TRANS+"参数" + para.getParameterID() + "正在进行" + ptc.getTreatName());
//					System.out.print(LogConstant.LOG_FLAG_TRANS+"参数" + para.getParameterID() + "正在进行" + ptc.getTreatName()+"转换");
					
					String className = ptc.getClassName();
					dataTranser = (IDataTrans) Class.forName(className)
							.newInstance();
				} catch (InstantiationException e) {
					throw new RSPException(LogConstant.LOG_FLAG_TRANS+"数据转换出错：无法初始化数据转换类" + ptc.getClassName());

				} catch (ClassNotFoundException e) {
					throw new RSPException(LogConstant.LOG_FLAG_TRANS+"数据转换出错：无法找到数据转换类" + ptc.getClassName());

				} catch (Exception e) {
					throw new RSPException(LogConstant.LOG_FLAG_TRANS+"数据转换出错");
				}
				
				value = dataTranser.trans(value);
//				System.out.print("转换的结果值为"+value);
				LogRecord.debug(LogConstant.LOG_FLAG_TRANS+"参数" + para.getParameterID() + "数据转换完成, 数据值：" + value);
//				System.out.print(LogConstant.LOG_FLAG_TRANS+"参数" + para.getParameterID() + "数据转换完成, 数据值：" + value);
			}
			
			//用转换后的值替换原有的值
			map.put(key, value);
		}
		
		return map;
	}

}
