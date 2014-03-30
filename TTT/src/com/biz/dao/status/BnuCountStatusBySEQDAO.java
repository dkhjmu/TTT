package com.biz.dao.status;

import java.util.HashMap;

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
public class BnuCountStatusBySEQDAO  extends AbstractBizDAO{
	private static String tableName="STATUS_BNU_SEQ";
	private static String id[]  ={"seq","bnu", "hcnt", "cnt", "cnt2", "cnt3", "gap" , "chec"};
	private static String type[]={
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
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
	
	public int insertBatch(String seq){
		sb=new StringBuffer();
		sb.append("insert into "+tableName+" \n");
		sb.append("select a.seq, a.bnu, GET_BASIS_CNT_DATA(a.seq, a.bnu, 6) hcnt, GET_BASIS_CNT_DATA(a.seq, a.bnu, 14) cnt, GET_BASIS_CNT_DATA(a.seq, a.bnu, 26) cnt, GET_BASIS_CNT_DATA(a.seq, a.bnu, 52) cnt, GET_BASIS_GAP_DATA(a.seq, a.bnu) gap,  \n");
		sb.append("nvl((select norder from basis_data where seq = a.seq and bnu=a.bnu), 0) as chec  \n");
		sb.append("from \n");
		sb.append("(select seq, bnu from \n");
		sb.append("(select seq from basis_data where seq > "+seq+" group by seq), (select bnu from basis_com))  a \n");
		sb.append("order by seq, bnu \n");
		
		int result = this.excuteUpdateSQL(sb.toString());
		if(result>0){
			this.commit();
		}else{
			System.out.println("Fail insert Batch : "+tableName);
		}
		
		return result;
	}
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> getCountAllList(){
		sb=new StringBuffer();
		sb.append("select seq,bnu, cnt, cnt2, cnt3, cnt2-cnt cntsum, cnt3-cnt2 cntsum2, cnt2-cnt ccnt1, cnt3-cnt2 ccnt2, gap, chec from    ");
		sb.append("STATUS_BNU_SEQ   ");
		sb.append("order by seq asc ");
		
		HashMap<String, HashMap<String, HashMap<String, String>>> result = this.excuteSelectSQLBySEQnBnu(sb.toString());
		
		return result;
	}
	
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> getStatusSeq(){
		sb=new StringBuffer();
		sb.append("select * from    ");
		sb.append("STATUS_BNU_SEQ   ");
		sb.append("order by seq asc ");
		
		HashMap<String, HashMap<String, HashMap<String, String>>> result = this.excuteSelectSQLBySEQnBnu(sb.toString());
		
		return result;
	}
	
	
	public static void main(String[] args) {
		BnuCountStatusBySEQDAO dao = new BnuCountStatusBySEQDAO();
		dao.dropTable();
		dao.createTable();
		dao.insertBatch("351");
		System.out.println("END!!!!");
	}
	
}
