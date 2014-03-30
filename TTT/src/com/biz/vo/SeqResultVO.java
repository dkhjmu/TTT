package com.biz.vo;

import java.util.HashMap;

public class SeqResultVO {
	IntVo seq;
	IntVo[] bnus;
	int[] nums;
	
	public SeqResultVO(IntVo seq, IntVo[] bnus) {
		super();
		this.seq = seq;
		this.bnus = bnus;
	}
	
	public SeqResultVO(int seq, int no1, int no2, int no3, int no4, int no5, int no6, int bonus) {
		this.seq = new IntVo(seq);
		bnus=new IntVo[7];
		bnus[0] = new IntVo(no1);
		bnus[1] = new IntVo(no2);
		bnus[2] = new IntVo(no3);
		bnus[3] = new IntVo(no4);
		bnus[4] = new IntVo(no5);
		bnus[5] = new IntVo(no6);
		bnus[6] = new IntVo(bonus);
		int numN[]={no1, no2, no3, no4, no5, no6, bonus};
		this.nums = numN;
	}

	public SeqResultVO(HashMap<String, String> rmap) {
		this.seq = new IntVo(rmap.get("SEQ"));
		bnus=new IntVo[7];
		bnus[0] = new IntVo(rmap.get("NO1"));
		bnus[1] = new IntVo(rmap.get("NO2"));
		bnus[2] = new IntVo(rmap.get("NO3"));
		bnus[3] = new IntVo(rmap.get("NO4"));
		bnus[4] = new IntVo(rmap.get("NO5"));
		bnus[5] = new IntVo(rmap.get("NO6"));
		bnus[6] = new IntVo(rmap.get("BONUS"));
		
		int numN[]={
				bnus[0].getInt(),
				bnus[1].getInt(),
				bnus[2].getInt(),
				bnus[3].getInt(),
				bnus[4].getInt(),
				bnus[5].getInt(),
				bnus[6].getInt(),
		};
		this.nums = numN;
		
	}

	public IntVo getSeq() {
		return seq;
	}

	public void setSeq(IntVo seq) {
		this.seq = seq;
	}

	public IntVo[] getBnus() {
		return bnus;
	}

	public void setBnus(IntVo[] bnus) {
		this.bnus = bnus;
	}

	public int[] getNumsArray() {
		return nums;
	}
	
	public int[] getNumsArrayNobonus() {
		int numN[]={
				bnus[0].getInt(),
				bnus[1].getInt(),
				bnus[2].getInt(),
				bnus[3].getInt(),
				bnus[4].getInt(),
				bnus[5].getInt(),
		};
		return numN;
	}
	
	public int getBonus(){
		return bnus[6].getInt();
	}

	public void setNums(int[] nums) {
		this.nums = nums;
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
