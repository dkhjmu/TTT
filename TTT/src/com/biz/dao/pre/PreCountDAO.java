package com.biz.dao.pre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.biz.base.dao.BasisDAO;
import com.biz.dao.AbstractBizDAO;
import com.biz.vo.IntVo;
import com.biz.vo.SeqResultVO;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;
import com.co.util.sql.dml.InsertData;

public class PreCountDAO extends AbstractBizDAO {
	
	private static String tableName="pre_cnt_data";
	private static String id[]  ={"seq",              "bnu",			 "cnt",             "norder"};
	private static String type[]={"NUMBER  NOT NULL", "NUMBER  NOT NULL","NUMBER  NOT NULL","NUMBER"};
	private static String pk[]  ={"seq", "bnu"};

	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}

	public int createBasisTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}
	
	
	public int getPreCount(int seq, int bnu, String cnt) {
		String result = "";
		int i=1;
		for (; i < 990; i++) {
			result = this.executeSingleRowFirstColumSelectSQL("select GET_BASIS_CNT_DATA("+seq+", "+bnu+", "+i+") cnt from dual");
			if (result.equals(cnt)) {
				break;
			}
		}
		
		return i;
	}
	
	public void printMap(){
		BasisDAO basisDao=new BasisDAO();
		List<SeqResultVO> results = basisDao.getAllResultVo();
		
		PreCountDAO dao=new PreCountDAO();
		for(int i=300;i<results.size();i++){
			SeqResultVO vo=results.get(i);
			int[] rr=vo.getNumsArrayNobonus();
			String sum="";
			for(int j=0;j<rr.length;j++){
				sum=sum+"\t"+dao.getPreCount(vo.getSeq().getInt(), rr[j], "2");
			}//for j
			System.out.println((vo.getSeq().getInt()-1)+":"+sum);
		}//for i
		
	}
	
	public static void main(String[] args) {
		BasisDAO Adao=new BasisDAO();
		int seq=Adao.getMaxSeq();

//		PreCountDAO dao=new PreCountDAO();
//		for(int i=1;i<46;i++){
//			System.out.println(i+"\t"+dao.getPreCount(seq, i, "5"));
//		}
		//System.out.println(dao.getPreCount(seq, 16, "10"));
		PreCountDAO pdao=new PreCountDAO();
		pdao.dropTable();
		pdao.createBasisTable();
		for(int i=seq-300;i<=seq;i++){
			pdao.insertBatchSeq(i);
		}
		System.out.println("ALLEnd");
		
	}

	public void insertBatchSeq(int seq) {
		ArrayList<PreCntVO> list = getPreCntVOList(seq);
		for(PreCntVO vo:list){
			insertVo(vo);
		}
		executeBatch();
	}

	public ArrayList<PreCntVO> getPreCntVOList(int seq) {
		ArrayList<PreCntVO> list=new ArrayList<PreCntVO>();
		for(int i=1;i<46;i++){
			PreCntVO vo=new PreCntVO(seq, i, getPreCount(seq, i, "3"));
			list.add(vo);
		}
		Collections.sort(list);
		for(int i=0;i<list.size();i++){
			PreCntVO vo=list.get(i);
			vo.setNorder(i+1);
		}
		return list;
	}
	
	public void insertVo(PreCntVO vo){
		this.addBatch(InsertData.getSQL(tableName, id, vo.getValue()));
	}
}
