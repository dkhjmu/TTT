package com.biz.picker;

public abstract class AbstractPicker {
	protected int[][] list;
	public int getSize() {
		return list.length;
	}
	
	public static int getRand(int max) {
		int rand = (int) (Math.floor(Math.random() * 100) % max);
		return rand;
	}
	
	public abstract int[] getSeq(int i);
	public abstract int pick(int tryN, int seq);
}
