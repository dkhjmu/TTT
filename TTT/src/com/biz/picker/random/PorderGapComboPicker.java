package com.biz.picker.random;

import java.util.HashMap;
import java.util.Iterator;

import com.biz.checher.Checker;
import com.biz.dao.pre.PreCountDAO;
import com.biz.dao.status.PointStatusBySEQDAO;
import com.biz.picker.AbstractPicker;
import com.biz.vo.PickerVo;
import com.biz.vo.PointMatrixVO;
import com.co.util.ArrayUtil;

public class PorderGapComboPicker extends AbstractPicker {


	private static PreCountDAO pdao;
	private static PointStatusBySEQDAO dao;
	PickerVo pick;
	
	public PorderGapComboPicker() {
		pdao = new PreCountDAO();
		dao = new PointStatusBySEQDAO();
		pick = new PickerVo();
	}
	
	@Override
	public int[] getSeq(int i) {
		return pick.getPickNum();
	}
	
	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public int pick(int tryN, int seq) {
		pick = new PickerVo();
		for(int i=1;i<46;i++){
			analize((seq-1), i);
		}
		return 0;
	}
	
	
	public void analize(int mseq, int bnu) {
		
		int sbet = pdao.getPreCount(mseq, bnu, "30");
		HashMap<String, HashMap<String, HashMap<String, String>>> result = dao.getPointAllList(mseq-sbet,mseq);
		
		
		PointMatrixVO cntPnt=new PointMatrixVO();
		PointMatrixVO ccnt1Pnt=new PointMatrixVO();
		PointMatrixVO ccnt2Pnt=new PointMatrixVO();
		PointMatrixVO gapPnt=new PointMatrixVO();
		PointMatrixVO porderPnt=new PointMatrixVO();
		PointMatrixVO checPnt=new PointMatrixVO();
		PointMatrixVO cntgapPnt=new PointMatrixVO();
		
		Iterator<String> iter = result.keySet().iterator();
		while(iter.hasNext()){
			String seq=iter.next();
			HashMap<String, HashMap<String, String>> map = result.get(seq);
			
			Iterator<String> biter = map.keySet().iterator();
			while(biter.hasNext()){
				HashMap<String, String> bmap = map.get(biter.next());
				if(bmap.get("BNU").equals(bnu+"")){
					cntPnt.addBnu(	bmap.get("CNT"));
					ccnt1Pnt.addBnu(bmap.get("CCNT1"));
					ccnt2Pnt.addBnu(bmap.get("CCNT2"));
					gapPnt.addBnu(	bmap.get("GAP"));
					porderPnt.addBnu(bmap.get("PORDER"));
					checPnt.addBnu(	bmap.get("CHEC"));
					cntgapPnt.addBnu(bmap.get("CNT")+"="+bmap.get("GAP"));
				}
			}
		}
		
		HashMap<String, HashMap<String, String>> tmap = dao.getValueList(mseq);
		
		for(int i=1;i<46;i++){
			HashMap<String, String> bmap = tmap.get(i+"");
			if(bmap.get("BNU").equals(bnu+"")){
					
					double pp = porderPnt.getPoint(bmap.get("PORDER"));
					double cp = cntgapPnt.getPoint(bmap.get("CNT")+"="+bmap.get("GAP"));
//					System.out.println("cp:"+bmap.get("BNU")+"="+cp+", pp="+pp);
					if(cp!=0.0){
						if(pp!=0.0){
//							System.out.println(bmap.get("BNU")+"\t"
//										+getSpStr(bmap, "CNT", cntPnt)+"\t"
//										+getSpStr(bmap, "CCNT1", ccnt1Pnt)+"\t"
//										+getSpStr(bmap, "CCNT2", ccnt2Pnt)+"\t"
//										+getSpStr(bmap, "GAP", gapPnt)+"\t"
//										+getSpStr(bmap, "PORDER", porderPnt)+"\t"
//										+getSpStr(bmap, "CHEC", checPnt)+"\t"
//										+bmap.get("CNT")+"="+bmap.get("GAP")+"\t"
//										+cntgapPnt.getPoint(bmap.get("CNT")+"="+bmap.get("GAP"))+"\t"
//							);
							pick.add(bmap.get("BNU"));
						}
					}else{
						
					}
					
			}
		}
	}

	public static String getSpStr(HashMap<String, String> bmap, String str,	PointMatrixVO cntPnt) {
		double cp1 = cntPnt.getPoint(	bmap.get(str));
		return bmap.get(str)+"\t"+cp1;
	}
	
	public static void main(String[] args) {
		PorderGapComboPicker picker=new PorderGapComboPicker();
		picker.pick(100, 543);
		int[] rr=picker.getSeq(0);
		ArrayUtil.print(rr);
		
//		int[] right={16,28,17,40,37,39};
//		int[] right={5,6,19,26,41,45};
		int[] right={13,18,26,31,34,44};
		System.out.println(Checker.checkResult(rr, right, 34));
		
		System.out.println("ALL OK");
	}

}
