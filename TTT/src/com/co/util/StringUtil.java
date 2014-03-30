package com.co.util;

import java.util.HashMap;

import com.biz.dao.status.BnuCountStatusBySEQDAO;
import com.biz.vo.InBoxVo;

public class StringUtil {
	public static void printAllMap(HashMap<String, HashMap<String, HashMap<String, String>>> map, int seq, int maxSeq){
		String line="SEQ";
		for(int i=1;i<46;i++){
			line=line + "\t"+i;
		}
		System.out.println(line);
		for(int start = maxSeq;start >= seq;start--){
			line="";
			HashMap<String, HashMap<String, String>> bnuMap = map.get(start+"");
			line=line+start+"\t";
			for(int i=1;i<46;i++){
				HashMap<String, String> rMap = bnuMap.get(i+"");
				String cnt=rMap.get("CNT");
				String chec=rMap.get("CHEC");
				if(chec.equals("0")){
					line=line + cnt + "\t";
				}else{
					//line=line + cnt + "(" + chec + ")\t";
					line=line + cnt + "(" + rMap.get("GAP") + ")\t";
				}
			}
			System.out.println(line);
		}
	}//printAllMap
	
	public static void printCountMap(HashMap<String, HashMap<String, String>> map){
		HashMap<String, InBoxVo> result=new HashMap<String, InBoxVo>();
		for(int i=1;i<46;i++){
			String key = map.get(i+"").get("CNT");
			String bnu = "";
			if(map.get(i+"").get("CHEC").equals("0")){
				bnu=map.get(i+"").get("BNU");
			}else{
				bnu=map.get(i+"").get("BNU")+"("+map.get(i+"").get("CHEC")+")";
			}
			InBoxVo v = result.get(key);
			if(v==null){
				v=new InBoxVo(key);
			}
			v.add(bnu);
			result.put(key, v);
		}
		
		int maxCnt = getMaxCnt(map);
		for(int k=maxCnt;k>=0;k--){
			System.out.println(k+"\t"+result.get(k+"").getLine());
		}
	}
	
	public static int getMaxCnt(HashMap<String, HashMap<String, String>> map){
		int max=0;
		for(int i=1;i<46;i++){
			int pp=Integer.parseInt(map.get(i+"").get("CNT"));
			if(pp>max){
				max=pp;
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		BnuCountStatusBySEQDAO dao = new BnuCountStatusBySEQDAO();
		printAllMap(dao.getCountAllList(), 302, 392);
		
		//printCountMap(dao.getCountAllList().get("392"));
	}
}
