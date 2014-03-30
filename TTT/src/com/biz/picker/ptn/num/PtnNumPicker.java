package com.biz.picker.ptn.num;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.biz.base.dao.BasisDAO;
import com.biz.dao.status.BnuCountStatusBySEQDAO;
import com.biz.picker.AbstractPicker;
import com.biz.vo.IntVo;
import com.biz.vo.SeqResultVO;

public class PtnNumPicker extends AbstractPicker {
	
	private HashMap<String, HashMap<String, HashMap<String, String>>> all;
	
	public PtnNumPicker(){
		BnuCountStatusBySEQDAO dao = new BnuCountStatusBySEQDAO();
		all = dao.getCountAllList();
	}

	@Override
	public int[] getSeq(int i) {
		return list[0];
	}

	@Override
	public int pick(int tryN, int seq) {
		pick(seq);
		HashMap<String, HashMap<String, String>> result = all.get((seq-1)+"");

		Iterator<String> iter = result.keySet().iterator();

		ArrayList<HashMap<String, String>> aVo = new ArrayList<HashMap<String, String>>();

		list=new int[1][45];
		int i=0;
		while (iter.hasNext()) {
			String n = iter.next();
			HashMap<String, String> map = result.get(n);
			IntVo vo = new IntVo(map.get("BNU"));
			
			IntVo vos = new IntVo(map.get("CNTSUM"));
			IntVo vos2 = new IntVo(map.get("CNTSUM2"));
			if (vos.getInt() >= 0 && vos.getInt() <= 4 && vos2.getInt() >= 0 && vos2.getInt() <= 6) {
				aVo.add(map);
				list[0][i++]=vo.getInt();
			} 
		}
		System.out.println("list i:"+i);
		
		return 0;
	}
	
	@Override
	public int getSize() {
		return 1;
	};
	
	
	public HashMap<String, HashMap<String, String>> tresh(int seq){
		System.out.println("tresh!!");
		
		BasisDAO dao=new BasisDAO();
		List<SeqResultVO> all = dao.getAllResultVo();
		HashMap<String, IntVo> map=new HashMap<String, IntVo>();
		int[] ma={0,0,0,0,0,0};
		for(int i=seq-53;i<seq-1;i++){
			SeqResultVO vo = all.get(i);
			int[] rr=vo.getNumsArrayNobonus();
			Arrays.sort(rr);
			for(int j=0;j<6;j++){
				ma[j]=(int)(Math.floor(rr[j]/10));
			}
			String str=Arrays.toString(ma);
			//System.out.println(str);
			if(map.get(str)==null){
				map.put(str, new IntVo(1));
			}else{
				map.get(str).add(new IntVo(1));
			}
		}
		
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
			System.out.println(key+"\t"+map.get(key).getInt());
		}
		
		return null;
	}
	
	public void pick(int seq) {
//		tresh(seq);
		
	}
	
	public int getAllpattern(int size){
		return ((size)*(size-1)*(size-2)*(size-3)*(size-4)*(size-5)) / (6*5*4*3*2);
	}
	
	
	public static void main(String[] args) {
		// 35,15,20,11,31,26
		PtnNumPicker p=new PtnNumPicker();
		//p.pick(390);
		p.pick(10, 390);
		int[] aa=p.getSeq(390);
		for(int i=0;i<aa.length;i++){
			System.out.println(aa[i]);
		}
	}

}
