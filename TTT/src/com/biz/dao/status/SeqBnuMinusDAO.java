package com.biz.dao.status;

import java.util.List;

import com.biz.base.dao.BasisDAO;
import com.biz.dao.AbstractBizDAO;
import com.biz.vo.SeqResultVO;
import com.co.util.ArrayUtil;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;
import com.co.util.sql.dml.InsertData;

public class SeqBnuMinusDAO extends AbstractBizDAO{
	private static String tableName="STATUS_MINUS_BNU_SEQ";
	private static String id[]  ={"seq","bnu1","bnu2","bnu3","bnu4","bnu5","bnu6"};
	private static String type[]={
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL" 
	};
	private static String pk[]  ={"seq"};
	
	private static String tableName2="STATUS_MINUS_BNU_SEQ2";
	private static String id2[]  ={"seq","tnum","bnu1"};
	private static String type2[]={
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL", 
		"NUMBER  NOT NULL" 
	};
	private static String pk2[]  ={"seq", "tnum"};
	
	
	public int dropTable(){
		this.excuteUpdateSQL(DropTable.getSQL(tableName));
		return this.excuteUpdateSQL(DropTable.getSQL(tableName2));
	}
	
	public int createTable() {
		this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName2, id2, type2, pk2));
	}
	
	public void insertData(int seq, int bnu1, int bnu2, int bnu3, int bnu4, int bnu5, int bnu6){
		String[] value={seq+"", bnu1+"",bnu2+"",bnu3+"",bnu4+"",bnu5+"",bnu6+"",};
		this.addBatch(InsertData.getSQL(tableName, id, value));
	}
	
	public void insertData2(int seq, int tnum, int bnu1){
		String[] value={seq+"", tnum+"", bnu1+""};
		this.addBatch(InsertData.getSQL(tableName2, id2, value));
	}
	
	public void insetBatch(){
		BasisDAO dao=new BasisDAO();
		List<SeqResultVO> result = dao.getAllResultVo();
		for(int i=0;i<result.size();i++){
			System.out.println("seq:"+(i+1)+" !! ");
			int[] rr=result.get(i).getNumsArray();
			rr=ArrayUtil.sort(rr);
			insertData(i+1, rr[1]-rr[0], rr[2]-rr[1], rr[3]-rr[2], rr[4]-rr[3], rr[5]-rr[4], rr[6]-rr[5]);
			insertData2(i+1, 1, rr[1]-rr[0]);
			insertData2(i+1, 2, rr[2]-rr[1]);
			insertData2(i+1, 3, rr[3]-rr[2]);
			insertData2(i+1, 4, rr[4]-rr[3]);
			insertData2(i+1, 5, rr[5]-rr[4]);
			insertData2(i+1, 6, rr[6]-rr[5]);
		}
		this.executeBatch();
	}
	
	public static void main(String[] args) {
		SeqBnuMinusDAO dao = new SeqBnuMinusDAO();
		dao.dropTable();
		dao.createTable();
		dao.insetBatch();
	}
	
}
