package com.co.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.biz.vo.IntVo;

public class ArrayUtil {
	public static int[] sort(int[] iArray){
		Arrays.sort(iArray);
		return iArray;
	}
	
	public static int[] sortDesc(int[] iArray){
		Integer it[]=new Integer[iArray.length];
		for(int i=0;i<iArray.length;i++){
			it[i]=new Integer(iArray[i]);
		}
		Arrays.sort(it, Collections.reverseOrder());
		return iArray;
	}
	
	public static int[] minusPtn(int[] rr) {
		int[] result = new int[rr.length-1];
		for(int i=0;i<rr.length-1;i++){
			result[i] = rr[i+1]-rr[i];
		}
		return result;
	}
	
	public static int[] floorPtn(int[] nb){
		int[] ma=new int[nb.length];
		for(int j=0;j<nb.length;j++){
			//ma[j]=(int)(Math.floor(nb[j]/10));
			ma[j]=(int)(Math.floor((nb[j]-1)/15));
		}
		return ma;
	}
	
	public static int sumArray(int[] mm) {
		int sum=0;
		for(int i=0;i<mm.length;i++){
			sum+=mm[i];
		}
		return sum;
	}
	
	public static void print(int[] rr){
		for(int i=0;i<rr.length;i++){
			System.out.print(rr[i]+"\t");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		int [] aa ={3, 2, 1};
		aa=sort(aa);
		System.out.println(Arrays.toString(aa));
		System.out.println(sumArray(aa));
		aa=minusPtn(aa);
		System.out.println(Arrays.toString(aa));
	}

	public static int checkEven(int[] rr) {
		int sum=0;
		for(int i=0;i<rr.length;i++){
			if(rr[i]%2==0){
				sum++;
			}
		}
		return sum;
	}
	
	public static int isIn(int[] rr, int tt){
		int sum = 0;
		for(int j=0;j<rr.length;j++){
			if(rr[j]==tt){
				sum++;
			}
		}
		return sum;
	}

	public static int isIn(ArrayList<IntVo> list, IntVo vo) {
		int sum = 0;
		for(int j=0;j<list.size();j++){
			if(list.get(j).getInt()==vo.getInt()){
				sum++;
			}
		}
		return sum;
	}

}
