package com.biz.random.dao;

import com.biz.dao.AbstractBizDAO;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;
import com.co.util.sql.dml.InsertData;

public class BasisComDAO extends AbstractBizDAO{
	private static String tableName="basis_com";
	private static String id[]  ={"bnu"};
	private static String type[]={"NUMBER  NOT NULL"};
	private static String pk[]  ={"bnu"};
	
	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}

	public int createBasisTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}
	
	private void insertBatchBasisData(int bnu){
		String[] value={bnu+""};
		this.addBatch(InsertData.getSQL(tableName, id, value));
	}
	
	public static void main(String[] args) {
		BasisComDAO dao=new BasisComDAO();
		dao.dropTable();
		dao.createBasisTable();
		
		for(int i=1;i<46;i++){
			dao.insertBatchBasisData(i);
		}
		dao.executeBatch();
		System.out.println("END!");
	}
}
