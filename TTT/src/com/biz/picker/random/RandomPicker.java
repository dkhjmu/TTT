package com.biz.picker.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.biz.analysis.TestAnalizer;
import com.biz.dao.status.BnuCountStatusBySEQDAO;
import com.biz.picker.AbstractPicker;
import com.biz.vo.IntVo;
import com.co.util.ArrayUtil;

public class RandomPicker extends AbstractPicker {

	HashMap<String, HashMap<String, HashMap<String, String>>> all;
	
	public RandomPicker() {
		BnuCountStatusBySEQDAO dao = new BnuCountStatusBySEQDAO();
		all = dao.getCountAllList();
	}
	
	@Override
	public int getSize() {
		return list.length;
	}

	@Override
	public int[] getSeq(int i) {
		return list[i];
	}

	@Override
	public int pick(int tryN, int seq) {
		HashMap<String, HashMap<String, String>> result = all.get((seq-1)+"");

		Iterator<String> iter = result.keySet().iterator();

		ArrayList<HashMap<String, String>> aVo = new ArrayList<HashMap<String, String>>();

		int[] abs={
				24,
				34,
				37,
				40,
				28,
				32,
				16,
				17,
				39,
				15,
				11,
				33,
				41,
				42,
		};
		while (iter.hasNext()) {
			String n = iter.next();
			HashMap<String, String> map = result.get(n);
//			IntVo vo = new IntVo(map.get("GAP"));
//			IntVo vo2 = new IntVo(map.get("CNT"));
//			if (vo.getInt() >= 1 && vo.getInt() <= 4) {
//				aVo.add(map);
//			} else if ((vo2.getInt() >= 3 && vo2.getInt() <= 5) || vo2.getInt() == 0) {
//			}
//			IntVo vos = new IntVo(map.get("CNTSUM"));
//			IntVo vos2 = new IntVo(map.get("CNTSUM2"));
//			if (vos.getInt() >= 0 && vos.getInt() <= 4 && vos2.getInt() >= 0 && vos2.getInt() <= 6) {
//				aVo.add(map);
//			}else{
//			}
			IntVo vo = new IntVo(map.get("BNU"));
			if(ArrayUtil.isIn(abs, vo.getInt())>0){
				aVo.add(map);
			}
		}
		
		TestAnalizer ta=new TestAnalizer();
		
		list=new int[tryN][6];
		for(int i=0;i<tryN;i++){
			int ss=0;
			int sum=0;
			int even=0;
			int[] rr=null;
			int[] mm=null;
			do{
				rr=getResultA(aVo);
				ss=rr[6];
				sum=rr[0]+rr[1]+rr[2]+rr[3]+rr[4]+rr[5];
				mm=ArrayUtil.minusPtn(rr);
				even=ArrayUtil.checkEven(rr);
				sum=countOver(mm, 10);
				
//				if(ss>19 || ss<7){
//					continue;
//				}else if(rr[7]>31 || rr[7] < 17){
//					continue;
//				}else if(rr[8]>57 || rr[8] < 37){
//					continue;
//				}else if(even == 6 || even == 0){
//					continue;
//				}else if(sum>3 || countOver(mm, 20) >1){
//					continue;
//				}else{
//					break;
//				}
				break;
				
			}while(true);
			int[] nb={rr[0],rr[1],rr[2],rr[3],rr[4],rr[5]};
//			int rpoint=ta.getMinusPP(nb)+ta.getBnuNormalPP(nb)+ta.getEvenPP(nb);
//			if(rpoint>21 && rpoint<26){
//				list[i] = nb;
//			}else{
//				i--;
//				continue;
//			}
			list[i] = nb;
		}
		
		return tryN;
	}
	
	private int countOver(int[] rr, int over){
		int sum=0;
		for(int i=0;i<rr.length;i++){
			if(rr[i]>over){
				sum++;
			}
		}
		return sum;
	}
	
	private static int[] getResultA(ArrayList<HashMap<String, String>> aVo) {
		ArrayList<HashMap<String, String>> aVoCopy=new ArrayList<HashMap<String,String>>(aVo);
		int leng=6;
		int sum=0;
		int sum2=0;
		int sum3=0;
		int[] resultA=new int[leng+3];
		for (int i = 0; i < leng; i++) {
			int rand = getRand(aVoCopy.size());
			HashMap<String, String> map = aVoCopy.get(rand);
			IntVo vo = new IntVo(map.get("BNU"));
			IntVo vo2 = new IntVo(map.get("CNT"));
			IntVo vo3 = new IntVo(map.get("CNT2"));
			IntVo vo4 = new IntVo(map.get("CNT3"));
			aVoCopy.remove(rand);
			resultA[i] = vo.getInt();
			sum+=(vo2.getInt());
			sum2+=(vo3.getInt());
			sum3+=(vo4.getInt());
		}
		resultA[6]=sum;
		resultA[7]=sum2;
		resultA[8]=sum3;
		return resultA;
	}

}
