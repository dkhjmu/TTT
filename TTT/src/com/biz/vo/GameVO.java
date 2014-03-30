package com.biz.vo;

import java.util.ArrayList;
import java.util.HashMap;

public class GameVO {
	int seq;
	int game;
	IntVo[] bnus;
	int[] nums;
	
	public GameVO() {
		nums=new int[6];
	}
	
	public GameVO(int seq, int game, ArrayList<HashMap<String, String>> result) {
		this.seq=seq;
		this.game=game;
		nums=new int[6];
		for(int i=0;i<6;i++){
			HashMap<String, String> map = result.get(i);
			IntVo vo=new IntVo(map.get("BNU"));
			nums[i]=vo.getInt();
		}
	}
	
	public int[] getNumsArray() {
		return nums;
	}
	
	@Override
	public String toString() {
		String str=seq+"\t";
		for(int i=0;i<nums.length;i++){
			str+=(nums[i]+"\t");
		}
		return str;
	}
	

}
