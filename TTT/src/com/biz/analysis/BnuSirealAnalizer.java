package com.biz.analysis;

import java.util.HashMap;
import java.util.Iterator;

import com.biz.base.dao.BasisDAO;
import com.biz.dao.pre.PreCountDAO;
import com.biz.dao.status.PointStatusBySEQDAO;
import com.biz.vo.PointMatrixVO;

public class BnuSirealAnalizer {
	
	private static PointStatusBySEQDAO dao;
	private static PreCountDAO pdao;

	public static void main(String[] args) {
		BasisDAO Adao=new BasisDAO();
		int mseq=Adao.getMaxSeq();
		dao = new PointStatusBySEQDAO();
		pdao = new PreCountDAO();
		
		for(int bnu=1;bnu<46;bnu++){
			analize(mseq, bnu);
		}
		
	}

	public static void analize(int mseq, int bnu) {
		
		int sbet = pdao.getPreCount(mseq-1, bnu, "30");
//		System.out.println(sbet);
		
		HashMap<String, HashMap<String, HashMap<String, String>>> result = dao.getPointAllList(mseq-sbet-1, mseq-1);
		
		
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
					System.out.println(
									bmap.get("BNU")+"\t"
									+bmap.get("SEQ")+"\t"
//									+bmap.get("NORDER")+"\t"
									+bmap.get("CNT")+"\t"
									+bmap.get("CNT2")+"\t"
									+bmap.get("CNT3")+"\t"
									+bmap.get("CCNT1")+"\t"
									+bmap.get("CCNT2")+"\t"
									+bmap.get("GAP")+"\t"
									+bmap.get("PCNT")+"\t"
									+bmap.get("PORDER")+"\t"
									+bmap.get("CHEC")+"\t"
									+bmap.get("CNT")+"="+bmap.get("GAP")
							);					
				}
			}
		}
		
		HashMap<String, HashMap<String, String>> tmap = dao.getValueList(388);
		
//		for(int i=1;i<46;i++){
//			HashMap<String, String> bmap = tmap.get(i+"");
//			if(bmap.get("BNU").equals(bnu+"")){
//			System.out.println(bmap.get("BNU")+"\t"
//								+getSpStr(bmap, "CNT", cntPnt)+"\t"
//								+getSpStr(bmap, "CCNT1", ccnt1Pnt)+"\t"
//								+getSpStr(bmap, "CCNT2", ccnt2Pnt)+"\t"
//								+getSpStr(bmap, "GAP", gapPnt)+"\t"
//								+getSpStr(bmap, "PORDER", porderPnt)+"\t"
//								+getSpStr(bmap, "CHEC", checPnt)+"\t"
//								+bmap.get("CNT")+"="+bmap.get("GAP")+"\t"
//								+cntgapPnt.getPoint(bmap.get("CNT")+"="+bmap.get("GAP"))+"\t"
//			);
//			}
//		}
	}

	public static String getSpStr(HashMap<String, String> bmap, String str,	PointMatrixVO cntPnt) {
		double cp1 = cntPnt.getPoint(	bmap.get(str));
		return bmap.get(str)+"\t"+cp1;
	}

}
