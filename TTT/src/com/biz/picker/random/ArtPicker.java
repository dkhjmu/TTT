package com.biz.picker.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.biz.dao.pre.PreCntVO;
import com.biz.dao.pre.PreCountDAO;
import com.biz.dao.status.BnuCountStatusBySEQDAO;
import com.biz.picker.AbstractPicker;
import com.biz.vo.IntVo;
import com.co.util.ArrayUtil;

public class ArtPicker extends AbstractPicker {
	
	private HashMap<String, HashMap<String, HashMap<String, String>>> all;
	private ArrayList<HashMap<String, String>> aVo;
	private ArrayList<HashMap<String, String>> gap1;
	private ArrayList<HashMap<String, String>> under12;
	private ArrayList<HashMap<String, String>> over20;
	private int curSeq;
	private PreCountDAO pdao;
	private BnuCountStatusBySEQDAO dao;
	
	public ArtPicker() {
		pdao = new PreCountDAO();
		
		dao = new BnuCountStatusBySEQDAO();
		all = dao.getCountAllList();
		aVo = new ArrayList<HashMap<String, String>>();
		gap1 = new ArrayList<HashMap<String, String>>();
		under12 = new ArrayList<HashMap<String, String>>();
		over20 = new ArrayList<HashMap<String, String>>();
	}
	
	
	@Override
	public int[] getSeq(int i) {
		return null;
	}

	@Override
	public int pick(int tryN, int seq) {
		curSeq = seq-1;
		HashMap<String, HashMap<String, String>> result = all.get((curSeq)+"");
		Iterator<String> iter = result.keySet().iterator();
		
		ArrayList<HashMap<String, String>> high = new ArrayList<HashMap<String, String>>();

		int[] aArray=new int[45];
		int index=0;
		while (iter.hasNext()) {
			String n = iter.next();
			HashMap<String, String> map = result.get(n);
			IntVo vo = new IntVo(map.get("BNU"));
			
			IntVo vos = new IntVo(map.get("CNTSUM"));
			IntVo vos2 = new IntVo(map.get("CNTSUM2"));
			if (vos.getInt() >= 0 && vos.getInt() <= 4 && vos2.getInt() >= 0 && vos2.getInt() <= 6) {
				aVo.add(map);
				IntVo gap = new IntVo(map.get("GAP"));
				IntVo chec = new IntVo(map.get("CHEC"));
				if(chec.getInt()!=0){
					gap1.add(map);
					if(vos2.getInt()>=2 && vos2.getInt()<=6){
						high.add(map);
					}
				}else if(gap.getInt()<=12){
					aArray[index++]=vo.getInt();
					under12.add(map);
				}else if(gap.getInt()>=20){
					over20.add(map);
				}
				
			} 
		}
		
		ArrayList<PreCntVO> plist = pdao.getPreCntVOList(curSeq);
		Collections.sort(plist);
		
		for(int i=0;i<5;i++){
			PreCntVO vo = plist.get(i);
			System.out.println(vo);
		}

		//10
		//4 1-1-2-2
		//3 1-2-3
		//1 1-1-1-3
		//1 1-1-1-1-2
		//1 2-2-2
		
		//even
		//4 3-3
		//3 2-4
		//3 4-2
		//1 1-5
		//1 5-1
		
		//after
		//minus
		// 10
		// 9
		// 8
		
		// 4g
		//4 1-1-2-2
		//4 3-3
		//System.out.println(gap1.size());
		
		int[] pickTemp = new int[6];
		
		if(high.size()==0){
			System.out.println("'oo'");
		}else{
			for(HashMap<String, String> map : high){
				System.out.println(map.get("BNU"));
				pickTemp=getTempPick(map.get("BNU"));
			}
		}
		System.out.println(under12.size()+"!!");
		
		
		for(int i=0;i<aVo.size();i++){
			HashMap<String, String> tmap = aVo.get(i);
			System.out.println(tmap.get("BNU"));
		}
		
		return 0;
	}
	
	private int[] getTempPick(String seed) {
//		     	c	1	2	cs	g	ch
//		389	16	2	5	10	3	8	4
//		389	17	2	5	8	3	1	0
//		389	28	2	2	3	0	6	0
//		389	37	3	4	8	1	6	0
//		389	39	1	2	4	1	11	0
//		389	40	5	6	10	1	2	0
		
//		389	3	1	2	5	1	22	7
//		389	7	6	8	8	2	3	3
//		389	16	2	5	10	3	8	4
//		389	18	1	4	8	3	17	2
//		389	20	3	4	9	1	2	5
//		389	23	2	2	5	0	12	6
//		389	26	4	10	13	6	2	1		
		
		int[] result=new int[6];
		IntVo vo=new IntVo(seed);
		
		//int pcnt=pdao.getPreCount(curSeq, vo.getInt(), "10");
		
		return result;
	}


	public static void main(String[] args) {
		ArtPicker pick=new ArtPicker();
		pick.pick(1, 390);
	}
	
	
}
