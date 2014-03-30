package com.biz.util;

public class CommonStrUtil {
	public static String getLinkedString(int[] gapps){
		String result="";
		for (int i = 0; i < gapps.length; i++) {
			if(gapps[i]<10){
				result=result+"0"+gapps[i];
			}else{
				result=result+gapps[i];
			}
			if(i<gapps.length-1){
				result=result+"-";
			}
		}
		return result;
	}
	public static String getLinkedString(String[] gapps){
		String result="";
		for (int i = 0; i < gapps.length; i++) {
			if(gapps[i].length()<2){
				result=result+"0"+gapps[i];
			}else{
				result=result+gapps[i];
			}
			if(i<gapps.length-1){
				result=result+"-";
			}
		}
		return result;
	}
	
	public static String getLinkedString(Object[] gapps){
		String result="";
		for (int i = 0; i < gapps.length; i++) {
			result=result+gapps[i];
			if(i<gapps.length-1){
				result=result+"-";
			}
		}
		return result;
	}

	public static String getCommaString(String[] nums) {
		String result="";
		for (int i = 0; i < nums.length; i++) {
			result=result+nums[i];
			if(i<nums.length-1){
				result=result+",";
			}
		}
		return result;
	}
	
	public static String getCommaString(int[] nums) {
		String result="";
		for (int i = 0; i < nums.length; i++) {
			result=result+nums[i];
			if(i<nums.length-1){
				result=result+",";
			}
		}
		return result;
	}
}
