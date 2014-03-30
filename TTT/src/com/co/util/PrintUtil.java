package com.co.util;

import java.util.ArrayList;

import com.biz.vo.IntVo;

public class PrintUtil {
	public static String printArray(ArrayList<IntVo> array){
		String str="";
		for(IntVo vo : array){
			str = str + vo.toString()+"\t";
			//System.out.print(vo.toString() + "\t" );
		}
		return str;
	}
	
	public static String printArray(ArrayList<IntVo> array, String comma){
		String str="";
		for(IntVo vo : array){
			str = str + vo.toString()+comma;
			//System.out.print(vo.toString() + "\t" );
		}
		return str;
	}
}
