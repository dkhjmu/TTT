package com.biz.analysis;

import java.util.HashMap;
import java.util.Iterator;

import com.biz.dao.status.PointStatusBySEQDAO;
import com.biz.vo.PointMatrixVO;

public class PointAnalizer {
	
	public static void main(String[] args) {
		
		PointStatusBySEQDAO dao=new PointStatusBySEQDAO();
		HashMap<String, HashMap<String, HashMap<String, String>>> result = dao.getPointAllList(190, 390);
		
		
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
				cntPnt.addBnu(	bmap.get("CNT"));
				ccnt1Pnt.addBnu(bmap.get("CCNT1"));
				ccnt2Pnt.addBnu(bmap.get("CCNT2"));
				gapPnt.addBnu(	bmap.get("GAP"));
				porderPnt.addBnu(bmap.get("PORDER"));
				checPnt.addBnu(	bmap.get("CHEC"));
				cntgapPnt.addBnu(bmap.get("CNT")+"="+bmap.get("GAP"));
			}
		}
		
		HashMap<String, HashMap<String, String>> tmap = dao.getValueList(388);
		
		for(int i=1;i<46;i++){
			HashMap<String, String> bmap = tmap.get(i+"");
			
			System.out.println(bmap.get("BNU")+"\t"
								+getSpStr(bmap, "CNT", cntPnt)+"\t"
								+getSpStr(bmap, "CCNT1", ccnt1Pnt)+"\t"
								+getSpStr(bmap, "CCNT2", ccnt2Pnt)+"\t"
								+getSpStr(bmap, "GAP", gapPnt)+"\t"
								+getSpStr(bmap, "PORDER", porderPnt)+"\t"
								+getSpStr(bmap, "CHEC", checPnt)+"\t"
								+bmap.get("CNT")+"="+bmap.get("GAP")+"\t"
								+cntgapPnt.getPoint(bmap.get("CNT")+"="+bmap.get("GAP"))+"\t"
			);
		}
		
	}

	public static String getSpStr(HashMap<String, String> bmap, String str,	PointMatrixVO cntPnt) {
		double cp1 = cntPnt.getPoint(	bmap.get(str));
		return bmap.get(str)+"\t"+cp1;
	}

}
