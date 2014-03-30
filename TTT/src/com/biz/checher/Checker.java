package com.biz.checher;

import java.util.List;

import com.biz.base.dao.BasisDAO;
import com.biz.picker.AbstractPicker;
import com.biz.picker.random.PorderGapComboPicker;
import com.biz.picker.random.RandomPicker;
import com.biz.vo.SeqResultVO;
import com.co.util.ArrayUtil;

public class Checker {
	public static int checkResult(int result[], int right[], int bonus){
		int vv=0;
		for(int i=0;i<right.length;i++){
			for(int j=0;j<result.length;j++){
				if(right[i]==result[j]){
					vv++;
				}
			}
		}
		
		if(vv==5){
			for(int j=0;j<result.length;j++){
				if(bonus==result[j]){
					return 7;
				}
			}
		}
		
		return vv;
	}
	
	public static int[] check(AbstractPicker picker, int tryN, int seq, int rightA[], int bonus){
		picker.pick(tryN, seq);
		int rrr[]={0,0,0,0,0,0,0,0};
		for (int i = 0; i < picker.getSize(); i++) {
			int[] resultA = picker.getSeq(i);
			ArrayUtil.print(resultA);
			ArrayUtil.print(rightA);
			int pv=Checker.checkResult(resultA, rightA, bonus);
			rrr[pv]++;
//			if (pv >= 5) {
//				System.out.println("right:");
//				printArray(rightA);
//				System.out.println("input:");
//				printArray(resultA);
//			}
		}
		return rrr;		
	}
	
	public static void printArray(int[] resultA){
		for(int i=0;i<resultA.length;i++){
			System.out.print(resultA[i]+"\t");
		}
		System.out.println();
	}
	
	public static void moneySum(int[] result, int input, int gets){
		for(int i=0;i<result.length;i++){
			System.out.print(result[i]+"\t");
		}
		int sum=getSum(result);
		String ss ="";
		if(input>gets){
			ss ="DN";
		}else{
			ss ="UP";
		}
		System.out.println("SUM:"+sum+"\t"+ss);
		try{
			Thread.sleep(33);
		}catch(Exception e){
		}
	}
	
	public static int getSum(int[] result) {
		return result[3]*5000+result[4]*50000+result[5]*1200000+result[6]*100000000+result[7]*50000000;
	}
	
	public static void simulating(AbstractPicker picker) {
		BasisDAO dao=new BasisDAO();
		System.out.println(dao.getMaxSeq());
		List<SeqResultVO> result = dao.getAllResultVo();
		int suc=0;
		int input=0;
		int gets=0;
		int tryN = 10;
		int startI = dao.getMaxSeq()-5;
		int[] total={0,0,0,0,0,0,0,0};
		for(int i=startI;i<result.size();i++){
			System.out.println("seq:"+(i+1)+" !! ");
			int rrr[]=Checker.check(picker, tryN, i+1, result.get(i).getNumsArrayNobonus(), result.get(i).getBonus());
			input=input+(tryN*1000);
			gets+=Checker.getSum(rrr);
			if(Checker.getSum(rrr)>(tryN*1000)){
				suc++;
			}
			Checker.moneySum(rrr, input, gets);
			total[0]+=rrr[0];
			total[1]+=rrr[1];
			total[2]+=rrr[2];
			total[3]+=rrr[3];
			total[4]+=rrr[4];
			total[5]+=rrr[5];
			total[6]+=rrr[6];
			total[7]+=rrr[7];
		}
		System.out.println("##################");
		moneySum(total, input, gets);
		System.out.println("SUC:"+suc);
		System.out.println("input:"+input);
		System.out.println("gets :"+gets);
		System.out.println("gap  :"+(gets-input));
	}
	

	public static void main(String[] args) {
//		RandomPicker picker=new RandomPicker();
//		Checker.simulating(picker);
		
//		PtnNumPicker picker2=new PtnNumPicker();
//		Checker.simulating(picker2);
		
		PorderGapComboPicker picker3=new PorderGapComboPicker();
		Checker.simulating(picker3);
		
	}
}
