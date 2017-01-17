package edu.thss.monitor.base.resource.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.thss.monitor.pub.exception.RSPException;
@SuppressWarnings("unchecked")
public class XmlReaderUtil {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws UnsupportedEncodingException 
	 * @throws RSPException 
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws UnsupportedEncodingException, RSPException{
		String xmlPath = System.getProperty("user.dir")+"\\bin\\META-INF\\resource-config.xml";;
		String path = new String(xmlPath.getBytes("iso-8859-1"),"utf-8");
		XmlNode xn = readXML(xmlPath);
		System.out.print("xx");
	}
	
	public static XmlNode readXML(String xmlPath) throws RSPException{
		SAXReader reader = new SAXReader();
		Document document = null;
    	try {
			document = reader.read(new File(xmlPath));
    	} catch (DocumentException e) {
			throw new RSPException("在该路径（"+xmlPath+"）下未找到配置文件",e);
		}
    	//解析根节点
		Element root = document.getRootElement(); 
		XmlNode rootNode = new XmlNode(root.getName(),root.getText());
		setAllAttr(rootNode,root);
		setSubNode(root,rootNode);
    	return rootNode;
	}
	
	private static void setSubNode(Element el,XmlNode elNode){
		List nodeLst = el.elements();   
		for (Iterator it_n = nodeLst.iterator(); it_n.hasNext();) {
			Element e = (Element) it_n.next(); 
			XmlNode eNode = new XmlNode(e.getName(),e.getText());
			setAllAttr(eNode,e);
			setSubNode(e,eNode);
			elNode.addSubNode(eNode);
		}
	}
	
	public static void setAllAttr(XmlNode node,Element elm){
		List attrLst = elm.attributes();
		for (Iterator it = attrLst.iterator(); it.hasNext();) {  
			Attribute attr = (Attribute) it.next();   
			node.addAttr(attr.getName(),attr.getText());
		} 
	}
	
	public static class XmlNode{
		
		
		private String tagName; //标签

		private String text; //文本

		private Map attrMap = null;
		
		private List<XmlNode> subNodeLst = null;

		private Set<String> subNodeTypeSet = null;
		
		public XmlNode(){}
		
		public Set<String> getSubNodeTypeSet() {
			return subNodeTypeSet;
		}

		public XmlNode(String tagName,String text){
			this.tagName = tagName;
			this.text = text;
		}
		
		public void addAttr(String attrName,String attrVle){
			if(attrMap==null){
				attrMap = new LinkedHashMap();
			}
			attrMap.put(attrName, attrVle);
		}
		
		public void addSubNode(XmlNode node){
			if(subNodeLst==null){
				subNodeLst = new ArrayList();
				subNodeTypeSet = new HashSet<String>();
			}
			subNodeLst.add(node);
			subNodeTypeSet.add(node.getTagName());
		}
		
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTagName() {
			return tagName;
		}

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public List<XmlNode> getSubNodeLst() {
			return subNodeLst;
		}

		public Map getAttrMap() {
			return attrMap;
		}
	}
	
}
