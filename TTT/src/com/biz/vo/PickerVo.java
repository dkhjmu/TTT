package com.biz.vo;

import java.util.ArrayList;
import java.util.Collections;

import com.co.util.ArrayUtil;

public class PickerVo {
	ArrayList<IntVo> bnuList;
	int size;
	public PickerVo() {
		bnuList=new ArrayList<IntVo>();
		size=6;
	}
	
	public PickerVo(int i) {
		bnuList=new ArrayList<IntVo>();
		size=i;
	}

	public PickerVo(PickerVo vo1) {
		bnuList=new ArrayList<IntVo>();
		int[] arr=vo1.getPickNum();
		for(int bnu : arr){
			this.add(bnu);
		}
	}

	public boolean add(int bnu){
		IntVo vo=new IntVo(bnu);
		if(ArrayUtil.isIn(bnuList, vo)>0){
			return false;
		}else{
			bnuList.add(vo);
			return true;
		}
	}
	
	public int[] getPickNum(){
		int[] result=new int[bnuList.size()];
		for(int i=0;i<bnuList.size();i++){
			result[i]=bnuList.get(i).getInt();
		}
		return result;
	}
	
	public int[] getPickNumOrdered(){
		Collections.sort(bnuList);
		return getPickNum();
	}

	public boolean isFull(){
		return bnuList.size()>=size;
	}
	
	public void add(String str) {
		add(new IntVo(str).getInt());
	}
}
