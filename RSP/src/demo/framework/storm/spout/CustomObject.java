package demo.framework.storm.spout;

import java.util.ArrayList;

@SuppressWarnings("unchecked")

/**
 * 自定义类型CustomObject
 */
public class CustomObject {
	private int id;
	private String name;
	private ArrayList list;
	private CustomObject cobj;
	public CustomObject(){}
	public CustomObject(int id,String name,ArrayList list,CustomObject cobj){
		this.id = id;
		this.name = name;
		this.list = list;
		this.cobj = cobj;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public ArrayList getList() {
		return list;
	}
	public CustomObject getCobj() {
		return cobj;
	}
}