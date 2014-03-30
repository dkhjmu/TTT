package com.biz.analysis;

import java.util.HashMap;
import java.util.Iterator;

import com.biz.base.dao.BasisDAO;
import com.biz.dao.pre.PreCountDAO;
import com.biz.dao.status.PointStatusBySEQDAO;
import com.biz.vo.PointMatrixVO;

public class BnuAnalizer {
	
	private static PreCountDAO pdao;
	private static PointStatusBySEQDAO dao;

	public static void main(String[] args) {
		BasisDAO Adao=new BasisDAO();
		int mseq=Adao.getMaxSeq();
		System.out.println(mseq+"!!");
		pdao = new PreCountDAO();
		dao = new PointStatusBySEQDAO();
		
		for(int bnu=1;bnu<46;bnu++){
			analize(mseq, bnu);
		}
		
	}

	public static void analize(int mseq, int bnu) {
		
		int sbet = pdao.getPreCount(mseq-1, bnu, "30");
		HashMap<String, HashMap<String, HashMap<String, String>>> result = dao.getPointAllList(mseq-sbet-1,mseq-1);
		
		
		PointMatrixVO cntPnt=new PointMatrixVO();
		PointMatrixVO ccnt1Pnt=new PointMatrixVO();
		PointMatrixVO ccnt2Pnt=new PointMatrixVO();
		PointMatrixVO gapPnt=new PointMatrixVO();
		PointMatrixVO porderPnt=new PointMatrixVO();
		PointMatrixVO checPnt=new PointMatrixVO();
		PointMatrixVO cntgapPnt=new PointMatrixVO();
		PointMatrixVO cgapPnt=new PointMatrixVO();
		PointMatrixVO c2gapPnt=new PointMatrixVO();
		
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
					cgapPnt.addBnu(	bmap.get("CGAP"));
					c2gapPnt.addBnu(	bmap.get("C2GAP"));
					cntgapPnt.addBnu(bmap.get("CNT")+"="+bmap.get("GAP"));
				}
			}
		}
		
		HashMap<String, HashMap<String, String>> tmap = dao.getValueList(mseq);
		
		for(int i=1;i<46;i++){
			HashMap<String, String> bmap = tmap.get(i+"");
			if(bmap.get("BNU").equals(bnu+"")){
			System.out.println(bmap.get("BNU")+"\t"
								+getSpStr(bmap, "CNT", cntPnt)+"\t"
								+getSpStr(bmap, "CCNT1", ccnt1Pnt)+"\t"
								+getSpStr(bmap, "CCNT2", ccnt2Pnt)+"\t"
								+getSpStr(bmap, "GAP", gapPnt)+"\t"
								+getSpStr(bmap, "PORDER", porderPnt)+"\t"
								+getSpStr(bmap, "CHEC", checPnt)+"\t"
								+getSpStr(bmap, "CGAP", cgapPnt)+"\t"
								+getSpStr(bmap, "C2GAP", c2gapPnt)+"\t"
								+bmap.get("CNT")+"="+bmap.get("GAP")+"\t"
								+cntgapPnt.getPoint(bmap.get("CNT")+"="+bmap.get("GAP"))+"\t"
			);
			}
		}
	}

	public static String getSpStr(HashMap<String, String> bmap, String str,	PointMatrixVO cntPnt) {
		double cp1 = cntPnt.getPoint(	bmap.get(str));
		return bmap.get(str)+"\t"+cp1;
	}

}
