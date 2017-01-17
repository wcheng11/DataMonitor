package edu.thss.monitor.base.codequery.imp;

import edu.thss.monitor.base.codequery.ICodeQuery;
import edu.thss.monitor.pub.entity.Code;
import edu.thss.monitor.pub.sys.AppContext;
import edu.thss.monitor.pub.dao.ICodeDAO;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

/**
 * ICodeQuery的实现类
 * @author zhaokai
 *
 */
public class CodeQuery implements ICodeQuery{
	
	public ICodeDAO dao;
	
	public CodeQuery(){
		this.dao = (ICodeDAO)AppContext.getSpringContext().getBean("codeDAO");
	}
	
	public List<Code> getCodesByMajorCode(int majorCode){
		List<Code> codes = new ArrayList<Code>();
		String majorCodeValueString = new Integer(majorCode).toString();
		Map attrMap = new HashMap<String,String>();
		attrMap.put("majorCode", majorCodeValueString);
        List<Object> objs = dao.findByAttr(Code.class, attrMap);
        Iterator it = objs.iterator();
        while(it.hasNext()){
        	
        	Object obj = it.next();
        	if(obj instanceof Code){
        		codes.add((Code)obj);
        	}
        }
        return codes;		
		
	}
	public List<Integer> getSubCodesByCode(int majorCode){
		
		List<Code> codes = this.getCodesByMajorCode(majorCode);
		List<Integer> subcodes = new ArrayList<Integer>();
		Iterator it = codes.iterator();
		while(it.hasNext()){
			Code c = (Code)it.next();
			subcodes.add(c.getSubcode());
		}
		return subcodes;	
		
	}
	
	public List<String> getDescriptionsByMajorCode(int majorCode){
		
		List<Code> codes = this.getCodesByMajorCode(majorCode);
		List<String> descriptions = new ArrayList<String>();
		Iterator it = codes.iterator();
		while(it.hasNext()){
			Code c = (Code)it.next();
			descriptions.add(c.getName());
		}
		return descriptions;	
	}
	
	public List<String> getDescriptionsByCodes(int majorCode, int subCode){
		List<String> pltNames = new ArrayList<String>();
		List<Code> codes = this.getCodesByMajorCode(majorCode);
		Iterator it = codes.iterator();
		while(it.hasNext()){
			Code c = (Code)it.next();
			if(c.getSubcode()== subCode){
				pltNames.add(c.getName());
			}			
		}
		return pltNames;
	}

}
