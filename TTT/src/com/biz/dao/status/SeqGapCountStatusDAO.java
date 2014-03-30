package com.biz.dao.status;

import com.biz.dao.AbstractBizDAO;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;

/**
 * 
 * gap 레벨별로 패턴화 시킴
 * BnuCountStatusBySEQDAO를 선행할것!
 * 
 * @author dkhjmu
 *
 */
public class SeqGapCountStatusDAO  extends AbstractBizDAO{
	private static String tableName="STATUS_SEQ_GAP";
	private static String id[]  ={"seq","gap1", "gap2", "gap3"};
	private static String type[]={
		"NUMBER  NOT NULL", 
		"NUMBER  NULL", 
		"NUMBER  NULL", 
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
		sb.append("select                                    ");
		sb.append("      seq                                 ");
		sb.append("       ,GET_SEQ_GAP_COUNT(seq, 1) gap1    ");
		sb.append("       ,GET_SEQ_GAP_COUNT(seq, 2) gap2    ");
		sb.append("       ,GET_SEQ_GAP_COUNT(seq, 3) gap3    ");
		sb.append("from STATUS_BNU_SEQ                       ");
		sb.append(" group by seq                             ");
		sb.append(" order by seq asc                         ");
		
		int result = this.excuteUpdateSQL(sb.toString());
		if(result>0){
			this.commit();
		}else{
			System.out.println("Fail insert Batch : "+tableName);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		SeqGapCountStatusDAO dao = new SeqGapCountStatusDAO();
		dao.dropTable();
		dao.createTable();
		dao.insertBatch();
		System.out.println("END!!!!");
	}
	
}
