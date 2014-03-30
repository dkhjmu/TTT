package com.biz.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class PointMatrixVO {
	HashMap<String, IntVo> countMap;
	int total;
	
	public PointMatrixVO() {
		countMap=new HashMap<String, IntVo>();
		total=0;
	}
	
	public void addBnu(int bnu){
		addBnu(new IntVo(bnu));
	}
	
	public void addBnu(IntVo bnu){
		addBnu(bnu.getInt()+"");
	}

	public void addBnu(String bnu) {
		IntVo vo=countMap.get(bnu);
		if(vo==null){
			countMap.put(bnu, new IntVo(1));
		}else{
			vo.add(1);
			countMap.put(bnu, vo);
		}
		total++;
	}
	
	public double getPoint(String value){
		IntVo vo = countMap.get(value);
		if(vo==null || total==0){
			return 0;
		}else{
			return Double.parseDouble(String.format("%.3f", (float)vo.getInt()/total));
		}
	}
	
	public void printAll(){
		Iterator<String> iter = countMap.keySet().iterator();
		ArrayList<PairVo> list=new ArrayList<PairVo>();
		while(iter.hasNext()){
			String key=iter.next();
			PairVo e=new PairVo(key, countMap.get(key));
			list.add(e);
		}
		Collections.sort(list);
		for(PairVo vo : list){
			System.out.println(vo.getKey()+"\t"+vo.getValue()+"\t"+getPoint(vo.getKey()));
		}
	}
}
