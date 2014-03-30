package com.biz.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommonLogger {
	SimpleDateFormat fmt=new SimpleDateFormat("[yyyy-MM-dd, hh:mm:ss] "); 
	int loglevel=1;
	
	public static final int DEBUG=4;
	public static final int INFO=5;
	public static final int ERROR=6;
	public static final int FATAL=7;
	
	public CommonLogger(int level){
		loglevel=level;
	}
	
	public CommonLogger(){
	}
	
	public void setLogLevel(int level){
		loglevel=level;
	}
	
	public void info(String message){
		if(loglevel<=INFO)
			System.out.println("[info] "+fmt.format(new Date(System.currentTimeMillis())) +" <"+ message+">");
	}
	
	public void infon(String message){
		if(loglevel<=INFO)
			System.out.print("[info] "+fmt.format(new Date(System.currentTimeMillis())) +" <"+ message+">");
	}
	
	public void infoln(String message){
		if(loglevel<=INFO)
			System.out.println(message);
	}
	
	public void infonn(String message){
		if(loglevel<=INFO)
			System.out.print(message);
	}
	
	public void debug(String message){
		if(loglevel<=DEBUG)
			System.out.println("[debug] "+fmt.format(new Date(System.currentTimeMillis())) +"<"+ message+">");
	}
	
	public void error(String message){
		if(loglevel<=ERROR)
			System.out.println("[error] "+fmt.format(new Date(System.currentTimeMillis())) +"<"+ message+">");
	}
	
	public void fatal(String message){
		if(loglevel<=FATAL)
			System.err.println("[fatal] "+fmt.format(new Date(System.currentTimeMillis())) +"<"+ message+">");
	}
	
	public static void debug(Object obj){
		if (obj instanceof ArrayList<?>) {
			ArrayList<?> alist =  (ArrayList<?>) obj;
			for (int i = 0; i < alist.size(); i++) {
				System.out.println(alist.get(i).toString());
			}
		}
	}
}
