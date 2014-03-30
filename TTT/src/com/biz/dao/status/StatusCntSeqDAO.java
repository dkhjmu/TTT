package com.biz.dao.status;

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
public class StatusCntSeqDAO  extends AbstractBizDAO{
	private static String tableName="STATUS_CNT_SEQ";
	private static String id[]  ={"seq",
									"cnt0", 
									"cnt1",
									"cnt2",
									"cnt3",
									"cnt4",
									"cnt5",
									"cnt6",
									"cOut",
								};
	private static String type[]={
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NULL" 
	};
	private static String pk[]  ={"seq"};
	
	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}
	
	public int createTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}
	
	public int insertBatch(){
		sb=new StringBuffer();
		
		sb.append("insert into "+tableName+" \n");
		sb.append(" select distinct a.seq,                          \n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 0) cnt0,		\n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 1) cnt1,		\n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 2) cnt2,		\n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 3) cnt3,		\n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 4) cnt4,		\n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 5) cnt5,		\n");
		sb.append("        GET_STATUS_CNT_GROUP(a.seq, 6) cnt6,		\n");
		sb.append("        7 - (GET_STATUS_CNT_GROUP(a.seq, 0) + GET_STATUS_CNT_GROUP(a.seq, 1) + GET_STATUS_CNT_GROUP(a.seq, 2) + GET_STATUS_CNT_GROUP(a.seq, 3) + GET_STATUS_CNT_GROUP(a.seq, 4) + GET_STATUS_CNT_GROUP(a.seq, 5) + GET_STATUS_CNT_GROUP(a.seq, 6)) as cOut \n");		
		sb.append(" from basis_data a	\n");	
		sb.append("  order by seq		\n");
		 
		
		int result = this.excuteUpdateSQL(sb.toString());
		if(result>0){
			this.commit();
		}else{
			System.out.println("Fail insert Batch : "+tableName);
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		StatusCntSeqDAO dao = new StatusCntSeqDAO();
		dao.dropTable();
		dao.createTable();
		dao.insertBatch();
		System.out.println("END!!!!");
	}
	
}
