package com.biz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.biz.util.CommonLogger;

public class AbstractBizDAO {
	protected StringBuffer sb=new StringBuffer();

	protected String sql;

	protected CommonLogger logger=new CommonLogger();
	
	protected static int batchNum=0;

	Statement stmt=null;

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	//TODO
//	String driverName = "oracle.jdbc.driver.OracleDriver";
	String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String usreId = "brjweb";
	String password = "brjweb";
	String driverName = "oracle.jdbc.driver.OracleDriver";
//	String dbURL = "jdbc:oracle:thin:@192.168.1.3:1521:AIDEVDB";
//	String usreId = "aitsadm";
//	String password = "admaits00";
	
	public AbstractBizDAO(){
		try{
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, usreId, password);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void getConnection() {
		try{
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, usreId, password);
		}catch (Exception e) {
		}
	}
	
	protected void close(){
		try {
			if (conn != null){
				conn.close();
			}
		} catch (Exception ex) {
		}
	}

	protected void finalize() throws Throwable {
		try {
			if (conn != null){
				conn.close();
			}
		} catch (Exception ex) {
		}
		super.finalize();
		
	}

	private void prepare(String sql) throws Exception{
		ps = conn.prepareStatement(sql);
	}

	protected void dispose(){
		try {
			if (rs != null){
				rs.close();
				rs=null;
			}
		} catch (Exception ex) {
		} // rs close
		try {
			if (ps != null){
				ps.close();
				ps=null;
			}
		} catch (Exception ex) {
		} // ps close
		try {
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<HashMap<String, String>> excuteSelectSQL(String sql){
		ArrayList<HashMap<String, String>> resultList=null;
		try {
			if(conn == null){
				getConnection();
			}
			prepare(sql);

			rs = ps.executeQuery();

			if(rs!=null){
				resultList = new ArrayList<HashMap<String,String>>();
				ResultSetMetaData rsm=rs.getMetaData();
				int cc=rsm.getColumnCount();
				while(rs.next()){
					HashMap<String, String> resultMap=new HashMap<String, String>();
					for(int i=1;i<=cc;i++){
						resultMap.put(rsm.getColumnName(i), rs.getString(i));
					}
					resultList.add(resultMap);
				}
			}

		} catch (Exception ex) {
			logger.error("ExcuteSQL Exception : " + ex);
			logger.error("! SQL !");
			logger.error(sql);
			logger.error("! SQL !");
		}finally{
			dispose();
		}

		return resultList;
	}//excuteSQL
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> excuteSelectSQLBySEQnBnu(String sql){
		HashMap<String, HashMap<String, HashMap<String, String>>> resultHashMap=null;
		try {
			if(conn == null){
				getConnection();
			}
			prepare(sql);
			
			rs = ps.executeQuery();
			
			if(rs!=null){
				resultHashMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
				ResultSetMetaData rsm=rs.getMetaData();
				int cc=rsm.getColumnCount();
				//HashMap<String, HashMap<String, String>> resultBnuMap=new HashMap<String, HashMap<String, String>>();
				while(rs.next()){
					HashMap<String, String> resultMap=new HashMap<String, String>();
					for(int i=1;i<=cc;i++){
						resultMap.put(rsm.getColumnName(i).toUpperCase(), rs.getString(i));
					}
					HashMap<String, HashMap<String, String>> map = resultHashMap.get(resultMap.get("SEQ"));
					if(map!=null){
						map.put(resultMap.get("BNU"), resultMap);
						resultHashMap.put(resultMap.get("SEQ"), map);
					}else{
						map=new HashMap<String, HashMap<String, String>>();
						map.put(resultMap.get("BNU"), resultMap);
						resultHashMap.put(resultMap.get("SEQ"), map);
					}
				}
			}
			
		} catch (Exception ex) {
			logger.error("ExcuteSQL Exception : " + ex);
			logger.error("! SQL !");
			logger.error(sql);
			logger.error("! SQL !");
		}finally{
			dispose();
		}
		
		return resultHashMap;
	}//excuteSQL
	
	public String executeSingleRowFirstColumSelectSQL(String sql){
		try{
			if(conn == null){
				getConnection();
			}
			
			prepare(sql);

			rs = ps.executeQuery();

			if(rs!=null){
				rs.next();
				String str=rs.getString(1);
				return str;
			}
		}catch (Exception ex) {
			logger.error("ExcuteSQL Exception : " + ex);
			logger.error("! SQL !");
			logger.error(sql);
			logger.error("! SQL !");
		}finally{
			dispose();
			//close();
		}
		return "";
	}//ExecuteSingleRowFirstColumSelectSQL

	public int excuteUpdateSQL(String sql){
		int resultInt=0;
		try {
			if(conn == null){
				getConnection();
			}
			prepare(sql);

			resultInt = ps.executeUpdate();

		} catch (Exception ex) {
			resultInt=0;
			logger.error("ExcuteSQL Exception : " + ex);
			logger.error("!update! SQL !");
			logger.error(sql);
			logger.error("!update! SQL !");
		}finally{
			dispose();
		}
		return resultInt;
	}//updateSQL
	
	
	public int addBatch(String sql){
		try {
			if(stmt==null){
				//logger.info("Null Make PS");
				conn.setAutoCommit(false);
				stmt=conn.createStatement();
			}
			stmt.addBatch(sql);
		} catch (Exception e) {
			logger.equals("batch Error");
		}
		return batchNum++;
	}
	
	public int[] executeBatch(){
		int[] result=null;
		try {
			logger.info("EXEC batch");
			result = stmt.executeBatch();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("Exec Batch Error");
			e.printStackTrace();
		} finally{
			batchNum=0;
			try {
				logger.info("commit");
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dispose();
		}
		return result; 
	}
	
	public void commit(){
		logger.info("commit");
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String queto(String str){
		return "'"+str+"'";
	}
	
	public static void main(String[] args) {
		AbstractBizDAO dao=new AbstractBizDAO();
		try {
			System.out.println("start");
			dao.getConnection();
			System.out.println("OK!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
