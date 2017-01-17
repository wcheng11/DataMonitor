package edu.thss.monitor.rsp.service.parse.process;

import java.util.List;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.TempTreClass;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.relate.Temp2Treat;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.template.ITemplatePretreat;

/**
 * 模板解析预处理类
 * 
 * @author zhuangxy 2013-1-15
 */
public class TemplatePretreatProcessor {

	/**
	 * 日志记录
	 */
	// protected static Log logger = LogFactory
	// .getLog(TemplatePretreatProcessor.class);

	/**
	 * 预处理接口
	 */
	private ITemplatePretreat preTreater;

	/**
	 * 原始报文数据，用于存储预处理的中间结果
	 */
	private Object packetData;

	/**
	 * 模板解析预处理方法
	 * 
	 * @param packetData
	 *            带解析的原始报文数据
	 * @param template
	 *            报文对应的解析模板
	 * @return 预处理后的带解析的报文，若处理过程出现异常，返回null
	 */
	public Object processTreat(Object data, Template template)
			throws RSPException {

		this.packetData = data;

		LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "开始进行解析预处理...");

		// 获取模板的预处理方案
		// List<TempTreClass> treatList = template.getTreatList();

		// 获取模板到模板处理方案的关联记录，按照treatSequence升序排列
		List<Temp2Treat> temp2TreatList = template.getTemp2TreatList();

		if (temp2TreatList == null || temp2TreatList.size() == 0) {

			// 无预处理方案
			LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "模板"
					+ template.getTemplateName() + "没有预处理方案，预处理结束");
			return packetData;
		} else {

			// 模板处理方案
			TempTreClass ttc;

			// 模板处理相关的模板参数
			TemplatePara tp;

			// 模板处理参数
			String para;

			// 按顺序遍历处理方案
			for (Temp2Treat temp2Treat : temp2TreatList) {

				ttc = temp2Treat.getTemplateTreat();
				tp = temp2Treat.getTemplatePara();
				para = temp2Treat.getTreatParameter();
				LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "开始进行预处理: "
						+ ttc.getTreatName());

				this.initialTreater(ttc);

				// 本次处理是基于上次处理结果
				Object result = this.preTreater.process(this.packetData,
						template, tp, para);

				if (result != null) { // 返回结果无误
					this.packetData = result;
					LogRecord.debug(LogConstant.LOG_FLAG_PARSE
							+ ttc.getTreatName() + "预处理完成。");

				} else {
					throw new RSPException(LogConstant.LOG_FLAG_PARSE, template
							.getTemplateName(), "预处理过程出错，预处理方案："
							+ ttc.getTreatName());
				}

			}

		}

		return packetData;
	}

	/**
	 * 初始化预处理接口
	 * 
	 * @param ttc
	 *            预处理方案
	 * @throws RSPException
	 */
	private void initialTreater(TempTreClass ttc) throws RSPException {

		String treatName = ttc.getClassName();
		try {
			this.preTreater = (ITemplatePretreat) Class.forName(treatName)
					.newInstance();
		} catch (InstantiationException e) {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, treatName,
					"模板预处理错误：无法初始化模板处理类" + treatName);

		} catch (ClassNotFoundException e) {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, treatName,
					"模板预处理错误：无法找到模板处理类" + treatName);

		} catch (Exception e) {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, treatName,
					"模板预处理错误");
		}
	}

}
