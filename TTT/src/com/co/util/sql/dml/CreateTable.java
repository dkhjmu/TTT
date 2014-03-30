package com.co.util.sql.dml;

public class CreateTable {

	public static String getSQL(String table, String id[], String type[]){
		StringBuffer sb=new StringBuffer();
		sb.append(" CREATE TABLE "+table+"      \n");
		sb.append(" (                                     \n");
		int i=0;
		for(;i<id.length-1;i++){
			sb.append("     "+id[i]+"    "+type[i]+",                    \n");
		}
		sb.append("     "+id[i]+"    "+type[i]+"                    \n");
		sb.append(" )                                     \n"); 
		return sb.toString();
	}
	
	public static String getSQLWithPK(String table, String id[], String type[], String pk[]){
		StringBuffer sb=new StringBuffer();
		sb.append(" CREATE TABLE "+table+"      \n");
		sb.append(" (                                     \n");
		for(int i=0;i<id.length;i++){
			sb.append("     "+id[i]+"    "+type[i]+",                    \n");
		}
		if(pk!=null && pk.length !=0){
			int j=0;
			sb.append("      PRIMARY KEY ( ").append(pk[j++]);
			for(;j<pk.length;j++){
				sb.append(", ").append(pk[j]);
			}
			sb.append(" )                                     \n");
		}
		sb.append(" )                                     \n"); 
		return sb.toString();
	}
}