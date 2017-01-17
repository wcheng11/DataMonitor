package demo.framework.storm_spring.bolt;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import demo.framework.storm_spring.service.IResponseService;

/**
 * Spring集成Bolt示例: 调用ResponseService的方法得到响应字符串，并打印出来
 */
public class SpringIntegrateBolt extends BaseRichBolt {

	private static final long serialVersionUID = -9101503428236294465L;
	
	protected static ApplicationContext springContext = 
		new ClassPathXmlApplicationContext(
				new String[]{"demo/framework/storm_spring/resource/beans-config.xml"}
		);;
	
	OutputCollector _collector;
	
	IResponseService responseService;
	
    @SuppressWarnings("unchecked")
	@Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        responseService = (IResponseService)springContext.getBean("responseService");
    }
    
    @Override
    public void execute(Tuple tuple) {
    	String name = (String)tuple.getValue(0); 
    	String responseStr = responseService.response(name);
    	System.out.println("=======>"+responseStr);
    }
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}