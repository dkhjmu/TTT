package com.biz.dao.status;

import java.util.HashMap;

import com.biz.base.dao.BasisDAO;
import com.biz.dao.AbstractBizDAO;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;

/**
 * 
 * BNU의 상태를 조회함
 * 
 * seq, bnu, cnt, gap, chec(등장한 값인지 아닌지 나타냄)
 * 
 * @author dkhjmu
 *
 */
public class PointStatusBySEQDAO  extends AbstractBizDAO{
	private static String tableName="STATUS_POINT_SEQ";
	private static String id[]  ={"seq","bnu", "point"};
	private static String type[]={
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NULL"
	};
	private static String pk[]  ={"seq","bnu"};
	
	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}
	
	public int createTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}
	
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> getPointAllList(int sseq, int eseq){
		sb=new StringBuffer();
		
		sb.append(" select a.seq, a.bnu, b.norder, a.cnt, a.cnt2, a.cnt3, a.cnt2-a.cnt ccnt1, a.cnt3-a.cnt ccnt2,                                             ");
		sb.append("                           a.cnt-a.gap cgap, a.cnt2-a.gap c2gap,  a.gap, a.chec, c.cnt pcnt, c.norder porder  ");
		sb.append(" from STATUS_BNU_SEQ A, basis_data B, pre_cnt_data c                                                                                       ");
		sb.append(" where (a.seq) = (b.seq-1)                                                                                                                 ");
		sb.append("   and (a.seq) = (c.seq)                                                                                                                   ");
		sb.append("   and a.seq between "+sseq+" and  "+eseq+"                                                                                                ");
		sb.append("   and a.bnu = b.bnu                                                                                                                       ");
		sb.append("   and a.bnu = c.bnu                                                                                                                       ");
//		sb.append("   and b.norder <> 7                                                                                                                       ");
		sb.append("   order by seq desc, bnu                                                                                                                  ");
		
		HashMap<String, HashMap<String, HashMap<String, String>>> result = this.excuteSelectSQLBySEQnBnu(sb.toString());
		
		return result;
	}
	
	public HashMap<String, HashMap<String, String>> getValueList(int seq){
		sb=new StringBuffer();
		
		sb.append(" select a.seq, a.bnu, a.cnt, a.cnt2, a.cnt3, a.cnt2-a.cnt ccnt1, a.cnt3-a.cnt ccnt2,                                             ");
		sb.append("                           a.cnt-a.gap cgap, a.cnt2-a.gap c2gap,  a.gap, a.chec, c.cnt pcnt, c.norder porder  ");
		sb.append(" from STATUS_BNU_SEQ A, pre_cnt_data c  ");
		sb.append(" where (a.seq) = (c.seq)  ");
		sb.append("   and a.seq = "+seq+"  ");
		sb.append("   and a.bnu = c.bnu  ");
		sb.append("   order by seq desc, bnu, norder  ");
		
		//System.out.println(sb.toString());
		
		HashMap<String, HashMap<String, HashMap<String, String>>> result = this.excuteSelectSQLBySEQnBnu(sb.toString());
		
		return result.get(seq+"");
	}
	
	
	
	
	
	public int insertBatch(int seq){
		for(int i=1;i<46;i++){
			sb=new StringBuffer();
			sb.append("insert into "+tableName+" \n");
			sb.append("select "+seq+" as seq, \n");
			sb.append("       "+i+" as bnu, \n");
			sb.append("     nvl((select point from STATUS_POINT_SEQ where seq="+(seq-1)+" and bnu="+i+" and rownum=1), 0) \n");
			sb.append("      + (select decode(count(*), 0, -1, 10.5) from basis_data where seq = "+seq+" and bnu ="+i+" and norder <> 7 ) \n");
			sb.append("      - nvl((select norder from basis_data where seq = "+seq+" and bnu ="+i+" and norder <> 7 ),0) point from dual  \n");
			
			//System.out.println(sb.toString());
			
			this.addBatch(sb.toString());
		}
		
		return 0;
	}
	
	
	public static void main(String[] args) {
		BasisDAO bdao=new BasisDAO();
		System.out.println(bdao.getMaxSeq());
		int mseq=bdao.getMaxSeq();
//		int mseq=3;
		
		PointStatusBySEQDAO dao = new PointStatusBySEQDAO();
		dao.dropTable();
		dao.createTable();
		for(int i=1;i<=mseq;i++){
			System.out.println(i+"!!");
			dao.insertBatch(i);
		}
		dao.executeBatch();
		System.out.println("END!!!!");
	}
	
}
