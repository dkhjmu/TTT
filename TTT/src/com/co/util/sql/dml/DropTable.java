package com.co.util.sql.dml;

public class DropTable {
	public static String getSQL(String table){
		return "DROP TABLE "+table+" \n";
	}
}
