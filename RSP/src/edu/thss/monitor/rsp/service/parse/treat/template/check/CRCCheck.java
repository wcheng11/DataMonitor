package edu.thss.monitor.rsp.service.parse.treat.template.check;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.relate.Tem2TemPara;
import edu.thss.monitor.rsp.service.parse.treat.template.ITemplatePretreat;

/**
 * 二进制报文的CRC校验实现类
 * @author zhuangxy
 * 2013-1-14
 */
public class CRCCheck implements ITemplatePretreat {
	
	/**
	 * 日志记录
	 */
//	protected static Log logger = LogFactory.getLog(CRCCheck.class);
	
	/**
	 * 需要校验的报文体
	 */
	private byte[] checkBody;
	
	/**
	 * 校验值
	 */
	private byte[] checkValue; //数据类型?

	@Override
	public Object process(Object data, Template template,
			TemplatePara tempPara, String treatPara) {
		
//		List<TemplatePara> paraList = template.getParameterList();
//		
//		if(!paraList.contains(tempPara)){
//			logger.error("模板中找不到校验相关的模板参数");
//			return null;
//		} 
		
		Tem2TemPara ttpRelate = null;
		List<Tem2TemPara> tem2TemParaList = template.getTem2TemParaList();
		for (Tem2TemPara tem2TemPara : tem2TemParaList) {
			if(tem2TemPara.getTemplatePara().equals(tempPara)){
				ttpRelate = tem2TemPara;
				break;
			}
		}
		
		if (ttpRelate == null) {
			LogRecord.error(LogConstant.LOG_FLAG_PROTOCOLRECONG + "找不到模板与模板参数的关联记录");
			return null;
		}
		
		byte[] packetData = (byte[]) data; 
		
		this.checkPrepare(packetData, ttpRelate);
		
		this.crcCheck();
		
		
		return null;
	}

	/**
	 * 校验预处理
	 * @param data
	 * @param ttpRelate
	 */
	private void checkPrepare(byte[] packetData, Tem2TemPara ttpRelate) {
		//TODO：1)根据偏移量和模板参数的长度获取校验值
		int offset = Integer.parseInt(ttpRelate.getOffset());
		
		
		if (offset >= 0) {
			
		} else {
			
		}
		
		//TODO：2)获取需要校验的报文体
		
	}

	/**
	 * 根据校验值对待校验数据体进行校验
	 */
	private void crcCheck(){
		// TODO：实现CRC校验
	}
	
	@Test
	public void testList(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		
		System.out.println(list);
		
		String test = "a";
		if (list.contains(test)) {
			System.out.println("success!");
		} else {
			System.out.println("fail!");
		}	
		
		System.out.println(Integer.parseInt("-1"));
	}
	
}
