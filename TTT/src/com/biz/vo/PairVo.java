package com.biz.vo;

public class PairVo implements Comparable<PairVo> {
	String key;
	IntVo value;
	
	public PairVo(String key, IntVo value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int compareTo(PairVo o) {
		return o.getValue().getInt()-value.getInt();
	}

	public IntVo getValue() {
		return this.value;
	}

	public String getKey() {
		return this.key;
	}
	
}
