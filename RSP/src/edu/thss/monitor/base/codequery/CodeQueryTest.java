package edu.thss.monitor.base.codequery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import edu.thss.monitor.base.codequery.imp.CodeQuery;
import edu.thss.monitor.pub.dao.ICodeDAO;
import edu.thss.monitor.pub.entity.Code;
import edu.thss.monitor.pub.sys.AppContext;

public class CodeQueryTest {
	
	public static void main(String[] args){
		
		ICodeDAO dao = (ICodeDAO)AppContext.getSpringContext().getBean("codeDAO");
		
		Code newCode = new Code();
		newCode.setMajorCode(1);
		newCode.setSubcode(23);
		newCode.setName("第一个");
		
		dao.save(newCode);
		
		ICodeQuery query = new CodeQuery();
		List<Code> codes = query.getCodesByMajorCode(1);
		
		Iterator it =  codes.iterator();
		
		while(it.hasNext()){
			
			Object obj = it.next();
			if(obj instanceof Code){
				Code c = (Code)obj;
				System.out.println(c.getName());
			}
			
		}
	
		
	}

}
