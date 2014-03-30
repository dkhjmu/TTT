package com.act.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.biz.checher.Checker;
import com.biz.dao.AbstractBizDAO;
import com.biz.picker.random.RandomPicker;
import com.biz.vo.GameVO;
import com.co.util.ArrayUtil;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;
import com.co.util.sql.dml.InsertData;

public class GameDAO extends AbstractBizDAO {
	
	private static String tableName="game_data";
	private static String id[]  ={"seq",              "game",              "bnu"};
	private static String type[]={"NUMBER  NOT NULL", "NUMBER  NOT NULL", "NUMBER  NOT NULL"};
	private static String pk[]  ={"seq", "game", "bnu"};

	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}

	public int createBasisTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}

	private void insertBatchBasisData(int seq, int game, int bnu){
		String[] value={seq+"", game+"", bnu+""};
		String sql=InsertData.getSQL(tableName, id, value);
		//System.out.println(sql);
		this.addBatch(sql);
	}

	public void makeGame(RandomPicker picker, int tryN, int seq) {
		picker.pick(tryN, seq);
		for(int i=0;i<tryN;i++){
			int[] rr=picker.getSeq(i);
			System.out.print(i+".\t");
			for(int j=0;j<6;j++){
				System.out.print(rr[j]+"\t");
				insertBatchBasisData(seq, i+1, rr[j]);
			}
			System.out.println();
		}
		this.executeBatch();
	}
	
	public GameVO getResultVo(int seq, int game){
		sb=new StringBuffer();
		sb.append("select bnu, rownum from game_data where seq = "+seq+" and game = "+game);
		
		ArrayList<HashMap<String, String>> result = this.excuteSelectSQL(sb.toString());
		if(result.size()<=0){
			return null;
		}
		GameVO vo = new GameVO(seq, game, result);
		return vo;
	}
	

	public static void main(String[] args) {
		GameDAO dao = new GameDAO();
		int seq=543;
		int tryN=1000;
		dao.dropTable();
		dao.createBasisTable();
		
		RandomPicker picker=new RandomPicker();
		dao.makeGame(picker, tryN, seq);

		System.out.println("#################################");
		int label[] = {0,1,2,3,4,5,6,7};
		int total[] = {0,0,0,0,0,0,0,0};
		int right[] = {13,18,26,31,34,44};
		int bonus=12;
		for(int i=1;i<=tryN;i++){
			GameVO vo=dao.getResultVo(seq, i);
			int sum=Checker.checkResult(vo.getNumsArray(), right, bonus);
			total[sum]++;
			if(sum>=5){
				System.out.println(i+"("+sum+"):\t"+Arrays.toString(vo.getNumsArray()));
			}
		}
		ArrayUtil.print(label);
		ArrayUtil.print(total);
		
		System.out.println("All End!!");
	}
}
