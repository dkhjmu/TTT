package com.biz.random.dao;

import java.util.ArrayList;

import com.biz.dao.AbstractBizDAO;
import com.biz.vo.IntVo;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;
import com.co.util.sql.dml.InsertData;

public class RandomDAO extends AbstractBizDAO {
	
	private static String tableName="basis_data";
	private static String id[]  ={"seq",              "bnu",              "norder"};
	private static String type[]={"NUMBER  NOT NULL", "NUMBER  NOT NULL", "NUMBER  NOT NULL"};
	private static String pk[]  ={"seq", "bnu"};

	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}

	public int createBasisTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}
	
	public int[] executeAllBatch(){
		return this.executeBatch();
	}

	public void insertBasisDataBatch(int seq, int no1, int no2, int no3, int no4, int no5, int no6, int bonus){
		logger.info(seq+" start");
		int nums[]={no1, no2, no3, no4, no5, no6, bonus};
		for(int i=0;i<nums.length;i++){
			insertBatchBasisData(seq, nums[i], i+1);
		}
	}

	private void insertBatchBasisData(int seq, int bnu, int odr){
		String[] value={seq+"", bnu+"", odr+""};
		this.addBatch(InsertData.getSQL(tableName, id, value));
	}
	
	public static void main(String[] args) {
//		RandomDAO dao=new RandomDAO();
//		dao.dropTable();
//		dao.createBasisTable();
//		for(int i=275;i<393;i++){
//			int[] result=getRandomGame();
//			dao.insertBasisDataBatch(i, result[0], result[1], result[2], result[3], result[4], result[5], result[6]);
//		}
//		dao.executeAllBatch();
		
		int i=0;
//		int[] seed = {};
		int[] result = {8,1,42,3,24,7};
		int[] target = null;
		do{
			i++;
			target=getRandomGame();
			System.out.println(i+":"+target);
		}while(compare(result, target)==false);
		System.out.println("RESULT! : "+i);
		
	}
	
	public static boolean compare(int[] result, int[] target){
		int lr = result.length;
		int size = 0;
		for(int i=0;i<target.length;i++){
			for(int j=0;j<lr;j++){
				if(result[j]==target[i]){
					size++;
				}
			}
		}
		return (size==lr);
	}
	
	private static final int GAME_SIZE = 23;

	public static int[] getRandomGame() {
		int[] randResult=new int[GAME_SIZE];
		ArrayList<IntVo> ball=new ArrayList<IntVo>();
		for(int i=1;i<46;i++){
			ball.add(new IntVo(i));
		}
		int nums=0;
		int rand=0;
		do{
			rand=getRand(ball.size());
			IntVo e=ball.get(rand);
			if(checkDuplicate(randResult, e.getInt())){
				randResult[nums]=e.getInt();
				ball.remove(rand);
				System.out.println(nums+".\t"+e.getInt());
				nums++;
			}else{
				System.out.println("FALSE!!!!");
			}
		}while(nums<GAME_SIZE);
		
		return randResult;
	}
	
	public static int[] getRandomGameWithSeed(int[] seed) {
		int[] randResult=new int[GAME_SIZE];
		ArrayList<IntVo> ball=new ArrayList<IntVo>();
		for(int i=1;i<46;i++){
			ball.add(new IntVo(i));
		}
		int nums=0;
		int rand=0;
		int seedNum=getRand(seed.length);
		
		randResult[nums++] = seed[seedNum];
		ball.remove(seed[seedNum]-1);
		do{
			rand=getRand(ball.size());
			IntVo e=ball.get(rand);
			if(checkDuplicate(randResult, e.getInt())){
				randResult[nums]=e.getInt();
				ball.remove(rand);
				System.out.println(nums+".\t"+e.getInt());
				nums++;
			}else{
				System.out.println("FALSE!!!!");
			}
		}while(nums<GAME_SIZE);
		
		return randResult;
	}
	
	
	private static int getRand(int max){
		int rand=(int)(Math.floor(Math.random()*100)%max);
		return rand;
	}
	
	private static boolean checkDuplicate(int[] input, int num){
		for(int i=0;i<input.length;i++){
			if(input[i]==num){
				return false;
			}
		}
		return true;
	}
}
