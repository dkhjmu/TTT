package com.co.util.sql.dml;

public class InsertData {
	
	public static String getSQL(String table, String id[], String value[]){
		//key part
		StringBuffer keys=new StringBuffer();
		if(table!=null){
			keys.append("INSERT INTO ").append(table).append("(       \n");
		}else{
			keys.append("INSERT (                                       \n");
		}
		//value part
		StringBuffer values=new StringBuffer();
		values.append(" VALUES (                                        \n");
		for (int i = 0; i < id.length; i++) {
			keys.append("   ").append(id[i]);
			values.append("    ").append(value[i]);
			if(i==(id.length-1)){
				keys.append("  \n )                                     \n");
				values.append(" --").append(id[i]).append("         \n");
				values.append("    )                                    \n");
			}else{
				keys.append(",                                          \n");
				values.append(", --").append(id[i]).append("        \n");
			}
		}
		//add 2 parts
		return keys.append(values).toString();
	}
}