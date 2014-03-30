package com.biz.vo;

import java.util.ArrayList;

public class InBoxVo {
	private String name="";
	private ArrayList<String> list=null;
	
	public InBoxVo() {
	}
	
	public InBoxVo(String n) {
		setName(n);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void add(String in){
		if(list==null){
			list=new ArrayList<String>();
		}
		list.add(in);
	}
	
	public String getLine(){
		String line="";
		for(int i=0;i<list.size();i++){
			line=line+list.get(i)+"\t";
		}
		return line;
	}

	@Override
	public String toString() {
		return "InBoxVo [name=" + name + ", list=" + list + "]";
	}
	
}
