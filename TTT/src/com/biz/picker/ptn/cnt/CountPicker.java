package com.biz.picker.ptn.cnt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.biz.dao.status.BnuCountStatusBySEQDAO;
import com.biz.picker.AbstractPicker;
import com.biz.vo.IntVo;
import com.co.util.PrintUtil;

public class CountPicker extends AbstractPicker {
	
	private ArrayList<IntVo> cnt0; 
	private ArrayList<IntVo> cnt1; 
	private ArrayList<IntVo> cnt2; 
	private ArrayList<IntVo> cnt3;
	private ArrayList<IntVo> cnt4;
	private ArrayList<IntVo> cnt5;
	private ArrayList<IntVo> cnt6;
	private ArrayList<IntVo> cntOut;
	
	public CountPicker(){
		cnt0=new ArrayList<IntVo>();
		cnt1=new ArrayList<IntVo>();
		cnt2=new ArrayList<IntVo>();
		cnt3=new ArrayList<IntVo>();
		cnt4=new ArrayList<IntVo>();
		cnt5=new ArrayList<IntVo>();
		cnt6=new ArrayList<IntVo>();
		cntOut=new ArrayList<IntVo>();
	}

	@Override
	public int[] getSeq(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pick(int tryN, int seq) {
		// TODO Auto-generated method stub
		pick(seq);
		
		return 0;
	}
	
	
	public HashMap<String, HashMap<String, String>> tresh(int seq){
		BnuCountStatusBySEQDAO dao = new BnuCountStatusBySEQDAO();
		HashMap<String, HashMap<String, HashMap<String, String>>> all = dao.getCountAllList();
		HashMap<String, HashMap<String, String>> result = all.get(seq+"");

		Iterator<String> iter = result.keySet().iterator();

		HashMap<String, HashMap<String, String>> aVo = new HashMap<String, HashMap<String, String>>();

		while (iter.hasNext()) {
			String n = iter.next();
			HashMap<String, String> map = result.get(n);
			IntVo vo = new IntVo(map.get("GAP"));
			IntVo vo2 = new IntVo(map.get("CNT"));
			if (vo.getInt() >= 1 && vo.getInt() <= 4) {
				aVo.put(n, map);
			} else if ((vo2.getInt() >= 3 && vo2.getInt() <= 5) || vo2.getInt() == 0) {
				aVo.put(n, map);
			}
		}
		
		return aVo;
	}
	
	public void pick(int seq) {
		HashMap<String, HashMap<String, String>> result = tresh(seq);

		Iterator<String> iter = result.keySet().iterator();

		while (iter.hasNext()) {
			String n = iter.next();
			HashMap<String, String> map = result.get(n);
			IntVo bnu = new IntVo(n );
			IntVo vo = new IntVo(map.get("CNT"));
			switch (vo.getInt()) {
			case 0:
				cnt0.add(bnu);
				break;
			case 1:
				cnt1.add(bnu);
				break;
			case 2:
				cnt2.add(bnu);
				break;
			case 3:
				cnt3.add(bnu);
				break;
			case 4:
				cnt4.add(bnu);
				break;
			case 5:
				cnt5.add(bnu);
				break;
			case 6:
				cnt6.add(bnu);
				break;
			default:
				cntOut.add(bnu);
				break;
			}
		}
		
		System.out.println("##########################################");
		System.out.println(" 0: "+cnt0.size()+"\t " + PrintUtil.printArray(cnt0));
		System.out.println(" 1: "+cnt1.size()+"\t " + PrintUtil.printArray(cnt1));
		System.out.println(" 2: "+cnt2.size()+"\t " + PrintUtil.printArray(cnt2));
		System.out.println(" 3: "+cnt3.size()+"\t " + PrintUtil.printArray(cnt3));
		System.out.println(" 4: "+cnt4.size()+"\t " + PrintUtil.printArray(cnt4));
		System.out.println(" 5: "+cnt5.size()+"\t " + PrintUtil.printArray(cnt5));
		System.out.println(" 6: "+cnt6.size()+"\t " + PrintUtil.printArray(cnt6));
		System.out.println(" out: "+cntOut.size()+"\t " + PrintUtil.printArray(cntOut));
		System.out.println("##########################################");
		System.out.println(" Total : "+result.size());
		System.out.println(" pattern : "+getAllpattern(result.size()));
		
	}
	
	public int getAllpattern(int size){
		return ((size)*(size-1)*(size-2)*(size-3)*(size-4)*(size-5)) / (6*5*4*3*2);
	}
	
	
	public static void main(String[] args) {
		// 35,15,20,11,31,26
		CountPicker p=new CountPicker();
		p.pick(389);
	}

}
