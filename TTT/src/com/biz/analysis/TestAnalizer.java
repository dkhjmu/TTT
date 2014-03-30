package com.biz.analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.biz.base.dao.BasisDAO;
import com.biz.vo.IntVo;
import com.biz.vo.SeqResultVO;
import com.co.util.ArrayUtil;

public class TestAnalizer {
	
	public HashMap<String, IntVo> map;
	
	private List<SeqResultVO> result;
	private int maxSeq=0;
	public TestAnalizer() {
		BasisDAO dao=new BasisDAO();
		maxSeq=dao.getMaxSeq();
		System.out.println(maxSeq);
		result = dao.getAllResultVo();
		map=new HashMap<String, IntVo>();
	}
	
	public void analize(){
		for(int i=maxSeq-200;i<result.size();i++){
			SeqResultVO vo = result.get(i);
			//System.out.println(vo);
			int[] nb = vo.getNumsArrayNobonus();
			Arrays.sort(nb);
			//System.out.println(vo.toString());
			int pp=getBnuNormalSP(nb);
			int np=getMinusPP(nb);
			int ep=getEvenPP(nb);
			//System.out.println(vo.toString()+""+pp+"\t"+ep+"\t"+np);
			
		}
	}
	
	
	public int getEvenPP(int[] nb) {
		int evenCnt=ArrayUtil.checkEven(nb);
		if(evenCnt==3){
			return 10;
		}else{
			return 10-evenCnt;
		}
		//6, 8, 10
	}

	public int getBnuNormalSP(int[] nb) {
		int[] ma = ArrayUtil.floorPtn(nb);
		int[] rr={0,0,0,0,0}; //0,1,2,3,4
		int sum=0;
		for(int i=0;i<ma.length;i++){
			rr[ma[i]]++;
		}
		for(int j=0;j<rr.length;j++){
			if(rr[j]>3){
				sum-=1;
			}else if(rr[j]==3){
				sum+=1;
			}else if(rr[j]==2){
				sum+=2;
			}else if(rr[j]==1){
				sum+=2;
			}else if(rr[j]==0){
				sum-=1;
			}else{
				sum-=2;
			}
		}
		ArrayUtil.print(rr);
		
		String str = rr[0]+"-"+rr[1]+"-"+rr[2];
		if(map.get(str)==null){
			map.put(str, new IntVo(1));
		}else{
			map.get(str).add(1);
		}
		return sum;
	}
	
	
	public int getBnuNormalPP(int[] nb) {
		int[] ma = ArrayUtil.floorPtn(nb);
		int[] rr={0,0,0,0,0}; //0,1,2,3,4
		int sum=0;
		for(int i=0;i<ma.length;i++){
			rr[ma[i]]++;
		}
		for(int j=0;j<rr.length;j++){
			if(rr[j]>3){
				sum-=1;
			}else if(rr[j]==3){
				sum+=1;
			}else if(rr[j]==2){
				sum+=2;
			}else if(rr[j]==1){
				sum+=2;
			}else if(rr[j]==0){
				sum-=1;
			}else{
				sum-=2;
			}
		}
		//ArrayUtil.print(rr);
		return sum;
	}
	
	public int getMinusPP(int[] nb){
		Arrays.sort(nb);
		int[] ma = ArrayUtil.minusPtn(nb);
		ma = ArrayUtil.floorPtn(ma);
		int[] rr={0,0,0,0,0}; //0,1,2,3,4
		int sum=0;
		for(int i=0;i<ma.length;i++){
			rr[ma[i]]++;
		}
		sum+=(rr[0]*2);
		sum+=(rr[1]*1);
		if(rr[2]>=1){
			sum-=2;
		}
		if(rr[3]>=1){
			sum-=3;
		}
		if(rr[4]>=1){
			sum-=4;
		}
		//8, 9, 10
		return sum;
	}
	
/*  Result! */	
//	12	0
//	13	0
//	14	1
//	15	1
//	16	0
//	17	8
//	18	6
//	19	5
//	20	5
//	21	5
//	22	11
//	23	19
//	24	8
//	25	11
//	26	8
//	27	8
//	28	3
//	29	1
//	30	0
//	31	0
//	32	0

	public static void main(String[] args) {
		TestAnalizer ta=new TestAnalizer();
		ta.analize();
		ta.printmap();
	}

	public void printmap() {
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
			System.out.println(key+"\t"+map.get(key).getInt());
		}
	}
}
