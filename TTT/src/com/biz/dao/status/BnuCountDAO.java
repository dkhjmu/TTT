package com.biz.dao.status;

import com.biz.dao.AbstractBizDAO;

public class BnuCountDAO extends AbstractBizDAO{
	
	public int getBnuCount(int baseSeq, int gap){
		StringBuffer sb=new StringBuffer();
		int gapSeq=baseSeq-gap;
		sb.append(" select count(cnt) from ( \n");
		sb.append(" select bnu, count(*) cnt from basis_data \n");
		sb.append(" where seq between "+gapSeq+" and "+baseSeq+" \n");
		sb.append(" group by bnu) \n");
		
		String result=executeSingleRowFirstColumSelectSQL(sb.toString());
		int resultInt=0;
		if(result != null){
			try {
				resultInt=Integer.parseInt(result);
			} catch (Exception e) {
				resultInt=0;
			}
		}
		
		return resultInt;
	}
	
	public int getTragetSolv(int targetNumber) {
		//System.out.println(targetNumber);
		int i=1;
		for(;i<45;i++){
			int result=getBnuCount(targetNumber, i);
			//System.out.println(result);
			if(result >= 45){
				break;
			}
		}
		//System.out.println();
		return i;
	}
	
	public static void main(String[] args) {
		BnuCountDAO dao=new BnuCountDAO();
		for(int i=392;i>350;i--){
			System.out.println(i+"\t"+dao.getTragetSolv(i));
		}
		
	}
	
	
}
