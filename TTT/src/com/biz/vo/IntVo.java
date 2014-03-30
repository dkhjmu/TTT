package com.biz.vo;

public class IntVo implements Comparable<IntVo> {
	private int num;

	public IntVo(int num) {
		this.num = num;
	}
	
	public IntVo(Object sNum) {
		try{
			this.num=Integer.parseInt(sNum.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public IntVo(String sNum) {
		try{
			this.num=Integer.parseInt(sNum);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getInt(){
		return num;
	}

	public boolean equals(IntVo obj){
		return this.num==obj.num;
	}
	
	public boolean equals(Object obj){
		return equals((IntVo)obj);
	}

	@Override
	public int hashCode() {
		return num;
	}
	
	@Override
	public String toString() {
		return num+"";
	}

	@Override
	public int compareTo(IntVo o) {
		return this.num-o.num;
	}
	
	public IntVo add(IntVo o){
		this.num=this.getInt()+o.getInt();
		return this;
	}

	public IntVo add(int i) {
		this.num=this.getInt()+i;
		return this;
	}
	
}
